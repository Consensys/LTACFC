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
package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.cbc.CrossEventProof;
import tech.pegasys.ltacfc.common.DynamicGasProvider;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class OtherBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(OtherBc.class);

  static final String OTHER_BLOCKCHAIN_ID = "1F";
  static final String OTHER_URI = "http://127.0.0.1:8310/";
  // Have the polling interval equal to the block time.
  private static final String POLLING_INTERVAL = "2000";

  OtherBlockchainContract otherBlockchainContract;
  LockableStorage lockableStorageContract;


  public OtherBc(Credentials credentials) throws IOException {
    this(credentials, OTHER_BLOCKCHAIN_ID, OTHER_URI, DynamicGasProvider.Strategy.FREE.toString(), POLLING_INTERVAL);
  }

  public OtherBc(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts() throws Exception {
    LOG.info("Deploy Other Blockchain Contracts");
    super.deployContracts();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.gasProvider,
        this.crossBlockchainControlContract.getContractAddress()).send();
    this.otherBlockchainContract =
        OtherBlockchainContract.deploy(this.web3j, this.tm, this.gasProvider,
          this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.otherBlockchainContract.getContractAddress()).send();
    LOG.info(" Other Blockchain Contract: {}", this.otherBlockchainContract.getContractAddress());
    LOG.info(" Lockable Storage Contract: {}", this.lockableStorageContract.getContractAddress());
  }

  public void setVal(BigInteger val) throws Exception {
    this.otherBlockchainContract.setVal(val).send();
  }

  public String getRlpFunctionSignature_GetVal() {
    return this.otherBlockchainContract.getRLP_getVal();
  }

  public String getRlpFunctionSignature_SetVal(BigInteger val) {
    return this.otherBlockchainContract.getRLP_setVal(val);
  }

  public String getRlpFunctionSignature_SetValues(BigInteger val1, BigInteger val2) {
    return this.otherBlockchainContract.getRLP_setValues(val1, val2);
  }

  public boolean storageIsLocked() throws Exception {
    Boolean isLocked = this.lockableStorageContract.locked().send();
    return  isLocked;
  }

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

    List<CrossBlockchainControl.SegmentEventResponse> segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    CrossBlockchainControl.SegmentEventResponse segmentEventResponse = segmentEventResponses.get(0);
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

  private void check(byte[] txReceipt) {
  }

}
