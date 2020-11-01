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

    function root(bytes[] calldata _signedEventInfo, bytes[] calldata _signature) external {
        // The minimum number of events is 1: start and no segment, used ot end timed-out a
        // cross-blockchain transactions.
        uint256 numEvents = _signedEventInfo.length;
        require(numEvents > 0, "Must be at least one event");
        require(numEvents == _signature.length, "Number of events and signatures do not match");

        bytes memory startEventData;
        bytes[] memory segmentEvents = new bytes[](numEvents - 1);
        uint256 rootBlockchainId;
        address startCbcContract;


        for (uint256 i = 0; i < numEvents; i++) {
            bytes memory signedEventInfo = _signedEventInfo[i];
            uint256 blockchainId;
            bytes memory eventData;
            address cbcContract;
            bytes32 eventSignature;
            (blockchainId, cbcContract, eventSignature, eventData) = extractEventInfo(signedEventInfo);

            if (i == 0) {
                require(eventSignature == START_EVENT_SIGNATURE, "First event was not START");
                rootBlockchainId = blockchainId;
                startCbcContract = cbcContract;
                startEventData = eventData;
            }
            else {
                require(eventSignature == SEGMENT_EVENT_SIGNATURE, "Subsequent events were not SEGMENT");
                segmentEvents[i-1] = eventData;
            }
            verifyEventSignature(blockchainId, eventData, _signature[i]);
        }


        rootProcessing(rootBlockchainId, startCbcContract, startEventData, segmentEvents);
    }


    function extractEventInfo(bytes memory _signedEventInfo) private pure
            returns(uint256 blockchainId, address cbcAddress, bytes32 eventSignature, bytes memory eventData){
        RLP.RLPItem[] memory signedEventInfo = RLP.toList(RLP.toRLPItem(_signedEventInfo));
        blockchainId = BytesUtil.bytesToUint256(RLP.toData(signedEventInfo[0]), 0);
        cbcAddress = BytesUtil.bytesToAddress(RLP.toData(signedEventInfo[1]));
        eventSignature = BytesUtil.bytesToBytes32(RLP.toData(signedEventInfo[2]), 0);
        eventData = RLP.toData(signedEventInfo[3]);
    }



    function verifyEventSignature(uint256 _blockchainId, bytes memory _eventInfo, bytes memory _signature) private view {
        // The code is arranged a little unusually to reduce the number of in-scope local variables so code will compile.
        address[] memory signers;
        bytes32[] memory sigRs;
        bytes32[] memory sigSs;
        uint8[] memory sigVs;

        {
            RLP.RLPItem[] memory signature = RLP.toList(RLP.toRLPItem(_signature));
            RLP.RLPItem[] memory signersRlp;
            signersRlp = RLP.toList(signature[0]);
            uint256 length = signersRlp.length;

            signers = new address[](length);
            RLP.RLPItem[] memory sigRsRlp = RLP.toList(signature[1]);
            sigRs = new bytes32[](length);
            RLP.RLPItem[] memory sigSsRlp = RLP.toList(signature[2]);
            sigSs = new bytes32[](length);
            RLP.RLPItem[] memory sigVsRlp = RLP.toList(signature[3]);
            sigVs = new uint8[](length);

            require(length == sigRs.length, "Length of sigR and signers does not match");
            require(length == sigSs.length, "Length of sigS and signers does not match");
            require(length == sigVs.length, "Length of sigV and signers does not match");

            for (uint256 i = 0; i < length; i++) {
                signers[i] = BytesUtil.bytesToAddress(RLP.toData(signersRlp[i]));
                sigRs[i] = BytesUtil.bytesToBytes32(RLP.toData(sigRsRlp[i]), 0);
                sigSs[i] = BytesUtil.bytesToBytes32(RLP.toData(sigSsRlp[i]), 0);
                sigVs[i] = BytesUtil.bytesToUint8(RLP.toData(sigVsRlp[i]), 0);
            }
        }

        registrar.verify(_blockchainId, signers, sigRs, sigSs, sigVs, _eventInfo);
    }
}