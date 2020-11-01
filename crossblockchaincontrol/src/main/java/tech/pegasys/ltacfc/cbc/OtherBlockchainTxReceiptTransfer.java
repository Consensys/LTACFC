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
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.ltacfc.soliditywrappers.CbcTxRootTransfer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class OtherBlockchainTxReceiptTransfer extends AbstractCbcTxReceiptTransfer {
  static final Logger LOG = LogManager.getLogger(OtherBlockchainTxReceiptTransfer.class);


  public OtherBlockchainTxReceiptTransfer(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  // TODO this should migrate to the abstract cbc class: the root blockchain could also end up having a segment
  public TransactionReceipt segment(CrossEventProof startProof, List<BigInteger> callPath) throws Exception {

    RlpDumper.dump(RLP.input(Bytes.wrap(startProof.getTransactionReceipt())));

    TransactionReceipt txR = this.crossBlockchainControlContract.segment(
        startProof.getBlockchainId(),
        startProof.getCrossBlockchainControlContract(),
        startProof.getTransactionReceiptRoot(),
        startProof.getTransactionReceipt(),
        startProof.getProofOffsets(),
        startProof.getProofs(),
        callPath).send();
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    LOG.info("Cross Bc Id: {}", this.crossBlockchainControlContract.activeCallCrossBlockchainTransactionId().send().toString(16));
    LOG.info("CallGraph: {}", new BigInteger(1, this.crossBlockchainControlContract.activeCallGraph().send()).toString(16));

    List<CbcTxRootTransfer.SegmentEventResponse> segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    CbcTxRootTransfer.SegmentEventResponse segmentEventResponse = segmentEventResponses.get(0);
    LOG.info("Segment Event:");
    LOG.info(" _crossBlockchainTransactionId: {}", segmentEventResponse._crossBlockchainTransactionId.toString(16));
    LOG.info(" _callPath len: {}", segmentEventResponse._callPath.size());
    LOG.info(" _hashOfCallGraph: {}", new BigInteger(1, segmentEventResponse._hashOfCallGraph).toString(16));
    LOG.info(" _success: {}", segmentEventResponse._success);
    LOG.info(" _returnValue: {}", new BigInteger(1, segmentEventResponse._returnValue).toString(16));
    LOG.info(" num locked contracts: {}", segmentEventResponse._lockedContracts.size());
//    for (String lockedContractAddress: segmentEventResponse._lockedContracts) {
//      LOG.info(" locked contracts: {}", lockedContractAddress);
//    }

    return txR;
  }
}
