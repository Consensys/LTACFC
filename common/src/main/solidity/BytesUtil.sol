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

abstract contract BytesUtil {

    // From stack overflow here: https://ethereum.stackexchange.com/questions/15350/how-to-convert-an-bytes-to-address-in-solidity
    function bytesToAddress(bytes memory bys) internal pure returns (address addr) {
        assembly {
            addr := mload(add(bys,20))
        }
    }

    // TODO find something faster than this.
    // From stack overflow here: https://ethereum.stackexchange.com/questions/7702/how-to-convert-byte-array-to-bytes32-in-solidity
    function bytesToBytes32(bytes calldata b, uint offset) internal pure returns (bytes32) {
        bytes32 out;

        for (uint i = 0; i < 32; i++) {
            out |= bytes32(b[offset + i] & 0xFF) >> (i * 8);
        }
        return out;
    }

    // Starting point was this, but with some modifications.
    // https://ethereum.stackexchange.com/questions/49185/solidity-conversion-bytes-memory-to-uint
    function bytesToUint256(bytes memory _b, uint256 _startOffset) internal pure returns (uint256) {
        require(_b.length >= _startOffset + 32, "slicing out of range");
        uint256 x;
        assembly {
            x := mload(add(_b, add(32, _startOffset)))
        }
        return x;
    }

    function bytesToUint64(bytes memory _b, uint256 _startOffset) internal pure returns (uint64) {
        require(_b.length >= _startOffset + 8, "slicing out of range");
        uint256 x;
        assembly {
            x := mload(add(_b, add(8, _startOffset)))
        }
        return uint64(x);
    }

}
