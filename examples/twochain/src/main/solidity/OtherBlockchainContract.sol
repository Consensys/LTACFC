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
import "./OtherBlockchainContractInterface.sol";
import "./LockableStorage.sol";

contract OtherBlockchainContract is OtherBlockchainContractInterface {

    // TODO add a map to this example.
    uint256 constant private keyForFlag = 0;
    uint256 constant private keyForVal = 1;
    uint256 constant private keyForBytes = 2;
    uint256 constant private keyForMap = 3;
    uint256 constant private keyForValsArrayLength = 1000;
    uint256 constant private keyForValsArrayStart = 1001;


    LockableStorage storageContract;

    constructor (address _storageContract) public {
        storageContract = LockableStorage(_storageContract);
    }


    function setVal(uint256 _val) public override {
        storageContract.setUint256(keyForVal, _val);
    }

    function incrementVal() external override {
        uint256 aVal = storageContract.getUint256(keyForVal);
        aVal++;
        storageContract.setUint256(keyForVal, aVal);
    }


    function setFlag(bool _flag) public override {
        storageContract.setBool(keyForFlag, _flag);
//        try storageContract.setBool(keyForFlag, _flag) {
//        } catch Error(string memory reason) {
//            revert(reason);
//        } catch (bytes memory) {
//            revert("Low level fault");
//        }
    }

    function setValAndFlag(bool _flag, uint256 _val) external override {
        setFlag(_flag);
        setVal(_val);
    }

    function setBytes(bytes calldata _val) external override {
        storageContract.setBytes(keyForBytes, _val);
    }

    // Note that if the array was previously longer than the new length, the
    // array elements past the length of the new array are not removed.
    // To do this, call setArrayValue(index, 0).
    function setArrayValues(uint256[] calldata _vals) external override {
        storageContract.setUint256(keyForValsArrayLength, _vals.length);
        for (uint256 i=0; i<_vals.length; i++) {
            storageContract.setUint256(keyForValsArrayStart + i, _vals[i]);
        }
    }

    function setArrayValue(uint256 _index, uint256 _val) external override {
        storageContract.setUint256(keyForValsArrayStart + _index, _val);
    }

    function setMapValue(uint256 _key, uint256 _val) external override {
        bytes32 index = keccak256(abi.encodePacked(keyForMap, _key));
        storageContract.setUint256(uint256(index), _val);
    }


    function getVal() public override view returns(uint256) {
        return storageContract.getUint256(keyForVal);
    }

    function getFlag() public override view returns(bool) {
        return storageContract.getBool(keyForFlag);
    }

    function getValAndFlag() external override view returns(bool, uint256) {
        return (getFlag(), getVal());
    }

    function getBytes() external override view returns(bytes memory) {
        return storageContract.getBytes(keyForBytes);
    }

    function getArrayLength() external override view returns(uint256) {
        return storageContract.getUint256(keyForValsArrayLength);
    }

    function getArrayValue(uint256 _index) external override view returns(uint256) {
        return storageContract.getUint256(keyForValsArrayStart + _index);
    }

    function getMapValue(uint256 _key) external override view returns(uint256) {
        bytes32 index = keccak256(abi.encodePacked(keyForMap, _key));
        return storageContract.getUint256(uint256(index));
    }

}
