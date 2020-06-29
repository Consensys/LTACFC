/*
 * Copyright 2020 ConsenSys AG.
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

import "../../../../registrar/src/main/solidity/Registrar.sol";

contract TxReceiptsRootStorage {
    Registrar registrar;

    // Mapping (blockchain Id => mapping(transaction receipt root) => bool)
    // The bool is true if the transaction receipt root exists for the blockchain
    mapping(uint256=>mapping(bytes32 => bool)) private txReceiptsRoots;


    constructor(address _registrar) public {
        registrar = Registrar(_registrar);
    }

    function addTxReceiptRoot(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes32 _txReceiptsRoot) external // override(RegistrarInterface)
    {

        bytes memory txReceiptsRootBytes = abi.encodePacked(_txReceiptsRoot);
        registrar.verify(_blockchainId, _signers, _sigR, _sigS, _sigV, txReceiptsRootBytes);

        txReceiptsRoots[_blockchainId][_txReceiptsRoot] = true;
    }


    function verify(
        uint256 /* _blockchainId */,
        bytes32 /* _txReceiptsRoot */
    ) external {

    }


    function containsTxReceiptRoot(
        uint256 _blockchainId,
        bytes32 _txReceiptsRoot) external //override(RegistrarInterface)
            view returns (bool){

        return (txReceiptsRoots[_blockchainId][_txReceiptsRoot]);
    }


}