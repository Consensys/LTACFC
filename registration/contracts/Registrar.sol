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
pragma solidity >=0.4.23;

import "./RegistrarInterface.sol";

contract Registrar is RegistrarInterface {
    // Address of accounts who administer this contract and the offset in the array+1.
    mapping(address => uint256) private adminsMap;

    address[] private adminsArray;


    modifier onlyAdmin() {
        require(adminsMap[msg.sender] != 0);
        _;
    }


    constructor() public {
        adminsArray.push(msg.sender);
        adminsMap[msg.sender] = 1;
    }

    function addAdmin(address _admin) external override(RegistrarInterface) onlyAdmin {
        adminsArray.push(_admin);
        adminsMap[_admin] = adminsArray.length;
    }

    function removeAdmin(address _admin) external override(RegistrarInterface) onlyAdmin {
        uint256 arrayOfsPlusOne = adminsMap[_admin];
        require(arrayOfsPlusOne != 0);
        adminsMap[_admin] = 0;
        adminsArray[arrayOfsPlusOne - 1] = address(0);
    }


    function addBlockchainAdmin(uint256 _blockchainId, address _admin) external override(RegistrarInterface) {
    }

    function removeBlockchainAdmin(uint256 _blockchainId, address _admin) external override(RegistrarInterface) {
    }


    function addBlockchain(uint256 _blockchainId, uint16 _signingAlgorithm) external override(RegistrarInterface) {

    }


    function addSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external override(RegistrarInterface) {

    }

    function removeSignerPublicKeyAddress(uint256 _blockchainId, address _signerPublicKeyAddress) external override(RegistrarInterface) {

    }

    function setSignatureThreshold(uint256 _blockchainId, uint256 _threshold) external override(RegistrarInterface) {

    }


    function verify(
        uint256 _blockchainId,
        address[] calldata signers,
        bytes32[] calldata sigR,
        bytes32[] calldata sigS,
        uint8[] calldata sigV,
        bytes calldata plainText) external override(RegistrarInterface) {

    }



    function adminArraySize() external view override(RegistrarInterface) returns (uint256) {
        return adminsArray.length;
    }

    function getAdmin(uint256 _index) external view override(RegistrarInterface) returns (address)  {
        return adminsArray[_index];
    }

    function isAdmin(address _mightBeAdmin) external view override(RegistrarInterface) returns (bool)  {
        return adminsMap[_mightBeAdmin] != 0;
    }

}