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

import "../../../../blockheader/src/main/solidity/TxReceiptsRootStorageInterface.sol";
import "./CrossBlockchainControl.sol";


contract CbcTxRootTransfer is CrossBlockchainControl {
    TxReceiptsRootStorageInterface public txReceiptRootStorage;

    struct EventProof {
        uint256 blockchainId;
        address cbcContract;
        bytes32 txReceiptRoot;
        bytes encodedTxReceipt;
        uint256[] proofOffsets;
        bytes[] proof;
    }

    // This storage variable is used as temporary storage when supplying proofs. At present,
    // struct parameters containing arrays of bytes or bytes, or even arrays or arrays does not
    // work. To get around this, load information to be used into this variable using separate calls.
    EventProof[] eventParams;

    constructor(uint256 _myBlockchainId, address _txReceiptRootStorage) CrossBlockchainControl (_myBlockchainId){
        txReceiptRootStorage = TxReceiptsRootStorageInterface(_txReceiptRootStorage);
    }


    function callPrep(uint256 _blockchainId, address _cbcContract, bytes32 _txReceiptRoot,
        bytes calldata _encodedTxReceipt, uint256[] calldata _proofOffsets, bytes[] calldata _proof) public {
        uint256[] memory proofOffsets = new uint256[](_proofOffsets.length);
        for (uint256 i = 0; i < _proofOffsets.length; i++) {
            proofOffsets[i] = _proofOffsets[i];
        }
        bytes[] memory proof = new bytes[](_proof.length);
        for (uint256 i = 0; i < _proof.length; i++) {
            proof[i] = _proof[i];
        }
        EventProof memory call = EventProof(_blockchainId, _cbcContract, _txReceiptRoot, _encodedTxReceipt, proofOffsets, proof);
        eventParams.push(call);

        // This verification code doesn't need to be here - the verification happens in the function.
        // However, having the verify here helps with debug.
        txReceiptRootStorage.verify(
            _blockchainId,
            _txReceiptRoot,
            _encodedTxReceipt,
            _proofOffsets,
            _proof);
    }



    function segment(
        uint256 _rootBlockchainId, address _rootCBCContract,
        bytes32 _startEventTxReceiptRoot, bytes calldata _encodedStartTxReceipt,
        uint256[] calldata _proofOffsets, bytes[] calldata _proof, uint256[] calldata _callPath) external {


        // TODO require call path length >= 1

        txReceiptRootStorage.verify(_rootBlockchainId, _startEventTxReceiptRoot, _encodedStartTxReceipt,
            _proofOffsets, _proof);

        bytes memory startEventData;
        {
            bytes memory _encodedStartTxReceiptLocal = _encodedStartTxReceipt;
            RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceiptLocal));
            bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
            //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
            (, startEventData) = extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        }

        segmentProcessing(_rootBlockchainId, startEventData, _callPath);
    }


    /**
     * Root call, when transaction root transfer consensus is used.
     *
     * The parameter should be an array of Info[], with the array offset 0 being the start event,
     * and the other array elements being for segment events. Note that the segment events must be
     * in order of the functions called.
     *
    **/
    function root() external {
        //Verify the start event and all segment events.
        for (uint256 i = 0; i < eventParams.length; i++) {
            txReceiptRootStorage.verify(
                eventParams[i].blockchainId,
                eventParams[i].txReceiptRoot,
                eventParams[i].encodedTxReceipt,
                eventParams[i].proofOffsets,
                eventParams[i].proof);
        }

        uint256 startEventBlockchainId = eventParams[0].blockchainId;
        address startEventCbcAddress = eventParams[0].cbcContract;

        // Extract the start event from the RLP encoded receipt trie data.
        bytes memory _encodedStartTxReceiptLocal = eventParams[0].encodedTxReceipt;
        bytes memory startEventData = extractStartEventData(address(this), _encodedStartTxReceiptLocal);

        // Extract segment events
        bytes[] memory segmentEvents = new bytes[](eventParams.length - 1);
        for (uint256 i = 1; i < eventParams.length; i++) {
            segmentEvents[i-1] = extractSegmentEventData(eventParams[i].cbcContract, eventParams[i].encodedTxReceipt);
        }

        rootProcessing(startEventBlockchainId, startEventCbcAddress, startEventData, segmentEvents);

        delete eventParams;
    }


    /**
 * Signalling call: Commit or ignore updates + unlock any locked contracts.
 *
 * The parameter should be an array of Info[], with the array offset 0 being the root event,
 * and the other array elements being for segment events that have locked contracts.
 *
 * User rootPrep to set-up the parameters
 **/
    function signalling() external {
        //Verify the root event and all segment events.
        for (uint256 i = 0; i < eventParams.length; i++) {
            txReceiptRootStorage.verify(
                eventParams[i].blockchainId,
                eventParams[i].txReceiptRoot,
                eventParams[i].encodedTxReceipt,
                eventParams[i].proofOffsets,
                eventParams[i].proof);
        }

        // Extract the root event from the RLP encoded receipt trie data.
        bytes memory _encodedRootTxReceiptLocal = eventParams[0].encodedTxReceipt;
        bytes memory rootEventData = extractRootEventData(eventParams[0].cbcContract, _encodedRootTxReceiptLocal);

        // The fact that the event verified implies that the root blockchain id value can be trusted.
        uint256 rootBlockchainId = eventParams[0].blockchainId;

        // Extract segment events
        bytes[] memory segmentEvents = new bytes[](eventParams.length - 1);
        for (uint256 i = 1; i < eventParams.length; i++) {
            //Check that the blockchain Id for the segment was matches this contract's blockchain id.
            require(myBlockchainId == eventParams[i].blockchainId, "Not the correct blockchain id");

            // Ensure this is the cross-blockchain contract contract that generated the segment event.
            require(address(this) == eventParams[i].cbcContract, "Segment blockchain CBC contract was not this one");

            segmentEvents[i-1] = extractSegmentEventData(eventParams[i].cbcContract, eventParams[i].encodedTxReceipt);
        }
        signallingProcessing(rootBlockchainId, rootEventData, segmentEvents);

        delete eventParams;
    }



    function extractStartEventData(address _rootCBCContract, bytes memory _encodedStartTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedStartTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory startEventData;
        (, startEventData) = extractEvent(_rootCBCContract, START_EVENT_SIGNATURE, receiptBytes);
        return startEventData;
    }

    function extractSegmentEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory segmentEventData;
        (, segmentEventData) = extractEvent(_cBCContract, SEGMENT_EVENT_SIGNATURE, receiptBytes);
        return segmentEventData;
    }

    function extractRootEventData(address _cBCContract, bytes memory _encodedTxReceipt) private pure returns (bytes memory) {
        RLP.RLPItem[] memory keyAndReceipt = RLP.toList(RLP.toRLPItem(_encodedTxReceipt));
        bytes memory receiptBytes = RLP.toData(keyAndReceipt[1]);
        //        (RLP.RLPItem[] memory startEventTopics, bytes memory startEventData) =
        bytes memory rootEventData;
        (, rootEventData) = extractEvent(_cBCContract, ROOT_EVENT_SIGNATURE, receiptBytes);
        return rootEventData;
    }

}