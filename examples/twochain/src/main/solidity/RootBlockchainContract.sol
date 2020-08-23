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

import "../../../../../crossblockchaincontrol/src/main/solidity/CrossBlockchainControl.sol";
import "./OtherBlockchainContractInterface.sol";

contract RootBlockchainContract {
    CrossBlockchainControl private crossBlockchainControlContract;
    uint256 private otherBlockchainId;
    OtherBlockchainContractInterface private otherContract;
    uint256 public localValue;

    constructor (address _crossBlockchainControl, uint256 _otherBlockchainId, address _otherContract) public {
        crossBlockchainControlContract = CrossBlockchainControl(_crossBlockchainControl);
        otherBlockchainId = _otherBlockchainId;
        otherContract = OtherBlockchainContractInterface(_otherContract);
    }

    function setVal(uint256 _val) external {
        crossBlockchainControlContract.crossBlockchainCall(otherBlockchainId, address(otherContract),
            abi.encodeWithSelector(otherContract.setVal.selector, _val));
    }

    function incrementVal() external {
        crossBlockchainControlContract.crossBlockchainCall(otherBlockchainId, address(otherContract),
            abi.encodeWithSelector(otherContract.incrementVal.selector));
    }

    function getVal() external view returns(uint256) {
        return crossBlockchainControlContract.crossBlockchainCallReturnsUint256(otherBlockchainId, address(otherContract),
            abi.encodeWithSelector(otherContract.getVal.selector));
    }

//
//    function setFlag(bool _flag) external;
//    function getFlag() external view returns(bool);
//
//    function setValAndFlag(bool _flag, uint256 _val) external;
//    function getValAndFlag() external view returns(bool, uint256);
//
//    function setValues(uint256[] calldata _vals) external;
//    function getValue(uint256 _index) external view returns(uint256);



}
