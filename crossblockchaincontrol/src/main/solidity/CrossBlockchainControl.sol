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

import "./CrossBlockchainControlInterface.sol";
import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "../../../../receipts/src/main/solidity/Receipts.sol";
import "../../../../lockablestorage/src/main/solidity/LockableStorage.sol";


contract CrossBlockchainControl is CrossBlockchainControlInterface, Receipts {
    bytes32 constant private START_EVENT_SIGNATURE = keccak256("Start(uint256,uint256,bytes)");
    bytes32 constant private SEGMENT_EVENT_SIGNATURE = keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");


    event Start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes _callGraph);
    event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
        address[] _lockedContracts, bool _success, bytes _returnValue);
    event Root(uint256 _crossBlockchainTransactionId, bool _success);
    event Signalling(uint256 _crossBlockchainTransactionId);
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

    // Mapping of cross-blockchain transaction id to time-out block time stamp.
    mapping (uint256=> uint256) public override  timeout;
    // Mapping of cross-blockchain transaction id to call graphs, for calls instigated from this blockchain.
    // TODO why is this needed?
    mapping (uint256=> bytes) public override  callGraphs;

    uint256 constant private SUCCESS = 1;
    uint256 constant private FAILURE = 2;
    mapping (uint256=> uint256) public override previousCallResult;


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
// TODO
//Check that tx.origin == msg.sender. This call must be issued by an EOA.

        require(crossBlockchainTransactionExists(_crossBlockchainTransactionId) == false, "Transaction already registered");
        timeout[_crossBlockchainTransactionId] = _timeout + block.timestamp;
        callGraphs[_crossBlockchainTransactionId] = _callGraph;

// TODO emit the account that was the EOA
        emit Start(_crossBlockchainTransactionId, _timeout, _callGraph);
    }


    function segment(
        uint256 _rootBlockchainId, address _rootCBCContract,
        bytes32 _startEventTxReceiptRoot, bytes calldata _encodedStartTxReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof, uint256[] calldata _callPath) external override {

// TODO
//Check that tx.origin == msg.sender. This call must be issued by an EOA.

        // TODO require call path length >= 1

        txReceiptRootStorage.verify(_rootBlockchainId, _startEventTxReceiptRoot, _encodedStartTxReceipt,
            _proofOffsets, _proof);

        bytes memory startEventData;
        {
            bytes memory _encodedStartTxReceiptLocal = _encodedStartTxReceipt;
            RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceiptLocal));
            bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
    //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
            (, startEventData) =
                extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        }

// TODO check that account that submitted the transaction is the same one that submitted the start transaction (check the start event)

        // TODO use transaction id in conjunction with call path to prevent replay attacks.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(startEventData, 0);
        //tempActiveTimeout = BytesUtil.bytesToUint256(startEventData, 0x20);
        // Skip the type field at 0x40.
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(startEventData, 0x60);
        bytes memory callGraph = BytesUtil.slice(startEventData, 0x80, lenOfActiveCallGraph);
        activeCallGraph = callGraph;

        activeCallRootBlockchainId = _rootBlockchainId;
        activeCallsCallPath = _callPath;

        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(callGraph, _callPath);
        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");
