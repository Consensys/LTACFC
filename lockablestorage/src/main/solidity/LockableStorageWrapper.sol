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
import "./LockableStorage.sol";

contract LockableStorageWrapper  {
    LockableStorage public storageContract;

    constructor (address _storageContract) public {
        storageContract = LockableStorage(_storageContract);
    }


    function setUint256(uint256 _key, uint256 _val) public {
        storageContract.setUint256(_key, _val);
    }

    function setBool(uint256 _key, bool _flag) public {
        storageContract.setUint256(_key, (_flag ? 1 : 0));
    }

    function setAddress(uint256 _key, address _address) public {
        storageContract.setUint256(_key, uint256(_address));
    }

    function setBytes(uint256 _key, bytes calldata _val) public {
        storageContract.setBytes(_key, _val);
    }


    function setArrayValue(uint256 _key, uint256 _index, uint256 _val) public {
        // Location of the key is the length.
        uint256 len = storageContract.getUint256(_key);
        require(_index < len);
        // Keccak256(_key) is the location of the array elements.
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + _index, _val);
    }

    function pushArrayValue(uint256 _key, uint256 _val) public {
        uint256 len = storageContract.getUint256(_key);
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + len, _val);
        storageContract.setUint256(_key, len+1);
    }

    function popArrayValue(uint256 _key) public {
        uint256 len = storageContract.getUint256(_key);
        require(len > 0);
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        storageContract.setUint256(uint256(startOfArrayLocation) + len, 0);
        storageContract.setUint256(_key, len-1);
    }

    function setMapValue(uint256 _key, uint256 _val) public {
        bytes32 index = keccak256(abi.encodePacked(_key, _key));
        storageContract.setUint256(uint256(index), _val);
    }


    function getUint256(uint256 _key) public view returns(uint256) {
        return storageContract.getUint256(_key);
    }

    function getBool(uint256 _key) public view returns(bool) {
        return storageContract.getUint256(_key) == 1 ? true : false;
    }

    function getAddress(uint256 _key) public view returns(address) {
        return address(storageContract.getUint256(_key));
    }

    function getBytes(uint256 _key) external view returns(bytes memory) {
        return storageContract.getBytes(_key);
    }

    function getArrayLength(uint256 _key) external view returns(uint256) {
        return storageContract.getUint256(_key);
    }

    function getArrayValue(uint256 _key, uint256 _index) external view returns(uint256) {
        uint256 len = storageContract.getUint256(_key);
        require(len > _index);
        bytes32 startOfArrayLocation = keccak256(abi.encodePacked(_key));
        return storageContract.getUint256(uint256(startOfArrayLocation) + _index);
    }

    function getMapValue(uint256 _key) external view returns(uint256) {
        bytes32 index = keccak256(abi.encodePacked(_key, _key));
        return storageContract.getUint256(uint256(index));
    }

}
