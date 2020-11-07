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
package tech.pegasys.ltacfc.cbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;
import tech.pegasys.ltacfc.common.StatsHolder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CbcExecutionEngineTxReceiptRootTransfer extends AbstractCbcExecutionEngine {
  static final Logger LOG = LogManager.getLogger(CbcExecutionEngineTxReceiptRootTransfer.class);

  Map<BigInteger, CrossBlockchainControlTxReceiptRootTransfer> cbcContracts = new HashMap<>();
  Map<BigInteger, AnIdentity[]> allSigners = new HashMap<>();

  CrossBlockchainControlTxReceiptRootTransfer rootCbcContract;
  BigInteger rootBcId;

  TxReceiptRootTransferEventProof startProof;


  public CbcExecutionEngineTxReceiptRootTransfer(
      List<CrossBlockchainControlTxReceiptRootTransfer> cbcContracts, List<AnIdentity[]> signers) throws Exception {
    super(CrossBlockchainConsensus.TRANSACTION_RECEIPT_SIGNING);

    if (cbcContracts.size() == 0) {
      throw new Exception("Must have at least one blockchain (cbc contracts)");
    }
    if (signers.size() != cbcContracts.size()) {
      throw new Exception("Signers and contracts length must match");
    }

    this.rootCbcContract = cbcContracts.get(0);
    this.rootBcId = this.rootCbcContract.blockchainId;

    for (int i = 0; i < cbcContracts.size(); i++) {
      CrossBlockchainControlTxReceiptRootTransfer contract = cbcContracts.get(i);
      this.cbcContracts.put(contract.blockchainId, contract);
      this.allSigners.put(contract.blockchainId, signers.get(i));
    }
  }


  protected void startCall() throws Exception {
    TransactionReceipt startTxReceipt = this.rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, RlpEncoder.encode(this.callGraph));
    this.startProof = this.rootCbcContract.getStartEventProof(startTxReceipt);
    publishReceiptRootToAll(this.rootBcId, startProof.getTransactionReceiptRoot());
  }




  private void publishReceiptRootToAll(BigInteger publishingFrom,  byte[] transactionReceiptRoot) throws Exception {
    Set<BigInteger> blockchainIdsToPublishTo = this.cbcContracts.keySet();
    publishReceiptRoot(publishingFrom, transactionReceiptRoot, blockchainIdsToPublishTo);
  }

  // Add tx receipt root so event will be trusted.
  private void publishReceiptRoot(BigInteger publishingFrom,  byte[] transactionReceiptRoot, Set<BigInteger> blockchainsToPublishTo) throws Exception {
    for (BigInteger bcId: blockchainsToPublishTo) {
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcContracts.get(bcId);
      AnIdentity[] signers = this.allSigners.get(bcId);
      cbcContract.addTransactionReceiptRootToBlockchain(signers, publishingFrom, transactionReceiptRoot);
    }
  }



