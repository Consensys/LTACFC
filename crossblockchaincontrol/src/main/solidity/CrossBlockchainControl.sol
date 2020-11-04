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
// import "./CrossBlockchainControlInterface.sol";
import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "../../../../receipts/src/main/solidity/Receipts.sol";
import "../../../../registrar/src/main/solidity/Registrar.sol";
import "../../../../lockablestorage/src/main/solidity/LockableStorage.sol";


abstract contract CrossBlockchainControl is CbcLockableStorageInterface, Receipts {
    bytes32 constant internal START_EVENT_SIGNATURE = keccak256("Start(uint256,address,uint256,bytes)");
    bytes32 constant internal SEGMENT_EVENT_SIGNATURE = keccak256("Segment(uint256,bytes32,uint256[],address[],bool,bytes)");
    bytes32 constant internal ROOT_EVENT_SIGNATURE = keccak256("Root(uint256,bool)");


    event Start(uint256 _crossBlockchainTransactionId, address _caller, uint256 _timeout, bytes _callGraph);
    event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
        address[] _lockedContracts, bool _success, bytes _returnValue);
    event Root(uint256 _crossBlockchainTransactionId, bool _success);
    event Signalling(uint256 _rootBcId, uint256 _crossBlockchainTransactionId);

    event Call(
        uint256 _expectedBlockchainId, uint256 _actualBlockchainId,
        address _expectedContract, address _actualContract,
        bytes _expectedFunctionCall, bytes _actualFunctionCall,
        bytes _retVal);
    event NotEnoughCalls(uint256 _expectedNumberOfCalls, uint256 _actualNumberOfCalls);

    event Dump(uint256 _val1, bytes32 _val2, address _val3, bytes _val4);

//uint256 public override myBlockchainId;
    uint256 public myBlockchainId;

    // For Root Blockchain:
    // Mapping of cross-blockchain transaction id to transaction information.
    // 0: Never used.
    // 1: The transaction has completed and was successful.
    // 2: The transaction has completed and was not successful.
    // Otherwise: time-out for the transaction: as seconds since unix epoch.
    uint256 constant private NOT_USED = 0;
    uint256 constant private SUCCESS = 1;
    uint256 constant private FAILURE = 2;
