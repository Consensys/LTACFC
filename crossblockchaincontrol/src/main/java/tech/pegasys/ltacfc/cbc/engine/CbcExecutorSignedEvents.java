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
import tech.pegasys.ltacfc.cbc.AbstractCbc;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.cbc.CrossBlockchainControlSignedEvents;
import tech.pegasys.ltacfc.cbc.CrossBlockchainControlTxReceiptRootTransfer;
import tech.pegasys.ltacfc.cbc.SignedEvent;
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

public class CbcExecutorSignedEvents extends AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(CbcExecutorSignedEvents.class);

  SignedEvent signedStartEvent;
  SignedEvent signedRootEvent;


  // Key for this map is the call path of the caller.
  Map<BigInteger, List<SignedEvent>> signedSegmentEvents = new HashMap<>();

  // Key for this map is the blockchain id that the segment occurred on.
  Map<BigInteger, List<SignedEvent>> signedSegmentEventsWithLockedContracts = new HashMap<>();

  public CbcExecutorSignedEvents(CbcManager cbcManager) throws Exception {
    super(CrossBlockchainConsensus.EVENT_SIGNING, cbcManager);
  }


  protected void startCall() throws Exception {
    CrossBlockchainControlSignedEvents rootCbcContract = this.cbcManager.getCbcContractSignedEvents(this.rootBcId);

    byte[] startEventData = rootCbcContract.start(this.crossBlockchainTransactionId, this.timeout, this.callGraph);
    this.signedStartEvent = new SignedEvent(this.cbcManager.getSigners(this.rootBcId),
          rootBcId, this.cbcManager.getCbcAddress(this.rootBcId), AbstractCbc.START_EVENT_SIGNATURE, startEventData);
  }

  protected void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception {
    if (callPath.size() == 0) {
      throw new Exception("Invalid call path length for segment: " + callPath.size());
    }

    BigInteger mapKey = callPathToMapKey(callPath);

    CrossBlockchainControlSignedEvents segmentCbcContract = this.cbcManager.getCbcContractSignedEvents(blockchainId);

    List<SignedEvent> signedEvents = this.signedSegmentEvents.computeIfAbsent(mapKey, k -> new ArrayList<>());
    Tuple<byte[], Boolean, Boolean> result = segmentCbcContract.segment(this.signedStartEvent, signedEvents, callPath);
    byte[] segEventData = result.getFirst();
    boolean noLockedContracts = result.getSecond();
    //boolean success = result.getThird();
    SignedEvent signedSegEvent = new SignedEvent(this.cbcManager.getSigners(blockchainId),
            blockchainId, this.cbcManager.getCbcAddress(blockchainId), AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);

    // Add the proof for the call that has just occurred to the map so it can be accessed when the next
    BigInteger parentMapKey = determineMapKeyOfCaller(callPath);
    signedEvents = this.signedSegmentEvents.computeIfAbsent(parentMapKey, k -> new ArrayList<>());
    signedEvents.add(signedSegEvent);

    // Add the proof to the list of segments that have contracts that need to be unlocked.
    if (!noLockedContracts) {
      signedEvents =
          this.signedSegmentEventsWithLockedContracts.computeIfAbsent(blockchainId, k -> new ArrayList<>());
      signedEvents.add(signedSegEvent);
    }
  }


  protected void root() throws Exception {
    CrossBlockchainControlSignedEvents rootCbcContract = this.cbcManager.getCbcContractSignedEvents(this.rootBcId);
    List<SignedEvent> signedSegEvents = this.signedSegmentEvents.get(ROOT_CALL_MAP_KEY);
    byte[] rootEventData = rootCbcContract.root(this.signedStartEvent, signedSegEvents);
    this.signedRootEvent = new SignedEvent(this.cbcManager.getSigners(this.rootBcId),
          this.rootBcId, this.cbcManager.getCbcAddress(this.rootBcId), AbstractCbc.ROOT_EVENT_SIGNATURE, rootEventData);
    this.success = rootCbcContract.getRootEventSuccess();
  }

  protected void doSignallingCalls() throws Exception {
    for (BigInteger blockchainId: this.signedSegmentEventsWithLockedContracts.keySet()) {
      List<SignedEvent> signedSegEventsLockedContractsCurrentBlockchain =
          this.signedSegmentEventsWithLockedContracts.get(blockchainId);
      CrossBlockchainControlSignedEvents cbcContract = this.cbcManager.getCbcContractSignedEvents(blockchainId);
      cbcContract.signalling(this.signedRootEvent, signedSegEventsLockedContractsCurrentBlockchain);
    }
  }
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
