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
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.DynamicGasProvider;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;

import java.io.IOException;
import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public abstract class AbstractCbc {
  private static final Logger LOG = LogManager.getLogger(AbstractCbc.class);

  // Retry requests to Ethereum Clients up to five times.
  protected static final int RETRY = 20;

  protected Credentials credentials;


  protected BigInteger blockchainId;
  protected String uri;
  // Polling interval should be equal to the block time.
  protected int pollingInterval;
  protected DynamicGasProvider gasProvider;

  Registrar registrarContract;
  protected Web3j web3j;
  protected TransactionManager tm;


  protected AbstractCbc(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    this.blockchainId = new BigInteger(bcId, 16);
    this.uri = uri;
    this.pollingInterval = Integer.parseInt(blockPeriod);
    this.credentials = credentials;
    this.web3j = Web3j.build(new HttpService(this.uri), this.pollingInterval, new ScheduledThreadPoolExecutor(5));
    this.tm = new RawTransactionManager(this.web3j, this.credentials, this.blockchainId.longValue(), RETRY, this.pollingInterval);
    this.gasProvider = new DynamicGasProvider(this.web3j, uri, gasPriceStrategy);
  }

  public void shutdown() {
    this.web3j.shutdown();
  }

  protected void deployContracts() throws Exception {
    this.registrarContract = Registrar.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.info(" Registrar Contract: {}", this.registrarContract.getContractAddress());
  }

  public void registerSignerThisBlockchain(AnIdentity signer) throws Exception {
    registerSigner(signer, this.blockchainId);
  }

  public void registerSigner(AnIdentity signer, BigInteger bcId) throws Exception {
    LOG.info("Registering signer {} as signer for blockchain {} in registration contract on blockchain {}",
        signer.getAddressAsBigInt(), bcId, this.blockchainId);
    TransactionReceipt receipt1 = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), bcId, signer.getAddressAsBigInt()).send();
    if (!receipt1.isStatusOK()) {
      throw new Exception("Transaction to register signer failed");
    }
  }

  public BigInteger getBlockchainId() {
    return this.blockchainId;
  }

  public static BigInteger generateRandomCrossBlockchainTransactionId() throws NoSuchAlgorithmException {
    // TODO put this into the crypto module and do a better job or this + reseeding.
    final SecureRandom rand = SecureRandom.getInstance("DRBG",
        DrbgParameters.instantiation(256, RESEED_ONLY, new byte[]{0x01}));
    return new BigInteger(255, rand);
  }

}