//      LOG.info("segment: getVal");
//      StatsHolder.log("segment: getVal");
//      List<BigInteger> getValCallPath = new ArrayList<>();
//      getValCallPath.add(BigInteger.ONE);
//      TransactionReceipt segGetValTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, getValCallPath);
//      TxReceiptRootTransferEventProof segGetValProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segGetValTxReceipt);
//      allSegmentProofs.add(segGetValProof);
//      // Add tx receipt root so event will be trusted.
//      bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());
//      rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());
//
//      if (simTradeWallet.someComplexBusinessLogicIfTrue) {
//        LOG.info("segment: setValues");
//        StatsHolder.log("segment: setValues");
//        List<BigInteger> setValuesCallPath = new ArrayList<>();
//        setValuesCallPath.add(BigInteger.TWO);
//        TransactionReceipt segSetValuesTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, setValuesCallPath);
//        TxReceiptRootTransferEventProof segSetValuesProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segSetValuesTxReceipt);
//        allSegmentProofs.add(segSetValuesProof);
//        signalSegProofs.add(segSetValuesProof);
//        // Add tx receipt root so event will be trusted.
//        bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
//        rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
//
//      } else {
//        LOG.info("segment: setVal");
//        StatsHolder.log("segment: setVal");
//        List<BigInteger> setValCallPath = new ArrayList<>();
//        setValCallPath.add(BigInteger.TWO);
//        TransactionReceipt segSetValTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, setValCallPath);
//        TxReceiptRootTransferEventProof segSetValProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segSetValTxReceipt);
//        allSegmentProofs.add(segSetValProof);
//        signalSegProofs.add(segSetValProof);
//        // Add tx receipt root so event will be trusted.
//        bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
//        rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
//      }
//
//      LOG.info("root");
//      TransactionReceipt rootTxReceipt = rootBlockchainCbcTxRootTransfer.root(startProof, allSegmentProofs);
//      TxReceiptRootTransferEventProof rootProof = rootBlockchainCbcTxRootTransfer.getRootEventProof(rootTxReceipt);
//      // Add tx receipt root so event will be trusted.
//      bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());
////    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());
//
//      LOG.info("signalling");
//      // Do a signal call on all blockchain that have had segment calls that have caused contracts to be locked.
//      bc2BlockchainCbcTxRootTransfer.signalling(rootProof, signalSegProofs);
//
//      success = rootBlockchainCbcTxRootTransfer.getRootEventSuccess();
//
//      rootBlockchainCbcTxRootTransfer.shutdown();
//      bc2BlockchainCbcTxRootTransfer.shutdown();
//      break;
//
//
//    case EVENT_SIGNING:
//      byte[] startEventData = rootBlockchainCbcSignedEvents.start(crossBlockchainTransactionId1, timeout, RlpEncoder.encode(callGraph));
//      SignedEvent signedStartEvent = new SignedEvent(new AnIdentity[]{signer},
//          rootBcId, rootBlockchainCbcContractAddress, AbstractCbc.START_EVENT_SIGNATURE, startEventData);
//
//      // Set of all segment event information needed for the root call.
//      List<SignedEvent> allSegmentEvents = new ArrayList<>();
//      // Set of all segments need for the signal call on Other Blockchain.
//      List<SignedEvent> signalSegEvents = new ArrayList<>();
//
//
//      LOG.info("segment: getVal");
//      StatsHolder.log("segment: getVal");
//      getValCallPath = new ArrayList<>();
//      getValCallPath.add(BigInteger.ONE);
//      byte[] segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, getValCallPath);
//      SignedEvent segGetValEvent = new SignedEvent(new AnIdentity[]{signer},
//          otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//      allSegmentEvents.add(segGetValEvent);
//
//      if (simTradeWallet.someComplexBusinessLogicIfTrue) {
//        LOG.info("segment: setValues");
//        StatsHolder.log("segment: setValues");
//        List<BigInteger> setValuesCallPath = new ArrayList<>();
//        setValuesCallPath.add(BigInteger.TWO);
//        segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, setValuesCallPath);
//        SignedEvent segSetValuesEvent = new SignedEvent(new AnIdentity[]{signer},
//            otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//        allSegmentEvents.add(segSetValuesEvent);
//        signalSegEvents.add(segSetValuesEvent);
//      } else {
//
//        LOG.info("segment: setVal");
//        StatsHolder.log("segment: setVal");
//        List<BigInteger> setValCallPath = new ArrayList<>();
//        setValCallPath.add(BigInteger.TWO);
//        segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, setValCallPath);
//        SignedEvent segSetValEvent = new SignedEvent(new AnIdentity[]{signer},
//            otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//        allSegmentEvents.add(segSetValEvent);
//        signalSegEvents.add(segSetValEvent);
//      }
//
//      LOG.info("root");
//      byte[] rootEventData = rootBlockchainCbcSignedEvents.root(signedStartEvent, allSegmentEvents);
//      SignedEvent rootEvent = new SignedEvent(new AnIdentity[]{signer},
//          rootBcId, rootBlockchainCbcContractAddress, AbstractCbc.ROOT_EVENT_SIGNATURE, rootEventData);
//
//      LOG.info("signalling");
//      // Do a signal call on all blockchain that have had segment calls that have caused contracts to be locked.
//      bc2BlockchainCbcSignedEvents.signalling(rootEvent, signalSegEvents);
//
//      success = rootBlockchainCbcSignedEvents.getRootEventSuccess();
//
//      rootBlockchainCbcSignedEvents.shutdown();
//      bc2BlockchainCbcSignedEvents.shutdown();
//      break;
//
//    default:
//      throw new RuntimeException("Unknown consensus type");
//  }
}