//        execute(targetContract, functionCall);

        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = targetContract.call(functionCall);

        bytes32 hashOfCallGraph = keccak256(callGraph);
        emit Segment(activeCallCrossBlockchainTransactionId, hashOfCallGraph, _callPath, activeCallLockedContracts, isSuccess, returnValueEncoded);

        cleanupAfterCall();
    }

    event Root2(uint256 _bcId, address _cbcContract, bytes32 _receiptRoot, bytes _encodedTxReceipt /* , uint256[] _proofOffsets, bytes[] proof */);

    Info[] rootCalls;

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
        Info memory call = Info(_blockchainId, _cbcContract, _txReceiptRoot, _encodedTxReceipt, proofOffsets, proof);
        rootCalls.push(call);

        txReceiptRootStorage.verify(_blockchainId, _txReceiptRoot, _encodedTxReceipt, _proofOffsets, _proof);
    }



    /**
     * Root call.
     *
     * Function parameters:
    //_startEvent: The Start Event emitted during the call to start().
//_startBlockNumber: The block when start() was called.
//_startMerkleProof: Merkle Proof that proves Start Event is valid.
//_calls: Array of return values for the Segment Events emitted during calls.
//_blockNumbers: Array of block numbers that Segments were called on.
//_proofs: Array of Merkle Proofs for Segment Events.
//
//Processing:
//Check that tx.origin == msg.sender. This call must be issued by an EOA.
    // TODO WHY?
//Check that Crosschain Transaction Id as shown in the Start Event is still active.
//Check that the root transactions blockchain Id as shown in the Start Event matches this blockchain.
//Verify the event information for the Start Event, checking the Merkle proof with the block hash for the blockchain and block number.
//If the block number is after the timeout indicated in the Start Event, GOTO Ignore.
//Check that the tx.origin matches the account that submitted the Start transaction, by checking the Start Event. Exit if it doesn’t.
//This means that only the initiator of the transaction can call this function call prior to the time-out.
//Verify the event information for the Segment Events, checking the Merkle proof with the block hash for the blockchain and block number. Check that the Segment Events correspond to function calls that the Start Event indicates that the root function call should have been called.
//Exit if the information doesn’t match.
//If any of the Segment Events indicate an error, Goto Ignore.
//Extract the function calls in Start Event and return results from _calls to create the list of expected blockchain ids, addresses, function ids,  parameters, and the return results. Store them in an Expected Calls state variable.
//Call the entry point function call with the parameters, using the values in the Start Event that indicated the root function. Have the call inside a try-catch block (a Solidity 6 feature).
//Check lockability and lock state for any state reads or writes. Modify as per the rules described later in this document.
//Each time a cross blockchain function call occurs, the crossCall function will be called. Check that the actual and expected blockchain ids, addresses, function ids, parameters match the values in Expected Calls. Return the function results to the calling code.
//If the actual and expected values don’t match: set a state variable to indicate there has been an error and throw an error in the function.
//When the entry point function call for this shard completes:
//Check that all cross blockchain function calls in Expected Calls were called and that no error was reported. If not goto Ignore.
//If the code threw an error, goto Ignore.
//Emit a Root Event indicating the cross blockchain transaction should be committed.
//Unlock all contracts locked at part of the root function.
//Add the cross blockchain transaction id to the completed set of transactions RSA accumulator.
//End
//Igore:
//Emit a Root Event indicating the cross blockchain transaction should be ignored.
//Add the cross blockchain transaction id to the completed set of transactions RSA accumulator.
//End
//
//
//The Root Event contains:
//Cross Blockchain Transaction Id
//Commit or Ignore.
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

        bytes memory _encodedStartTxReceiptLocal = rootCalls[0].encodedTxReceipt;
        require(address(this) == rootCalls[0].cbcContract, "Root blockchain CBC contract was not this one");
        bytes memory startEventData = extractStartEventData(address(this), _encodedStartTxReceiptLocal);

        //Check that Crosschain Transaction Id as shown in the Start Event is still active.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(startEventData, 0);
        uint256 timeoutForCall = timeout[activeCallCrossBlockchainTransactionId];
        require(timeoutForCall != 0, "Call not active");

        //Check that the blockchain Id that can be used with the transaction receipt for verification matches this
        // blockchain. That is, check that this is the root blockchain.
        require(myBlockchainId == rootCalls[0].blockchainId, "This is not the root blockchain");
        activeCallRootBlockchainId = myBlockchainId;

        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(startEventData, 0x60);
        bytes memory callGraph = BytesUtil.slice(startEventData, 0x80, lenOfActiveCallGraph);
        activeCallGraph = callGraph;
        bytes32 hashOfCallGraph = keccak256(callGraph);



//        emit Dump(block.timestamp, bytes32(0), address(0), "");
//        emit Dump(timeoutForCall, bytes32(0), address(0), "");
//

        //Check if the cross-blockchain transaction has timed-out.
        if (block.timestamp > timeoutForCall) {
            failRootTransaction();
            cleanupAfterCall();
            return;
        }

        // TODO Check that the tx.origin matches the account that submitted the Start transaction, by checking the Start Event. Exit if it doesn’t.
        //This means that only the initiator of the cross-blockchain transaction can call this function call prior to the time-out.

        //Verify the event information in the Segment Events.
        // Check that the Segment Events correspond to function calls that the Start Event indicates that the
        //  root function call should have been called.
        // Exit if the information doesn’t match.
        //If any of the Segment Events indicate an error, Goto Ignore.
        for (uint256 i = 1; i < rootCalls.length; i++) {
            bytes memory segmentEvent = extractSegmentEventData(rootCalls[i].cbcContract, rootCalls[i].encodedTxReceipt);

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

            emit Dump(crossBlockchainTxId, hashOfCallGraphFromSegment, address(0), segmentEvent);
            emit Dump(locationOfCallPath, hashOfCallGraphFromSegment, address(0), segmentEvent);
            emit Dump(locationOfLockedContracts, hashOfCallGraphFromSegment, address(0), segmentEvent);
            emit Dump(success, hashOfCallGraph, address(0), segmentEvent);
            emit Dump(locationOfReturnValue, hashOfCallGraph, address(0), segmentEvent);
            emit Dump(lenOfReturnValue, hashOfCallGraph, address(0), returnValue);

            activeCallReturnValues.push(returnValue);
        }

        // The elememnt will be the default, 0.
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

        emit Root(activeCallCrossBlockchainTransactionId, isSuccess);
        cleanupAfterCall();
    }



// TODO should be Info[]
//    function root2(Info calldata) external {
//    }



//    function root1(uint256[][] calldata _start) external {
//        emit Root(17, true);
//    }


//    function root(
//        uint256[] calldata _rootAndSegmentBlockchainIds,
//        address[] calldata _rootAndSegmentCBCContracts,
//        bytes32[] calldata _startAndSegmentTxReceiptRoots,
//        bytes[] calldata _startAndSegmentTxReceipts,
////        uint256[][] calldata _startAndSegmentProofOffsets,
//        bytes[][] calldata _startAndSegmentProofs
//) external override {
//
//        emit Root(7, true);
//
//    }

//    function root2(Info[] calldata /* _startAndSegments */ ) external override {
//        emit Root(13, true);
//    }


    function signalling(bytes32 /* _startEventTxReceiptRoot */, bytes calldata /* _startEvent */) external override  view {

    }

    function close(bytes32 /* _startEventTxReceiptRoot */, bytes calldata /* _startEvent */) external override  view {

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


    function crossBlockchainTransactionExists(uint256 _crossBlockchainTransactionId) public override view returns (bool) {
        return 0 != timeout[_crossBlockchainTransactionId];
    }

    function crossBlockchainTransactionTimeout(uint256 _crossBlockchainTransactionId) external override view returns (uint256) {
        return timeout[_crossBlockchainTransactionId];
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

//    function execute(address _targetContract, bytes memory _functionCall) private {
//        bool isSuccess;
//        bytes memory returnValueEncoded;
//        (isSuccess, returnValueEncoded) = _targetContract.call(_functionCall);
//        // TODO unlock contracts if failed and revert state, and indicate an error in the event below.
////        emit Segment(activeCallRootBlockchainId, activeCallCrossBlockchainTransactionId, activeCallsCallPath,
////            activeCallLockedContracts, returnValueEncoded);
//    }

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

    function failRootTransaction() private {
        timeout[activeCallCrossBlockchainTransactionId] = 0;
        previousCallResult[activeCallCrossBlockchainTransactionId] = FAILURE;
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