/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
pragma solidity >=0.7.1;
pragma experimental ABIEncoderV2;

import "./CbcLockableStorageInterface.sol";
import "./CrossBlockchainControlInterface.sol";
import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "../../../../receipts/src/main/solidity/Receipts.sol";
import "../../../../lockablestorage/src/main/solidity/LockableStorage.sol";


contract CrossBlockchainControl is CrossBlockchainControlInterface, CbcLockableStorageInterface, Receipts {
    bytes32 constant private START_EVENT_SIGNATURE = keccak256("Start(uint256,address,uint256,bytes)");
    bytes32 constant private SEGMENT_EVENT_SIGNATURE = keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");
    bytes32 constant private ROOT_EVENT_SIGNATURE = keccak256("Root(uint256,bool)");


    event Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes _callGraph);
    event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
        address[] _lockedContracts, bool _success, bytes _returnValue);
    event Root(uint256 _crossBlockchainTransactionId, bool _success);
    event Signalling(uint256 _rootBcId, uint256 _crossBlockchainTransactionId);
    event Close(uint256 _crossBlockchainTransactionId);

    event Call(
        uint256 _expectedBlockchainId, uint256 _actualBlockchainId,
        address _expectedContract, address _actualContract,
        bytes _expectedFunctionCall, bytes _actualFunctionCall,
        bytes _retVal);
    event NotEnoughCalls(uint256 _expectedNumberOfCalls, uint256 _actualNumberOfCalls);

    event Dump(uint256 _val1, bytes32 _val2, address _val3, bytes _val4);

    uint256 public override myBlockchainId;
    TxReceiptsRootStorageInterface public txReceiptRootStorage;

    // For Root Blockchain:
    // Mapping of cross-blockchain transaction id to transaction information.
    // 0: Never used.
    // 1: The transaction has completed and was successful.
    // 2: The transaction has completed and was not successful.
    // Otherwise: time-out for the transaction: as seconds since unix epoch.
    uint256 constant private NOT_USED = 0;
    uint256 constant private SUCCESS = 1;
    uint256 constant private FAILURE = 2;
    mapping (uint256=> uint256) public override transactionInformation;


    // Storage variables that are just stored for the life of a transaction. They need to
    // be available in storage as code calls back into this contract.
    // TODO some of these will be able to be stored in memory
    uint256 public activeCallRootBlockchainId;
    uint256 public activeCallCrossBlockchainTransactionId;
    bytes public activeCallGraph;
    uint256[] private activeCallsCallPath;
    bytes[] private activeCallReturnValues;
    uint256 private activeCallReturnValuesIndex;
    mapping(address => bool) private activeCallLockedContractsMap;
    address[] private activeCallLockedContracts;
    bool private activeCallFailed;


    constructor(uint256 _myBlockchainId, address _txReceiptRootStorage) {
        myBlockchainId = _myBlockchainId;
        txReceiptRootStorage = TxReceiptsRootStorageInterface(_txReceiptRootStorage);
    }


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external override {
        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        require(transactionInformation[_crossBlockchainTransactionId] == NOT_USED, "Transaction already registered");
        uint256 transactionTimeoutSeconds = _timeout + block.timestamp;
        transactionInformation[_crossBlockchainTransactionId] = transactionTimeoutSeconds;

        emit Start(_crossBlockchainTransactionId, msg.sender, transactionTimeoutSeconds, _callGraph);
    }


    function segment(
        uint256 _rootBlockchainId, address _rootCBCContract,
        bytes32 _startEventTxReceiptRoot, bytes calldata _encodedStartTxReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof, uint256[] calldata _callPath) external override {

        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        // TODO require call path length >= 1

        txReceiptRootStorage.verify(_rootBlockchainId, _startEventTxReceiptRoot, _encodedStartTxReceipt,
            _proofOffsets, _proof);

        bytes memory startEventData;
        {
            bytes memory _encodedStartTxReceiptLocal = _encodedStartTxReceipt;
            RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceiptLocal));
            bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
    //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
            (, startEventData) = extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        }



        // TODO use transaction id in conjunction with call path to prevent replay attacks.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(startEventData, 0);
        address startCaller = BytesUtil.bytesToAddress1(startEventData, 0x20);
        require(startCaller == tx.origin, "EOA does not match start event");
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(startEventData, 0x80);
        emit Dump(lenOfActiveCallGraph, bytes32(0), address(0), bytes(""));

        bytes memory callGraph = BytesUtil.slice(startEventData, 0xA0, lenOfActiveCallGraph);
        activeCallGraph = callGraph;

        activeCallRootBlockchainId = _rootBlockchainId;
        activeCallsCallPath = _callPath;

        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(callGraph, _callPath);
        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = targetContract.call(functionCall);

        bytes32 hashOfCallGraph = keccak256(callGraph);
        // TODO emit segments understanding of root blockhain id
        emit Segment(activeCallCrossBlockchainTransactionId, hashOfCallGraph, _callPath, activeCallLockedContracts, isSuccess, returnValueEncoded);

        cleanupAfterCall();
    }

    event Root2(uint256 _bcId, address _cbcContract, bytes32 _receiptRoot, bytes _encodedTxReceipt /* , uint256[] _proofOffsets, bytes[] proof */);

    EventProof[] rootCalls;

    function rootPrep(uint256 _blockchainId, address _cbcContract, bytes32 _txReceiptRoot,
        bytes calldata _encodedTxReceipt, uint256[] calldata _proofOffsets, bytes[] calldata _proof) external override {
        uint256[] memory proofOffsets = new uint256[](_proofOffsets.length);
        for (uint256 i = 0; i < _proofOffsets.length; i++) {
            proofOffsets[i] = _proofOffsets[i];
        }
        bytes[] memory proof = new bytes[](_proof.length);
        for (uint256 i = 0; i < _proof.length; i++) {
            proof[i] = _proof[i];
        }
        emit Root2(_blockchainId, _cbcContract, _txReceiptRoot, _encodedTxReceipt);
        EventProof memory call = EventProof(_blockchainId, _cbcContract, _txReceiptRoot, _encodedTxReceipt, proofOffsets, proof);
        rootCalls.push(call);

        // This verification code doesn't need to be here - the verification happens in the function.
        // However, having the verify here helps with debug.
        txReceiptRootStorage.verify(
            _blockchainId,
            _txReceiptRoot,
            _encodedTxReceipt,
            _proofOffsets,
            _proof);
    }

    function root2(EventProof calldata _eventProof) external {
        emit Dump(_eventProof.blockchainId, bytes32(0), address(0), bytes(""));
    }


    function root1(EventProof[] calldata _eventProofs) external override {
        emit Dump(_eventProofs.length, bytes32(0), address(0), bytes(""));
        emit Dump(_eventProofs[0].blockchainId, bytes32(0), address(0), bytes(""));

    }


    /**
     * Root call.
     *
     * The parameter should be an array of Info[], with the array offset 0 being the start event,
     * and the other array elements being for segment events. Note that the segment events must be
     * in order of the functions called.
     *
    **/
    function root() external override {
        //Verify the start event and all segment events.
        for (uint256 i = 0; i < rootCalls.length; i++) {
            txReceiptRootStorage.verify(
                rootCalls[i].blockchainId,
                rootCalls[i].txReceiptRoot,
                rootCalls[i].encodedTxReceipt,
                rootCalls[i].proofOffsets,
                rootCalls[i].proof);
        }

        //Check that tx.origin == msg.sender. This call must be issued by an EOA. This is required so
        // that we can check that the tx.origin for this call matches what is in the start event.
        require(tx.origin == msg.sender, "Transaction must be instigated by an EOA");

        //Check that the blockchain Id that can be used with the transaction receipt for verification matches this
        // blockchain. That is, check that this is the root blockchain.
        require(myBlockchainId == rootCalls[0].blockchainId, "This is not the root blockchain");
        activeCallRootBlockchainId = myBlockchainId;

        // Ensure this is the cross-blockchain contract contract that generated the start event.
        require(address(this) == rootCalls[0].cbcContract, "Root blockchain CBC contract was not this one");

        // Extract the start event from the RLP encoded receipt trie data.
        bytes memory _encodedStartTxReceiptLocal = rootCalls[0].encodedTxReceipt;
        bytes memory startEventData = extractStartEventData(address(this), _encodedStartTxReceiptLocal);

        // Extract segment events
        bytes[] memory segmentEvents = new bytes[](rootCalls.length - 1);
        for (uint256 i = 1; i < rootCalls.length; i++) {
            segmentEvents[i-1] = extractSegmentEventData(rootCalls[i].cbcContract, rootCalls[i].encodedTxReceipt);
        }

        rootProcessing(startEventData, segmentEvents);
    }

    function rootProcessing(bytes memory _startEventData, bytes[] memory _segmentEvents) private {
        // Check that Cross-blockchain Transaction Id as shown in the Start Event is still active.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(_startEventData, 0);
        uint256 timeoutForCall = transactionInformation[activeCallCrossBlockchainTransactionId];
        require(timeoutForCall != NOT_USED, "Call not active");
        require(timeoutForCall != SUCCESS, "Call ended");
        require(timeoutForCall != FAILURE, "Call ended");

        //Check if the cross-blockchain transaction has timed-out.
        if (block.timestamp > timeoutForCall) {
            failRootTransaction();
            cleanupAfterCall();
            return;
        }

        // Check that the tx.origin matches the account that submitted the Start transaction,
        // by checking the Start Event. Exit if it doesn’t.
        // This means that only the initiator of the cross-blockchain transaction can call this
        // function call prior to the time-out.
        address startCaller = BytesUtil.bytesToAddress1(_startEventData, 0x20);
        require(startCaller == tx.origin, "EOA does not match start event");

        // Determine the hash of the call graph described in the start event. This is needed to check the segment
        // event information.
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(_startEventData, 0x80);
        bytes memory callGraph = BytesUtil.slice(_startEventData, 0xA0, lenOfActiveCallGraph);
        activeCallGraph = callGraph;
        bytes32 hashOfCallGraph = keccak256(callGraph);

        //Verify the event information in the Segment Events.
        // Check that the Segment Events correspond to function calls that the Start Event indicates that the
        //  root function call should have been called.
        // Exit if the information doesn’t match.
        //If any of the Segment Events indicate an error, Goto Ignore.
        for (uint256 i = 0; i < _segmentEvents.length; i++) {
            bytes memory segmentEvent = _segmentEvents[i];

            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 crossBlockchainTxId = BytesUtil.bytesToUint256(segmentEvent, 0);
            bytes32 hashOfCallGraphFromSegment = BytesUtil.bytesToBytes32(segmentEvent, 0x20);
            uint256 locationOfCallPath = BytesUtil.bytesToUint256(segmentEvent, 0x40);
            uint256 locationOfLockedContracts = BytesUtil.bytesToUint256(segmentEvent, 0x60);
            uint256 success = BytesUtil.bytesToUint256(segmentEvent, 0x80);
            uint256 locationOfReturnValue = BytesUtil.bytesToUint256(segmentEvent, 0xA0);

            uint256 lenOfReturnValue = BytesUtil.bytesToUint256(segmentEvent, locationOfReturnValue);
            bytes memory returnValue = BytesUtil.slice(segmentEvent, locationOfReturnValue + 0x20, lenOfReturnValue);

//            emit Dump(crossBlockchainTxId, hashOfCallGraphFromSegment, address(0), segmentEvent);
//            emit Dump(locationOfCallPath, hashOfCallGraphFromSegment, address(0), segmentEvent);
//            emit Dump(locationOfLockedContracts, hashOfCallGraphFromSegment, address(0), segmentEvent);
//            emit Dump(success, hashOfCallGraph, address(0), segmentEvent);
//            emit Dump(locationOfReturnValue, hashOfCallGraph, address(0), segmentEvent);
//            emit Dump(lenOfReturnValue, hashOfCallGraph, address(0), returnValue);

            // TODO check hash of start event: This ensures the same call graph was executed by all parts of the call graph.
            // TODO check cross blockchain tx id
            // TODO fail is success == false

            // Store the extracted return results from segment events.
            activeCallReturnValues.push(returnValue);
        }

        // The element will be the default, 0.
        uint256[] memory callPathForStart = new uint256[](1);

        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(callGraph, callPathForStart);
        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");
        //execute(targetContract, functionCall);

        bool isSuccess;
        (isSuccess, ) = targetContract.call(functionCall);

        // Check that all cross-blockchain calls were used.
        if (activeCallReturnValues.length != activeCallReturnValuesIndex) {
            emit NotEnoughCalls(activeCallReturnValues.length, activeCallReturnValuesIndex);
            isSuccess = false;
        }

        isSuccess = activeCallFailed ? false : isSuccess;


        unlockContracts(isSuccess);

        transactionInformation[activeCallCrossBlockchainTransactionId] = isSuccess ? SUCCESS : FAILURE;

        emit Root(activeCallCrossBlockchainTransactionId, isSuccess);
        cleanupAfterCall();
    }


    /**
     * Signalling call: Commit or ignore updates + unlock any locked contracts.
     *
     * The parameter should be an array of Info[], with the array offset 0 being the root event,
     * and the other array elements being for segment events that have locked contracts.
     *
     * User rootPrep to set-up the parameters
     **/
    function signalling() external override {
        //Verify the root event and all segment events.
        for (uint256 i = 0; i < rootCalls.length; i++) {
            txReceiptRootStorage.verify(
            rootCalls[i].blockchainId,
            rootCalls[i].txReceiptRoot,
            rootCalls[i].encodedTxReceipt,
            rootCalls[i].proofOffsets,
            rootCalls[i].proof);
        }

        // Extract the root event from the RLP encoded receipt trie data.
        bytes memory _encodedRootTxReceiptLocal = rootCalls[0].encodedTxReceipt;
        bytes memory rootEventData = extractRootEventData(rootCalls[0].cbcContract, _encodedRootTxReceiptLocal);

        // Extract information from the root event.
        uint256 rootEventCrossBlockchainTxId = BytesUtil.bytesToUint256(rootEventData, 0);
        uint256 success = BytesUtil.bytesToUint256(rootEventData, 0x20);
        // The fact that the event verified implies that the root blockchain id value can be trusted.
        uint256 rootBlockchainId = rootCalls[0].blockchainId;

        // Check that all of the Segment Events are for the same transaction id, and are for this blockchain.
        for (uint256 i = 1; i < rootCalls.length; i++) {
            //Check that the blockchain Id for the segment was matches this contract's blockchain id.
            require(myBlockchainId == rootCalls[i].blockchainId, "Not the correct blockchain id");

            // Ensure this is the cross-blockchain contract contract that generated the segment event.
            require(address(this) == rootCalls[i].cbcContract, "Segment blockchain CBC contract was not this one");

            bytes memory segmentEvent = extractSegmentEventData(rootCalls[i].cbcContract, rootCalls[i].encodedTxReceipt);

            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 segmentEventCrossBlockchainTxId = BytesUtil.bytesToUint256(segmentEvent, 0);
            // Check that the cross blockchain transaction id is the same for the root and the segment event.
            require(rootEventCrossBlockchainTxId == segmentEventCrossBlockchainTxId);
            // TODO check the Root Blockchain id indicated in the segment matches the one from the root transaction.


            // TODO Check that the cross chain transaction id for the root blockchain id is in use on this blockchain but has not been added to the completed list.

            // For each address indicated in the Segment Event as being locked, Commit or Ignore updates
            // according to what the Root Event says.
            uint256 locationOfLockedContracts = BytesUtil.bytesToUint256(segmentEvent, 0x60);
            emit Dump(locationOfLockedContracts, bytes32(0), address(0), segmentEvent);
            uint256 numElementsOfArray = BytesUtil.bytesToUint256(segmentEvent, locationOfLockedContracts);
            emit Dump(numElementsOfArray, bytes32(0), address(0), segmentEvent);
            for (uint256 j = 0; j < numElementsOfArray; j++) {
                address lockedContractAddr = BytesUtil.bytesToAddress1(segmentEvent, locationOfLockedContracts + 0x20 + 0x20 * j);
                emit Dump(0, bytes32(0), lockedContractAddr, segmentEvent);
                LockableStorage lockedContract = LockableStorage(lockedContractAddr);
                // Check that the contract really has been locked by this transaction.
                require(lockedContract.locked());
                require(lockedContract.lockedByRootBlockchainId() == rootBlockchainId);
                require(lockedContract.lockedByTransactionId() == rootEventCrossBlockchainTxId);
                lockedContract.finalise(success != 0);
            }
        }

        emit Signalling(rootBlockchainId, rootEventCrossBlockchainTxId);
    }



    function crossBlockchainCall(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) external override {
        bytes memory returnValue = commonCallProcessing(_blockchainId, _contract, _functionCallData);

        // Revert if return value != empty then assert
        require(compare(returnValue, bytes("")));
    }


    function crossBlockchainCallReturnsUint256(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) external override returns (uint256){
        bytes memory returnValue = commonCallProcessing(_blockchainId, _contract, _functionCallData);
        return BytesUtil.bytesToUint256(returnValue, 0);
    }

    // Called by a provisional storage contract indicating the contract needs to be locked.
    function addToListOfLockedContracts(address _contractToLock) external override {
        if (activeCallLockedContractsMap[_contractToLock] == false) {
            activeCallLockedContracts.push(_contractToLock);
            activeCallLockedContractsMap[_contractToLock] = true;
        }
    }


    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() public override view returns (bool) {
        return 0 == activeCallRootBlockchainId;
    }

    function getActiveCallRootBlockchainId() public override view returns (uint256) {
        return activeCallRootBlockchainId;
    }

    function getActiveCallCrossBlockchainTransactionId() public override view returns (uint256) {
        return activeCallCrossBlockchainTransactionId;
    }



    function extractTargetFromCallGraph(bytes memory _callGraph, uint256[] memory _callPath) private pure
        returns (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) {

        RLP.RLPItem[] memory functions = RLP.toList(RLP.toRLPItem(_callGraph));

        for (uint i=0; i < _callPath.length - 1; i++) {
            functions = RLP.toList(functions[_callPath[i]]);
        }
        RLP.RLPItem[] memory func = RLP.toList(functions[_callPath[_callPath.length - 1]]);
        targetBlockchainId = RLP.toUint(func[0]);
        targetContract = RLP.toAddress(func[1]);
        functionCall = RLP.toData(func[2]);
    }

    function extractStartEventData(address _rootCBCContract, bytes memory _encodedStartTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
//        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory startEventData;
        (, startEventData) = extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        return startEventData;
    }

    function extractSegmentEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory segmentEventData;
        (, segmentEventData) = extractEvent(_cBCContract, SEGMENT_EVENT_SIGNATURE, receiptBytes);
        return segmentEventData;
    }

    function extractRootEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory rootEventData;
        (, rootEventData) = extractEvent(_cBCContract, ROOT_EVENT_SIGNATURE, receiptBytes);
        return rootEventData;
    }


    function failRootTransaction() private {
        transactionInformation[activeCallCrossBlockchainTransactionId] = FAILURE;
        emit Root(activeCallCrossBlockchainTransactionId, false);
    }


    /**
     * Clean-up temporary storage after a Segment or Root call.
     */
    function cleanupAfterCall() private {
        // Clean-up active call temporary storage.
        delete activeCallCrossBlockchainTransactionId;
        delete activeCallRootBlockchainId;
        delete activeCallGraph;
        delete activeCallsCallPath;
        for (uint i = 0; i < activeCallLockedContracts.length; i++) {
            delete activeCallLockedContractsMap[activeCallLockedContracts[i]];
        }
        delete activeCallLockedContracts;
        delete activeCallReturnValues;
        delete activeCallReturnValuesIndex;
        delete activeCallFailed;
    }

    function unlockContracts(bool _commit) private {
        emit Dump(activeCallLockedContracts.length, bytes32(0), address(0), "");
        for (uint256 i = 0; i < activeCallLockedContracts.length; i++) {
            emit Dump(activeCallLockedContracts.length, bytes32(0), activeCallLockedContracts[i], "");
            LockableStorage lockableStorageContract = LockableStorage(activeCallLockedContracts[i]);
            lockableStorageContract.finalise(_commit);
        }
    }


    function commonCallProcessing(uint256 _blockchainId, address _contract, bytes calldata _functionCallData) private returns(bytes memory) {
        emit Dump(_blockchainId, bytes32(0), _contract, _functionCallData);

        require(activeCallReturnValuesIndex < activeCallReturnValues.length, "Call to cross call without return value");

        // Check that this function call should occur
        uint256[] memory callPath = new uint256[](activeCallsCallPath.length + 1);
        for (uint i = 0; i < activeCallsCallPath.length; i++) {
            callPath[i] = activeCallsCallPath[i];
        }
        callPath[callPath.length - 1] = activeCallReturnValuesIndex + 1;
        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(activeCallGraph, callPath);

        if (_blockchainId != targetBlockchainId ||
            _contract != targetContract ||
            !compare(_functionCallData, functionCall)) {
            activeCallFailed = true;
        }
        bytes memory retVal = activeCallReturnValues[activeCallReturnValuesIndex++];

        emit Call(targetBlockchainId, _blockchainId, targetContract, _contract, functionCall, _functionCallData, retVal);

        return retVal;
    }
}