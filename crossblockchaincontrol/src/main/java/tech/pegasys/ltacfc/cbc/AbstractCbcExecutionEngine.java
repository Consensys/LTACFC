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
package tech.pegasys.ltacfc.cbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;

import java.math.BigInteger;
import java.util.List;

public abstract class AbstractCbcExecutionEngine {
  static final Logger LOG = LogManager.getLogger(AbstractCbcExecutionEngine.class);

  private CrossBlockchainConsensus consensusMethodology;

  protected RlpList callGraph;
  protected BigInteger timeout;
  protected BigInteger crossBlockchainTransactionId;

  boolean success;


  public AbstractCbcExecutionEngine(CrossBlockchainConsensus consensusMethodology) {
    this.consensusMethodology = consensusMethodology;
  }




  public void execute(RlpList callGraph, long timeout) throws Exception {
    LOG.info("start");
    this.callGraph = callGraph;
    this.timeout = BigInteger.valueOf(timeout);

    this.crossBlockchainTransactionId = AbstractCbc.generateRandomCrossBlockchainTransactionId();

    startCall();

    int maxCallDepth = maximumCallDepth(callGraph);
    LOG.info("Max Call Depth: {}", maxCallDepth);

  }

  protected abstract void startCall()throws Exception;


  private int maximumCallDepth(RlpList callGraph) {
    return callDepth(callGraph, 0);
  }

  private int callDepth(RlpList callGraph, int depth) {
    List<RlpType> calls = callGraph.getValues();
    int maxDepth = depth;
    for (RlpType call: calls) {
      if (call instanceof  RlpList) {
        int depthFound = callDepth((RlpList) call, depth+1);
        if (depthFound > maxDepth) {
          maxDepth = depthFound;
        }
      }
    }
    return maxDepth;
  }
}
