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
pragma solidity >=0.6.9;
pragma experimental ABIEncoderV2;

import "./CrossBlockchainControlInterface.sol";
import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "../../../../receipts/src/main/solidity/Receipts.sol";


contract CrossBlockchainControl is CrossBlockchainControlInterface, Receipts {
    bytes32 constant private START_EVENT_SIGNATURE = keccak256("Start(uint256,uint256,bytes)");


    event Start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes _callGraph);
    event Segment(uint256 _rootBlockchain, uint256 _crossBlockchainTransactionId, uint256[] _callPath,
        address[] _lockedContracts, bytes _returnValue);
    event Signalling(uint256 _crossBlockchainTransactionId);
    event Close(uint256 _crossBlockchainTransactionId);


    uint256 public override myBlockchainId;
    TxReceiptsRootStorageInterface public txReceiptRootStorage;

    // Mapping of cross-blockchain transaction id to time-out block time stamp.
    mapping (uint256=> uint256) public override  timeout;
    // Mapping of cross-blockchain transaction id to call graphs, for calls instigated from this blockchain.
    mapping (uint256=> bytes) public override  callGraphs;

    // Storage variables that are just stored for the life of a transaction. They need to
    // be available in storage as code calls back into this contract.
    // TODO some of these will be able to be stored in memory
    uint256 public activeCallRootBlockchainId;
    uint256 public activeCallCrossBlockchainTransactionId;
    bytes public activeCall;
//    uint256 public tempActiveTimeout;
    uint256[] private activeCallsCallPath;
    mapping(address => bool) private activeCallLockedContractsMap;

    address[] private activeCallLockedContracts;


    constructor(uint256 _myBlockchainId, address _txReceiptRootStorage) public {
        myBlockchainId = _myBlockchainId;
        txReceiptRootStorage = TxReceiptsRootStorageInterface(_txReceiptRootStorage);
    }

    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external override {
        require(crossBlockchainTransactionExists(_crossBlockchainTransactionId) == false, "Transaction already registered");
        timeout[_crossBlockchainTransactionId] = _timeout;
        callGraphs[_crossBlockchainTransactionId] = _callGraph;
        emit Start(_crossBlockchainTransactionId, _timeout, _callGraph);
    }


    function segment(
        uint256 _rootBlockchainId, address _rootCBCContract,
        bytes32 _startEventTxReceiptRoot, bytes calldata _encodedStartTxReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof, uint256[] calldata _callPath) external override {

        txReceiptRootStorage.verify(_rootBlockchainId, _startEventTxReceiptRoot, _encodedStartTxReceipt,
            _proofOffsets, _proof);

        bytes memory _encodedStartTxReceiptLocal = _encodedStartTxReceipt;
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceiptLocal));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
            extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);

        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(startEventData, 0);
        //tempActiveTimeout = BytesUtil.bytesToUint256(startEventData, 0x20);
        activeCall = startEventData;

//
//        activeCallRootBlockchainId = extractFromStartEventRootBlockchainId(startEvent);
//        require(activeCallRootBlockchainId == _rootBlockchainId);
//
//        uint256 targetBlockchainId = extractFromStartEventTargetBlockchainId(startEvent, _callPath);
//        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");
//
//        activeCallCrossBlockchainTransactionId = extractFromStartEventTransactionId(_startEvent);
//        // TODO use transaction id in conjunction with callPath to prevent replay attacks
        activeCallsCallPath = _callPath;
//
//        address targetContract = extractFromStartEventTargetContract(_startEvent, _callPath);
//        bytes memory functionCall = extractFromStartEventFunctionCall(_startEvent, _callPath);
//
//        execute(targetContract, functionCall);
//
//        delete activeCallRootBlockchainId;
    }

    function signalling(bytes32 /* _startEventTxReceiptRoot */, bytes calldata /* _startEvent */) external override  view {

    }

    function close(bytes32 /* _startEventTxReceiptRoot */, bytes calldata /* _startEvent */) external override  view {

    }

    function crossBlockchainCall(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override {
        // Check that this function call should occur and register if this is an error.

    }


    function crossBlockchainCallReturnsUint256(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external override view returns (uint256){
        // Check that this function call should occur and register if this is an error.

    }

    // Called by a provisional storage contract indicating the contract needs to be locked.
    function lockContract(address _contractToLock) external override {
        if (activeCallLockedContractsMap[_contractToLock] == false) {
            activeCallLockedContracts.push(_contractToLock);
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


    function extractFromStartEventRootBlockchainId(bytes calldata /* _startEvent */) private pure returns (uint256) {
        // TODO
        return 0;
    }

    function extractFromStartEventRootTransactionId(bytes calldata /* _startEvent */) private pure returns (uint256) {
        // TODO
        return 0;
    }

    function extractFromStartEventTargetBlockchainId(bytes calldata /* _startEvent */, uint256[] calldata /* _callPath */) private pure returns (uint256) {
        // TODO
        return 0;
    }

    function extractFromStartEventTargetAddress(bytes calldata /* _startEvent */, uint256[] calldata /* _callPath */) private pure returns (address) {
        // TODO
        return address(0);
    }

    function extractFromStartEventFunctionCall(bytes calldata /* _startEvent */, uint256[] calldata /* _callPath */) private pure returns (bytes memory) {
       // TODO
       return new bytes(0);
    }

    function extractFromStartEventTransactionId(bytes calldata /* _startEvent */ ) private pure returns (uint256) {
       // TODO
       return uint256(0);
    }


    function extractFromStartEventTargetContract(bytes calldata /* _startEvent */, uint256[] calldata /* _callPath */) private pure returns (address) {
        // TODO
        return address(0);
    }

    function execute(address targetContract, bytes memory functionCall) private {
        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = targetContract.call(functionCall);
        // TODO unlock contracts if failed and revert state, and indicate an error in the event below.
        emit Segment(activeCallRootBlockchainId, activeCallCrossBlockchainTransactionId, activeCallsCallPath,
            activeCallLockedContracts, returnValueEncoded);
    }


}