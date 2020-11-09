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
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.AbstractCbc;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractCbcExecutor {
  static final Logger LOG = LogManager.getLogger(AbstractCbcExecutor.class);

  private CrossBlockchainConsensus consensusMethodology;

  CbcManager cbcManager;


  protected byte[] callGraph;

  protected BigInteger timeout;
  protected BigInteger crossBlockchainTransactionId;
  protected BigInteger rootBcId;

  boolean success;


  public AbstractCbcExecutor(CrossBlockchainConsensus consensusMethodology, CbcManager cbcManager) {
    this.consensusMethodology = consensusMethodology;
    this.cbcManager = cbcManager;
  }


  public void init(byte[] callGraph, BigInteger timeout, BigInteger transactionId, BigInteger rootBcId) {
    this.callGraph = callGraph;
    this.timeout = timeout;
    this.crossBlockchainTransactionId = transactionId;
    this.rootBcId = rootBcId;
  }

  protected abstract void startCall()throws Exception;

  protected abstract void segment(BigInteger blockchainId, BigInteger callerBlockchainId, List<BigInteger> callPath) throws Exception;

  protected abstract void root() throws Exception;
}
