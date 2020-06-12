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

interface RegistrarInterface {

    /**
     * Add an account that can administer this contract.
     *
     * @param _admin Administrator address.
     */
    function addAdmin(address _admin) external;

    /**
     * Remove an account that can administer this contract.
     *
     * @param _admin Administrator address.
     */
    function removeAdmin(address _admin) external;



    /**
     * Add an account that can administer this contract.
     *
     * @param _admin Administrator address.
     */
    function addBlockchainAdmin(uint256 _blockchainId, address _admin) external;

    /**
     * Remove an account that can administer this contract.
     *
     * @param _admin Administrator address.
     */
    function removeBlockchainAdmin(uint256 _blockchainId, address _admin) external;

    /**
     * Add a blockchain that transaction roots can be sent from.
     *
     * @param _blockchainId The 256 bit identifier of the blockchain.
     * @param _signingAlgorithm The id of the signing algorithm used for this blockchain.
     *               0 is ECDSA using secp256k1 curve with KECCAK256.
     */
    function addBlockchain(uint256 _blockchainId, uint16 _signingAlgorithm) external;


    /**
     * Add a signer for the blockchain.
     */
    function addSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external;

    /**
     * Add a signer for the blockchain.
     */
    function removeSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external;

    /**
     * Set the signing threshold for the blockchain.
     */
    function setSignatureThreshold(uint256 _blockchainId, uint256 _threshold) external;


    /**
     * Verify signatures.
     */
    function verify(
        uint256 _blockchainId,
        address[] calldata signers,
        bytes32[] calldata sigR,
        bytes32[] calldata sigS,
        uint8[] calldata sigV,
        bytes calldata plainText) external;


    function adminArraySize() external view returns (uint256);

    function getAdmin(uint256 _index) external view returns (address);

    function isAdmin(address _mightBeAdmin) external view returns (bool);

}