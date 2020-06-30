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

/**
 * Transaction receipt root data store.
 */
interface TxReceiptsRootStorageInterface {

    /**
     * Add a transaction receipt root to the transaction receipt root data store.
     *
     * Reverts in the following situations:
     * * If the number of signers and signatures do not match
     * * If any of the signers are not registered for the blockchain id
     * * The any signature in the array can not be verified using the corresponding signer address in the _signer array
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _signers Array of addresses that correspond to the public keys of the signers.
     * @param _sigR Array of R components of the signatures to be verified.
     * @param _sigS Array of S components of the signatures to be verified.
     * @param _sigV Array of V components of the signatures to be verified.
     * @param _txReceiptsRoot The transaction receipt root to add to the data store.
     */
    function addTxReceiptRoot(
        uint256 _blockchainId,
        address[] calldata _signers,
        bytes32[] calldata _sigR,
        bytes32[] calldata _sigS,
        uint8[] calldata _sigV,
        bytes32 _txReceiptsRoot) external;


    /**
     * Verify that a transaction receipt is part of the Merkle Patricia Trie that hashes to a transaction
     * receipt root that belongs to a blockchain.
     *
     * Reverts in the following situations:
     * * Transaction receipt has not been added for the blockchain.
     * * The proof does not prove that the transaction receipt is part of the Merkle Patricia Trie.
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _txReceiptsRoot The transaction receipt root to reference.
     * @param _txReceipt The value that is being proven to be part of the Merkle Patricia Trie of transaction receipts.
     * @param _proof The RLP encoded proof information.
     *
     * TODO: Document proof format.
     */
    function verify(uint256 _blockchainId, bytes32 _txReceiptsRoot, bytes calldata _txReceipt, bytes calldata _proof) external;


    /**
     * Check that a transaction receipt root has been added to the data store.
     *
     * @param _blockchainId Identifier of blockchain that the transaction receipt belongs to
     * @param _txReceiptsRoot The transaction receipt root to reference.
     * @return true if the transaction receipt has been stored in the data store for the blockchain id.
     */
    function containsTxReceiptRoot(uint256 _blockchainId, bytes32 _txReceiptsRoot) external view returns (bool);
}