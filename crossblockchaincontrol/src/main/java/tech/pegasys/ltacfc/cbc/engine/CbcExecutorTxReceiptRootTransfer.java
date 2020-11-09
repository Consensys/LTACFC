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
package tech.pegasys.ltacfc.cbc.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.cbc.CrossBlockchainControlTxReceiptRootTransfer;
import tech.pegasys.ltacfc.cbc.TxReceiptRootTransferEventProof;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;
import tech.pegasys.ltacfc.common.Tuple;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CbcExecutorTxReceiptRootTransfer extends AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(CbcExecutorTxReceiptRootTransfer.class);

  // The maximum number of calls that can be done from any one function. The value
  // has been set to an aritrarily largish number. If people write complicated
  // functions that have a 1000 calls, or write functions that have loops and
  // do many cross-blockchain function calls, then this number might need to be made larger.
  private static final BigInteger MAX_CALLS_FROM_ONE_FUNCTION = BigInteger.valueOf(1000);

  protected static final BigInteger ROOT_CALL_MAP_KEY = calculateRootCallMapKey();
  private static BigInteger calculateRootCallMapKey() {
    List<BigInteger> rootCallPath = new ArrayList<BigInteger>();
    rootCallPath.add(BigInteger.ZERO);
    return callPathToMapKey(rootCallPath);
  }

  TxReceiptRootTransferEventProof startProof;
  TxReceiptRootTransferEventProof rootProof;


  // Key for this map is the call path of the caller.
  Map<BigInteger, List<TxReceiptRootTransferEventProof>> segmentProofs = new HashMap<>();

  // Key for this map is the blockchain id that the segment occurred on.
  Map<BigInteger, List<TxReceiptRootTransferEventProof>> segmentProofsWithLockedContracts = new HashMap<>();

  public CbcExecutorTxReceiptRootTransfer(CbcManager cbcManager) throws Exception {
    super(CrossBlockchainConsensus.TRANSACTION_RECEIPT_SIGNING, cbcManager);
  }


  protected void startCall() throws Exception {
    CrossBlockchainControlTxReceiptRootTransfer rootCbcContract = this.cbcManager.getCbcContractTxRootTransfer(this.rootBcId);

    TransactionReceipt startTxReceipt = rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, this.callGraph);
    this.startProof = rootCbcContract.getStartEventProof(startTxReceipt);
    publishReceiptRootToAll(this.rootBcId, this.startProof.getTransactionReceiptRoot());
  }

  protected void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception {
    if (callPath.size() == 0) {
      throw new Exception("Invalid call path length for segment: " + callPath.size());
    }

    BigInteger mapKey = callPathToMapKey(callPath);

    CrossBlockchainControlTxReceiptRootTransfer segmentCbcContract = this.cbcManager.getCbcContractTxRootTransfer(blockchainId);

    List<TxReceiptRootTransferEventProof> proofs = this.segmentProofs.get(mapKey);
    TransactionReceipt segTxReceipt = segmentCbcContract.segment(this.startProof, proofs, callPath);

    TxReceiptRootTransferEventProof segProof = segmentCbcContract.getSegmentEventProof(segTxReceipt);

    // Add the proof for the call that has just occurred to the map so it can be accessed when the next
    BigInteger parentMapKey = determineMapKeyOfCaller(callPath);
    proofs = this.segmentProofs.computeIfAbsent(parentMapKey, k -> new ArrayList<>());
    proofs.add(segProof);

    // Add the locked contracts as a result of the segment to the list of locked contracts.
    List<TxReceiptRootTransferEventProof> proofsOfSegmnentsWithLockedContracts =
        this.segmentProofsWithLockedContracts.computeIfAbsent(blockchainId, k -> new ArrayList<>());
    proofsOfSegmnentsWithLockedContracts.add(segProof);


    // Segments proofs need to be available on the blockchain they executed on (for the
    // Signalling call), and on the blockchain that the contract that called this contract
    // resides on (for the Root or Segment call).
    Set<BigInteger> blockchainsToPublishTo = new HashSet<>();
    blockchainsToPublishTo.add(blockchainId);
    blockchainsToPublishTo.add(callerBlockchainId);
    publishReceiptRoot(blockchainId, segProof.getTransactionReceiptRoot(), blockchainsToPublishTo);
  }


  protected void root() throws Exception {
    CrossBlockchainControlTxReceiptRootTransfer rootCbcContract = this.cbcManager.getCbcContractTxRootTransfer(this.rootBcId);
    List<TxReceiptRootTransferEventProof> proofs = this.segmentProofs.get(ROOT_CALL_MAP_KEY);
    TransactionReceipt rootTxReceipt = rootCbcContract.root(this.startProof, proofs);
    this.rootProof = rootCbcContract.getRootEventProof(rootTxReceipt);

    this.success = rootCbcContract.getRootEventSuccess();
    publishReceiptRootToAll(this.rootBcId, this.rootProof.getTransactionReceiptRoot());
  }

  protected void doSignallingCalls() throws Exception {
    for (BigInteger blockchainId: this.segmentProofsWithLockedContracts.keySet()) {
      List<TxReceiptRootTransferEventProof> segProofsLockedContractsCurrentBlockchain =
          this.segmentProofsWithLockedContracts.get(blockchainId);
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(blockchainId);
      cbcContract.signalling(this.rootProof, segProofsLockedContractsCurrentBlockchain);
    }


  }


  private void publishReceiptRootToAll(BigInteger publishingFrom,  byte[] transactionReceiptRoot) throws Exception {
    Set<BigInteger> blockchainIdsToPublishTo = this.cbcManager.getAllBlockchainIds();
    publishReceiptRoot(publishingFrom, transactionReceiptRoot, blockchainIdsToPublishTo);
  }

  // Add tx receipt root so event will be trusted.
  private void publishReceiptRoot(BigInteger publishingFrom,  byte[] transactionReceiptRoot, Set<BigInteger> blockchainsToPublishTo) throws Exception {
    for (BigInteger bcId: blockchainsToPublishTo) {
      CrossBlockchainControlTxReceiptRootTransfer cbcContract = this.cbcManager.getCbcContractTxRootTransfer(bcId);
      AnIdentity[] signers = this.cbcManager.getSigners(bcId);
      cbcContract.addTransactionReceiptRootToBlockchain(signers, publishingFrom, transactionReceiptRoot);
    }
  }



  /**
   * Determine a key that can be used for a map that uniquely identifies the call path's
   * caller.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  private BigInteger determineMapKeyOfCaller(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      return BigInteger.ZERO;
    }
    else {
      List<BigInteger> parentCallPath = new ArrayList<>(callPath);

      BigInteger bottomOfCallPath = callPath.get(callPath.size() - 1);
      if (bottomOfCallPath.compareTo(BigInteger.ZERO) == 0) {
        parentCallPath.remove(parentCallPath.size() - 1);
      }
      parentCallPath.set(parentCallPath.size() - 1, BigInteger.ZERO);

      return callPathToMapKey(parentCallPath);
    }
  }



  /**
   * Determine a key that can be used for a map that uniquely identifies the call path.
   * A message digest of the call path could be used, but a simpler multiplication method
   * will work just as well.
   *
   * @param callPath The call path to determine a map key for.
   * @return The map key representing the call path.
   */
  private static BigInteger callPathToMapKey(List<BigInteger> callPath) {
    if (callPath.size() == 0) {
      throw new RuntimeException("Invalid call path length: " + callPath.size());
    }
    else {
      BigInteger key = BigInteger.ONE;
      for (BigInteger call: callPath) {
        if (call.compareTo(MAX_CALLS_FROM_ONE_FUNCTION) >= 0) {
          throw new RuntimeException("Maximum calls from one function is: " + MAX_CALLS_FROM_ONE_FUNCTION);
        }

        key = key.multiply(MAX_CALLS_FROM_ONE_FUNCTION);
        key = key.add(call.add(BigInteger.ONE));
      }
      return key;
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
