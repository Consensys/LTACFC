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
package tech.pegasys.ltacfc.cbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.common.RevertReason;
import tech.pegasys.ltacfc.common.StatsHolder;
import tech.pegasys.ltacfc.common.Tuple;
import tech.pegasys.ltacfc.soliditywrappers.CbcSignedEvent;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CrossBlockchainControlSignedEvents extends AbstractCbc {
  private static final Logger LOG = LogManager.getLogger(CrossBlockchainControlSignedEvents.class);

  // TODO put this into a map for the current transaction id, so many transactions can be handled in parallel
  // The time-out for the current transaction.
  private long crossBlockchainTransactionTimeout;
  private boolean rootEventSuccess;

  private CbcSignedEvent crossBlockchainControlContract;

  public CrossBlockchainControlSignedEvents(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts() throws Exception {
    super.deployContracts();
    this.crossBlockchainControlContract =
        CbcSignedEvent.deploy(this.web3j, this.tm, this.gasProvider, this.blockchainId, this.registrarContract.getContractAddress()).send();
    LOG.info(" Cross Blockchain Contract Contract: {}", this.crossBlockchainControlContract.getContractAddress());
  }



  public byte[] start(BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    TransactionReceipt txR = this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
    StatsHolder.logGas("Start Transaction", txR.getGasUsed());
    List<CbcSignedEvent.StartEventResponse> startEvents = this.crossBlockchainControlContract.getStartEvents(txR);
    CbcSignedEvent.StartEventResponse startEvent = startEvents.get(0);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    // LOG.info("Start Event: {}", new BigInteger(getEventData(txR, AbstractCbc.START_EVENT_SIGNATURE_BYTES)).toString(16));
    return getEventData(txR, AbstractCbc.START_EVENT_SIGNATURE_BYTES);
  }




  public Tuple<byte[], Boolean, Boolean> segment(SignedEvent startEvent, List<SignedEvent> segEvents, List<BigInteger> callPath) throws Exception {

    // TODO For the moment just support one level of function call.
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(startEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(startEvent.getEncodedSignatures());

    //RlpDumper.dump(RLP.input(Bytes.wrap(encodedSignatures.get(0))));
    TransactionReceipt txR;
    try {
      txR = this.crossBlockchainControlContract.segment(encodedEvents, encodedSignatures, callPath).send();
      StatsHolder.logGas("Segment Transaction", txR.getGasUsed());
    } catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    List<CbcSignedEvent.SegmentEventResponse> segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    if (segmentEventResponses.size() != 1) {
      throw new RuntimeException("Unexpected number of segment events emitted: " + segmentEventResponses.size());
    }

    CbcSignedEvent.SegmentEventResponse segmentEventResponse = segmentEventResponses.get(0);
    LOG.info("Segment Event:");
    LOG.info(" Cross-Blockchain Transaction Id: {}", segmentEventResponse._crossBlockchainTransactionId.toString(16));
    StringBuilder calls = new StringBuilder();
    // TODO The code below is a hack to handle the fact that currently Web3J returns a Uint256 object, but the type is BigInteger.
    // TODO this code will break when Web3J fixes their bug.
    for (Object partOfCallPath: segmentEventResponse._callPath) {
      Uint256 hack = (Uint256) partOfCallPath;
      calls.append("[");
      calls.append(hack.getValue());
      calls.append("] ");
    }
    LOG.info(" Call Path: {}", calls);
    LOG.info(" Hash Of Call Graph: {}", new BigInteger(1, segmentEventResponse._hashOfCallGraph).toString(16));
    LOG.info(" Success: {}", segmentEventResponse._success);
    LOG.info(" Return Value: {}", new BigInteger(1, segmentEventResponse._returnValue).toString(16));
    StringBuilder lockedContracts = new StringBuilder();
    // TODO The code below is a hack to handle the fact that currently Web3J returns an Address object, but the type is BigInteger.
    // TODO this code will break when Web3J fixes their bug.
    for (Object lockedContract: segmentEventResponse._lockedContracts) {
      Address hack = (Address) lockedContract;
      calls.append("[");
      calls.append(hack.getValue());
      calls.append("] ");
    }
    LOG.info(" Locked Contracts: [{}]", lockedContracts);

    showAllDumpEvents(txR);

    return new Tuple<byte[], Boolean, Boolean>(
        getEventData(txR, AbstractCbc.SEGMENT_EVENT_SIGNATURE_BYTES),
        segmentEventResponse._lockedContracts.isEmpty(),
        segmentEventResponse._success);
  }


  public byte[] root(SignedEvent startEvent, List<SignedEvent> segEvents) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(startEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(startEvent.getEncodedSignatures());
    for (SignedEvent segEvent: segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }

    long now = System.currentTimeMillis() / 1000;
    LOG.info(" Current time on this computer: {}; Transaction time-out: {}", now, this.crossBlockchainTransactionTimeout);
    if (this.crossBlockchainTransactionTimeout < now) {
      LOG.warn(" Cross-Blockchain transaction will fail as transaction has timed-out");
    }
    else if (this.crossBlockchainTransactionTimeout < (now - 10)) {
      LOG.warn(" Cross-Blockchain transaction might fail as transaction time-out is soon");
    }

    TransactionReceipt txR;
    try {
      txR = this.crossBlockchainControlContract.root(encodedEvents, encodedSignatures).send();
      StatsHolder.logGas("Root Transaction", txR.getGasUsed());
    }
    catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    if (!txR.isStatusOK()) {
      throw new Exception("Root transaction failed");
    }

    List<CbcSignedEvent.RootEventResponse> rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    CbcSignedEvent.RootEventResponse rootEventResponse = rootEventResponses.get(0);
    LOG.info("Root Event:");
    LOG.info(" _crossBlockchainTransactionId: {}", rootEventResponse._crossBlockchainTransactionId.toString(16));
    LOG.info(" _success: {}", rootEventResponse._success);

    this.rootEventSuccess = rootEventResponse._success;


    LOG.info("Call Events");
    List<CbcSignedEvent.CallEventResponse> callEventResponses = this.crossBlockchainControlContract.getCallEvents(txR);
    for (CbcSignedEvent.CallEventResponse callEventResponse : callEventResponses) {
      LOG.info("  Event:");
      LOG.info("   Expected Blockchain Id: {}", callEventResponse._expectedBlockchainId.toString(16));
      LOG.info("   Actual Blockchain Id: {}", callEventResponse._actualBlockchainId.toString(16));
      LOG.info("   Expected Contract: {}", callEventResponse._expectedContract);
      LOG.info("   Actual Contract: {}", callEventResponse._actualContract);
      LOG.info("   Expected Function Call: {}", new BigInteger(1, callEventResponse._expectedFunctionCall).toString(16));
      LOG.info("   Actual Function Call: {}", new BigInteger(1, callEventResponse._actualFunctionCall).toString(16));
      LOG.info("   Return Value: {}", new BigInteger(1, callEventResponse._retVal).toString(16));
    }

    showAllDumpEvents(txR);

    return getEventData(txR, ROOT_EVENT_SIGNAUTRE_BYTES);
  }

  public void signalling(SignedEvent rootEvent, List<SignedEvent> segEvents) throws Exception {
    List<byte[]> encodedEvents = new ArrayList<>();
    encodedEvents.add(rootEvent.getEncodedEventInformation());
    List<byte[]> encodedSignatures = new ArrayList<>();
    encodedSignatures.add(rootEvent.getEncodedSignatures());
    for (SignedEvent segEvent: segEvents) {
      encodedEvents.add(segEvent.getEncodedEventInformation());
      encodedSignatures.add(segEvent.getEncodedSignatures());
    }


    TransactionReceipt txR = this.crossBlockchainControlContract.signalling(encodedEvents, encodedSignatures).send();
    StatsHolder.logGas("Signalling Transaction", txR.getGasUsed());
    if (!txR.isStatusOK()) {
      throw new Exception("Signalling transaction failed");
    }

    List<CbcSignedEvent.SignallingEventResponse> sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    CbcSignedEvent.SignallingEventResponse sigEventResponse = sigEventResponses.get(0);
    LOG.info("Signalling Event:");
    LOG.info(" _rootBlockchainId: {}", sigEventResponse._rootBcId.toString(16));
    LOG.info(" _crossBlockchainTransactionId: {}", sigEventResponse._crossBlockchainTransactionId.toString(16));

    showAllDumpEvents(txR);
  }



  public String getCbcContractAddress() {
    return this.crossBlockchainControlContract.getContractAddress();
  }

  public boolean getRootEventSuccess() {
    return this.rootEventSuccess;
  }

  public byte[] getEventData(TransactionReceipt txR, Bytes eventSignatureToFind) throws Exception {
    List<Log> logs = txR.getLogs();
    String cbcAddress = getCbcContractAddress();
    for (Log log: logs) {
      String eventEmittedByAddress = log.getAddress();
      if (!cbcAddress.equalsIgnoreCase(eventEmittedByAddress)) {
        continue;
      }
      List<String> eventTopics = log.getTopics();
      if (eventTopics.size() != 1) {
        continue;
      }
      String eventSignatureStr = eventTopics.get(0);
      Bytes eventSignatureBytes = Bytes.fromHexString(eventSignatureStr);
      if (eventSignatureBytes.compareTo(eventSignatureToFind) != 0) {
        continue;
      }
      String eventDataStr = log.getData();
      Bytes eventDataBytes = Bytes.fromHexString(eventDataStr);
      return eventDataBytes.toArray();
    }
    throw new Exception("Event not found in transaction receipt");
  }

  protected void showAllDumpEvents(TransactionReceipt txR) {
    LOG.info("Dump Events");
    List<CbcSignedEvent.DumpEventResponse> dumpEventResponses = this.crossBlockchainControlContract.getDumpEvents(txR);
    if (dumpEventResponses.isEmpty()) {
      LOG.info(" None");
    }
    for (CbcSignedEvent.DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.info(" Event:");
      LOG.info("  Uint256: {}", dumpEventResponse._val1.toString(16));
      LOG.info("  Bytes32: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.info("  Address: {}", dumpEventResponse._val3);
      LOG.info("  Bytes: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }
  }
}
