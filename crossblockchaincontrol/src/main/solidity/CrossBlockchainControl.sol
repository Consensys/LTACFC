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

contract CrossBlockchainControl {
    // Mapping of cross-blockchain transaction id to time-out block time stamp.
    mapping (uint256=> uint256) public timeout;
    // Mapping of cross-blockchain transaction id to call graphs.
    mapping (uint256=> bytes) public callGraphs;


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes calldata _callGraph) external {
        require(crossBlockchainTransactionExists(_crossBlockchainTransactionId) == false);
        timeout[_crossBlockchainTransactionId] = _timeout;
        callGraphs[_crossBlockchainTransactionId] = _callGraph;
        emit Start(_crossBlockchainTransactionId, _timeout, _callGraph);
    }

    function segment(uint256 _startEventBlockHash, bytes calldata _startEvent) external view {
//        require(crossBlockchainTransactionExists(_crossBlockchainTransactionId));

    }

    function root(uint256 _startEventBlockHash, bytes calldata _startEvent) external view {

    }

    function signalling(uint256 _startEventBlockHash, bytes calldata _startEvent) external view {

    }

    function close(uint256 _startEventBlockHash, bytes calldata _startEvent) external view {

    }

    function crossBlockchainTransactionExists(uint256 _crossBlockchainTransactionId) public view returns (bool) {
        return 0 != timeout[_crossBlockchainTransactionId];
    }

    function crossBlockchainTransactionTimeout(uint256 _crossBlockchainTransactionId) external view returns (uint256) {
        return timeout[_crossBlockchainTransactionId];
    }


    event Start(uint256 _crossBlockchainTransactionId, uint256 _timeout, bytes _callGraph);

}