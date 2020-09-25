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
    event Segment(uint256 _crossBlockchainTransactionId, bytes32 _hashOfCallGraph, uint256[] _callPath,
        address[] _lockedContracts, bytes _returnValue);
    event Signalling(uint256 _crossBlockchainTransactionId);
    event Close(uint256 _crossBlockchainTransactionId);

    event Dump(uint256 _bcId, address _addr, bytes _functionCall);

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
    bytes public activeCallGraph;
    uint256 public tempActiveTimeout;
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

        // TODO use transaction id in conjunction with call path to prevent replay attacks.
        activeCallCrossBlockchainTransactionId = BytesUtil.bytesToUint256(startEventData, 0);
        tempActiveTimeout = BytesUtil.bytesToUint256(startEventData, 0x20);
        // Skip the type field at 0x40.
        uint256 lenOfActiveCallGraph = BytesUtil.bytesToUint256(startEventData, 0x60);
        bytes memory callGraph = BytesUtil.slice(startEventData, 0x80, lenOfActiveCallGraph);
        activeCallGraph = callGraph;

        activeCallRootBlockchainId = _rootBlockchainId;
        activeCallsCallPath = _callPath;

        (uint256 targetBlockchainId, address targetContract, bytes memory functionCall) = extractTargetFromCallGraph(callGraph, _callPath);
        emit Dump(targetBlockchainId, targetContract, functionCall);


//        require(targetBlockchainId == myBlockchainId, "Target blockchain id does not match my blockchain id");
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



    function extractTargetFromCallGraph(bytes memory _callGraph, uint256[] calldata _callPath) private pure
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

    function execute(address _targetContract, bytes memory _functionCall) private {
        bool isSuccess;
        bytes memory returnValueEncoded;
        (isSuccess, returnValueEncoded) = _targetContract.call(_functionCall);
        // TODO unlock contracts if failed and revert state, and indicate an error in the event below.
//        emit Segment(activeCallRootBlockchainId, activeCallCrossBlockchainTransactionId, activeCallsCallPath,
//            activeCallLockedContracts, returnValueEncoded);
    }


}