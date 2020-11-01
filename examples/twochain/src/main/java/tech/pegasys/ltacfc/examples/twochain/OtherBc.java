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
import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.cbc.OtherBlockchainTxReceiptTransfer;
import tech.pegasys.ltacfc.common.DynamicGasProvider;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;


import java.io.IOException;
import java.math.BigInteger;

public class OtherBc extends OtherBlockchainTxReceiptTransfer {
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
    return  this.lockableStorageContract.locked().send();
  }

  public void showValues() throws Exception {
    LOG.info("Other Blockchain: KEY_FOR_VAL: {}", this.otherBlockchainContract.getVal().send());
  }

}
