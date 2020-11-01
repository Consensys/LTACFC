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
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.cbc.CrossEventProof;
import tech.pegasys.ltacfc.cbc.RootBlockchainTxReceiptTransfer;
import tech.pegasys.ltacfc.common.DynamicGasProvider;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.RootBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.soliditywrappers.CbcTxRootTransfer;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.MerklePatriciaTrie;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.Proof;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RootBc extends RootBlockchainTxReceiptTransfer {
  static final Logger LOG = LogManager.getLogger(RootBc.class);

  static final String ROOT_BLOCKCHAIN_ID = "1F";
  static final String ROOT_URI = "http://127.0.0.1:8310/";
  // Have the polling interval equal to the block time.
  private static final String POLLING_INTERVAL = "2000";


  RootBlockchainContract rootBlockchainContract;
  LockableStorage lockableStorageContract;

  public RootBc(Credentials credentials) throws IOException {
    this(credentials, ROOT_BLOCKCHAIN_ID, ROOT_URI, DynamicGasProvider.Strategy.FREE.toString(), POLLING_INTERVAL);
  }

  public RootBc(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(BigInteger otherBlockchainId, String otherContractAddress) throws Exception {
    LOG.info("Deploy Root Blockchain Contracts");
    deployContracts();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.gasProvider,
        this.crossBlockchainControlContract.getContractAddress()).send();
    this.rootBlockchainContract =
        RootBlockchainContract.deploy(this.web3j, this.tm, this.gasProvider,
            this.crossBlockchainControlContract.getContractAddress(),
            otherBlockchainId,
            otherContractAddress,
            this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.rootBlockchainContract.getContractAddress()).send();
    LOG.info(" Root Blockchain Contract: {}", this.rootBlockchainContract.getContractAddress());
    LOG.info(" Lockable Storage Contract: {}", this.lockableStorageContract.getContractAddress());
  }

  public String getRlpFunctionSignature_SomeComplexBusinessLogic(BigInteger val) {
    return this.rootBlockchainContract.getRLP_someComplexBusinessLogic(val);
  }

  public void setVal1(BigInteger val) throws Exception {
    this.rootBlockchainContract.setVal1(val).send();
  }
  public void setVal2(BigInteger val) throws Exception {
    this.rootBlockchainContract.setVal2(val).send();
  }

  public BigInteger getVal1() throws Exception {
    return this.rootBlockchainContract.getVal1().send();
  }
  public BigInteger getVal2() throws Exception {
    return this.rootBlockchainContract.getVal2().send();
  }


  public void showValues() throws Exception {
    LOG.info("Root Blockchain: Val1: {}", this.rootBlockchainContract.getVal1().send());
    LOG.info("Root Blockchain: Val2: {}", this.rootBlockchainContract.getVal2().send());
  }
}