//mapping (uint256=> uint256) public override transactionInformation;
    mapping (uint256=> uint256) public transactionInformation;



    // Storage variables that are stored for the life of a transaction. They need to
    // be available in storage as code calls back into this contract.
    // -----------------------------------------------------------------------------
    // The root blockchain for the currently executing cross-blockchain call. Used to detect if there is a
    // cross-blockchain call occurring and to determine the root blockchain id for the call locking a
    // contract.
    uint256 public activeCallRootBlockchainId;

    // The cross-blockchain transaction id of the currently executing cross-blokchain call. Used to determine
    // the transaction id causing a contract to be locked.
    uint256 public activeCallCrossBlockchainTransactionId;

    // The call graph currently being executed. This is needed to determine whether any
    // cross-blockchain calls from the current execution are valid.
    bytes public activeCallGraph;

    // The location within the call graph of the currently executing call segment.
    uint256[] private activeCallsCallPath;

    // The values to be returned to function calls in the currently executing call segment.
    bytes[] private activeCallReturnValues;

    // The index into the activeCallReturnValues. That is, which return value should be returned
    // to the next function call from the currently executing call segment.
    uint256 private activeCallReturnValuesIndex;

    // The set of contracts locked by the current call segment.
    // Map to ensure contract addresses aren't added twice.
    mapping(address => bool) private activeCallLockedContractsMap;
    // List of locked contracts.
    address[] private activeCallLockedContracts;

    // Indicates the current call segment has failed.
    bool private activeCallFailed;



    constructor(uint256 _myBlockchainId) {
        myBlockchainId = _myBlockchainId;
    }


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external {
        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        require(transactionInformation[_crossBlockchainTransactionId] == NOT_USED, "Transaction already registered");
        uint256 transactionTimeoutSeconds = _timeout + block.timestamp;
        transactionInformation[_crossBlockchainTransactionId] = transactionTimeoutSeconds;

        emit Start(_crossBlockchainTransactionId, msg.sender, transactionTimeoutSeconds, _callGraph);
    }


    function segmentProcessing(
        uint256 _rootBlockchainId, bytes memory _startEventData, uint256[] calldata _callPath) internal {

        // The tx.origin needs to be the same on all blockchains that are party to the
        // cross-blockchain transaction. As such, ensure start is only called from an
        // EOA.
        require(tx.origin == msg.sender, "Start must be called from an EOA");

        // TODO require call path length >= 1



        // TODO use transaction id in conjunction with call path to prevent replay attacks.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(_startEventData, 0);
        address startCaller = BytesUtil.bytesToAddress1(_startEventData, 0x20);
        require(startCaller == tx.origin, "EOA does not match start event");
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(_startEventData, 0x80);

        bytes memory callGraph = BytesUtil.slice(_startEventData, 0xA0, lenOfActiveCallGraph);
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




    function rootProcessing(
        uint256 _startEventBlockchainId, address _startEventCbcContract,
        bytes memory _startEventData, bytes[] memory _segmentEvents) internal {
        //Check that tx.origin == msg.sender. This call must be issued by an EOA. This is required so
        // that we can check that the tx.origin for this call matches what is in the start event.
        require(tx.origin == msg.sender, "Transaction must be instigated by an EOA");

        //Check that the blockchain Id that can be used with the transaction receipt for verification matches this
        // blockchain. That is, check that this is the root blockchain.
        require(myBlockchainId == _startEventBlockchainId, "This is not the root blockchain");
        activeCallRootBlockchainId = myBlockchainId;

        // Ensure this is the cross-blockchain contract contract that generated the start event.
        require(address(this) == _startEventCbcContract, "Root blockchain CBC contract was not this one");

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
    function signallingProcessing(uint256 _rootBlockchainId, bytes memory _rootEventData, bytes[] memory _segmentEvents) internal {

        // Extract information from the root event.
        uint256 rootEventCrossBlockchainTxId = BytesUtil.bytesToUint256(_rootEventData, 0);
        uint256 success = BytesUtil.bytesToUint256(_rootEventData, 0x20);

        // Check that all of the Segment Events are for the same transaction id, and are for this blockchain.
        for (uint256 i = 0; i < _segmentEvents.length; i++) {
            // Recall Segment event is defined as:
            // event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
            //        address[] _lockedContracts, bool _success, bytes _returnValue);
            uint256 segmentEventCrossBlockchainTxId = BytesUtil.bytesToUint256(_segmentEvents[i], 0);
            // Check that the cross blockchain transaction id is the same for the root and the segment event.
            require(rootEventCrossBlockchainTxId == segmentEventCrossBlockchainTxId);
            // TODO check the Root Blockchain id indicated in the segment matches the one from the root transaction.


            // TODO Check that the cross chain transaction id for the root blockchain id is in use on this blockchain but has not been added to the completed list.

            // For each address indicated in the Segment Event as being locked, Commit or Ignore updates
            // according to what the Root Event says.
            uint256 locationOfLockedContracts = BytesUtil.bytesToUint256(_segmentEvents[i], 0x60);
            emit Dump(locationOfLockedContracts, bytes32(0), address(0), _segmentEvents[i]);
            uint256 numElementsOfArray = BytesUtil.bytesToUint256(_segmentEvents[i], locationOfLockedContracts);
            emit Dump(numElementsOfArray, bytes32(0), address(0), _segmentEvents[i]);
            for (uint256 j = 0; j < numElementsOfArray; j++) {
                address lockedContractAddr = BytesUtil.bytesToAddress1(_segmentEvents[i], locationOfLockedContracts + 0x20 + 0x20 * j);
                emit Dump(0, bytes32(0), lockedContractAddr, _segmentEvents[i]);
                LockableStorage lockedContract = LockableStorage(lockedContractAddr);
                // Check that the contract really has been locked by this transaction.
                require(lockedContract.locked());
                require(lockedContract.lockedByRootBlockchainId() == _rootBlockchainId);
                require(lockedContract.lockedByTransactionId() == rootEventCrossBlockchainTxId);
                lockedContract.finalise(success != 0);
            }
        }

        emit Signalling(_rootBlockchainId, rootEventCrossBlockchainTxId);
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