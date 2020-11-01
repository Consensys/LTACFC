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
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.common.RevertReason;
import tech.pegasys.ltacfc.soliditywrappers.CbcTxRootTransfer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;


public abstract class RootBlockchainTxReceiptTransfer extends AbstractCbcTxReceiptTransfer {
  static final Logger LOG = LogManager.getLogger(RootBlockchainTxReceiptTransfer.class);

  // TODO put this into a map for the current transaction id, so many transactions can be handled in parallel
  // The time-out for the current transaction.
  long crossBlockchainTransactionTimeout;

  public boolean rootEventSuccess;

  public RootBlockchainTxReceiptTransfer(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public TransactionReceipt start(BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    LOG.info("TxId: {}", transactionId.toString(16));
    BigInteger cG = new BigInteger(1, callGraph);
    TransactionReceipt txR = this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
    List<CbcTxRootTransfer.StartEventResponse> startEvents = this.crossBlockchainControlContract.getStartEvents(txR);
    CbcTxRootTransfer.StartEventResponse startEvent = startEvents.get(0);
    LOG.info("Timeout: {} seconds from unix epoc, given request: {}", startEvent._timeout, timeout);
    LOG.info(" Current time on this computer: {}", System.currentTimeMillis() / 1000);
    this.crossBlockchainTransactionTimeout = startEvent._timeout.longValue();
    return txR;
  }


  public TransactionReceipt root(CrossEventProof startProof, List<CrossEventProof> segProofs) throws Exception {
    segProofs.add(0, startProof);


    for (CrossEventProof proofInfo: segProofs) {
      TransactionReceipt txR = this.crossBlockchainControlContract.callPrep(
          proofInfo.getBlockchainId(),
          proofInfo.getCrossBlockchainControlContract(),
          proofInfo.getTransactionReceiptRoot(),
          proofInfo.getTransactionReceipt(),
          proofInfo.getProofOffsets(),
          proofInfo.getProofs()).send();
      if (!txR.isStatusOK()) {
        throw new Exception("Root transaction failed");
      }
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
      txR = this.crossBlockchainControlContract.root().send();
    }
    catch (TransactionException ex) {
      LOG.error(" Revert Reason: {}", RevertReason.decodeRevertReason(ex.getTransactionReceipt().get().getRevertReason()));
      throw ex;
    }

    if (!txR.isStatusOK()) {
      throw new Exception("Root transaction failed");
    }
    LOG.info("Dump Events");
    List<CbcTxRootTransfer.DumpEventResponse> dumpEventResponses = this.crossBlockchainControlContract.getDumpEvents(txR);
    for (CbcTxRootTransfer.DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.info("  Event:");
      LOG.info("   1: {}", dumpEventResponse._val1.toString(16));
      LOG.info("   2: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.info("   3: {}", dumpEventResponse._val3);
      LOG.info("   4: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }


    List<CbcTxRootTransfer.RootEventResponse> rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    CbcTxRootTransfer.RootEventResponse rootEventResponse = rootEventResponses.get(0);
    LOG.info("Root Event:");
    LOG.info(" _crossBlockchainTransactionId: {}", rootEventResponse._crossBlockchainTransactionId.toString(16));
    LOG.info(" _success: {}", rootEventResponse._success);

    this.rootEventSuccess = rootEventResponse._success;


    LOG.info("Call Events");
    List<CbcTxRootTransfer.CallEventResponse> callEventResponses = this.crossBlockchainControlContract.getCallEvents(txR);
    for (CbcTxRootTransfer.CallEventResponse callEventResponse : callEventResponses) {
      LOG.info("  Event:");
      LOG.info("   Expected Blockchain Id: {}", callEventResponse._expectedBlockchainId.toString(16));
      LOG.info("   Actual Blockchain Id: {}", callEventResponse._actualBlockchainId.toString(16));
      LOG.info("   Expected Contract: {}", callEventResponse._expectedContract);
      LOG.info("   Actual Contract: {}", callEventResponse._actualContract);
      LOG.info("   Expected Function Call: {}", new BigInteger(1, callEventResponse._expectedFunctionCall).toString(16));
      LOG.info("   Actual Function Call: {}", new BigInteger(1, callEventResponse._actualFunctionCall).toString(16));
      LOG.info("   Return Value: {}", new BigInteger(1, callEventResponse._retVal).toString(16));
    }
    return txR;
  }
}
