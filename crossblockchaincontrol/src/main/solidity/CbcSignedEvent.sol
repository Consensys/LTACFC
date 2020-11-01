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
 *
 * SPDX-License-Identifier: Apache-2.0
 */
pragma solidity >=0.7.1;
pragma experimental ABIEncoderV2;

import "./CrossBlockchainControl.sol";
import "../../../../registrar/src/main/solidity/Registrar.sol";


contract CbcSignedEvent is CrossBlockchainControl {
    Registrar registrar;

    constructor(uint256 _myBlockchainId, address _registrar) CrossBlockchainControl(_myBlockchainId){
        registrar = Registrar(_registrar);
    }

//    function rootEventSigning(uint256[] calldata _blockchainId, address[] calldata _cbcContract, bytes32[] calldata _eventSignature, bytes[] calldata _eventParameters,
//        uint256[] calldata _signatureIndex,
//        address[] calldata _signers, bytes32[] calldata _sigR, bytes32[] calldata _sigS, uint8[] calldata _sigV) external {
//
//        {
//        checkSameLength(_blockchainId, _cbcContract, _eventSignature, _eventParameters, _signatureIndex);
//        }
//        for (uint256 i = 0; i < _blockchainId.length; i++) {
//            bytes memory signedPlainText = abi.encodePacked(_blockchainId[i], _cbcContract[i], _eventSignature[i], _eventParameters[i]);
//            // TODO this implies all signers are for all blockchains
//            // Use _signatureIndex to define sub-ranges within the arrays for each blockchain
//            registrar.verify(_blockchainId[i], _signers, _sigR, _sigS, _sigV, signedPlainText);
//        }
//
//        // TODO check that eventSignature[0] is START
//        // TODO check that eventSignature[] is SEGMENT
//
//        bytes[] memory segmentEvents = new bytes[](_eventParameters.length - 1);
//        for (uint256 i = 1; i < _eventParameters.length; i++) {
//            segmentEvents[i-1] = _eventParameters[i];
//        }
//
//        rootProcessing(_blockchainId[0], _cbcContract[0], _eventParameters[0], segmentEvents);
//    }


    function checkSameLength(
        uint256[] calldata _blockchainId, address[] calldata _cbcContract,
        bytes32[] calldata _eventSignature, bytes[] calldata _eventParameters,
        uint256[] calldata _signatureIndex) pure private {
        require(_blockchainId.length == _cbcContract.length, "Length of blockchainId and cbcContract do not match");
        require(_blockchainId.length == _eventSignature.length, "Length of blockchainId and eventSignature do not match");
        require(_blockchainId.length == _eventParameters.length, "Length of blockchainId and eventParameters do not match");
        require(_blockchainId.length == _signatureIndex.length, "Length of blockchainId and signatureIndex  do not match");
    }
}