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


interface CrossBlockchainControlInterface {

    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external;

    function segment(uint256 /*_startEventBlockHash*/, bytes calldata _startEvent, bytes calldata /*_proof*/, uint256[] calldata _callPath) external;

    function signalling(uint256 /* _startEventBlockHash */, bytes calldata /* _startEvent */) external view;

    function close(uint256 /* _startEventBlockHash */, bytes calldata /* _startEvent */) external view;

    function crossBlockchainCall(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external;


    function crossBlockchainCallReturnsUint256(uint256 /* _blockchain */, address /* _contract */, bytes calldata /* _functionCallData */) external view returns (uint256);

    // Called by a provisional storage contract indicating the contract needs to be locked.
    function lockContract(address _contractToLock) external;

    function crossBlockchainTransactionExists(uint256 _crossBlockchainTransactionId) external view returns (bool);

    function crossBlockchainTransactionTimeout(uint256 _crossBlockchainTransactionId) external view returns (uint256);

    /**
     * @return false if the current transaction execution is part of a cross-blockchain call\.
     */
    function isSingleBlockchainCall() external view returns (bool);

    function getActiveCallRootBlockchainId() external view returns (uint256);

    function getActiveCallCrossBlockchainTransactionId() external view returns (uint256);

    // Accessor functions for public variables.
    function myBlockchainId() external view returns(uint256);
    function timeout(uint256) external view returns(uint256);
    function callGraphs(uint256) external view returns(bytes memory);
}