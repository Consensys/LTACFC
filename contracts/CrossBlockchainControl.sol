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
 */
pragma solidity >=0.4.23;

contract CrossBlockchainControl {
    // Mapping of cross-blockchain transaction id to time-out block number.
    mapping (uint256=> uint256) public timeout;


    function start(uint256 _crossBlockchainTransactionId, uint256 _timeout) external {
        timeout[_crossBlockchainTransactionId] = _timeout;
        emit Start(_crossBlockchainTransactionId, _timeout);
    }

    function segment(uint256 _startEventBlockHash, bytes calldata _startEvent) external {

    }

    function root(uint256 _startEventBlockHash, bytes calldata _startEvent) external {

    }

    function signalling(uint256 _startEventBlockHash, bytes calldata _startEvent) external {

    }

    function close(uint256 _startEventBlockHash, bytes calldata _startEvent) external {

    }

    event Start(uint256 id, uint256 timeout);

}