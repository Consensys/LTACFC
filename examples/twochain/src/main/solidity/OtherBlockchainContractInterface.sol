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

interface OtherBlockchainContractInterface {
    function setVal(uint256 _val) external;
    function incrementVal() external;
    function setFlag(bool _flag) external;
    function setValAndFlag(bool _flag, uint256 _val) external;
    function setBytes(bytes calldata _val) external;
    function setArrayValues(uint256[] calldata _vals) external;
    function setArrayValue(uint256 _index, uint256 _val) external;
    function setMapValue(uint256 _key, uint256 _val) external;

    function getVal() external view returns(uint256);
    function getFlag() external view returns(bool);
    function getValAndFlag() external view returns(bool, uint256);
    function getBytes() external  view returns(bytes memory);
    function getArrayLength() external view returns(uint256);
    function getArrayValue(uint256 _index) external view returns(uint256);
    function getMapValue(uint256 _key) external view returns(uint256);
}
