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
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.AbstractCbc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SerialExecutionEngine implements ExecutionEngine {
  static final Logger LOG = LogManager.getLogger(SerialExecutionEngine.class);

  AbstractCbcExecutor executor;

  public SerialExecutionEngine(AbstractCbcExecutor executor) {
    this.executor = executor;
  }

  public boolean execute(RlpList callGraph, long timeout) throws Exception {
    LOG.info("start");
    BigInteger crossBlockchainTransactionId = AbstractCbc.generateRandomCrossBlockchainTransactionId();
    BigInteger timeoutBig = BigInteger.valueOf(timeout);


    BigInteger rootBlockchainId = callRootBlockchainId(callGraph);
    this.executor.init(RlpEncoder.encode(callGraph), timeoutBig, crossBlockchainTransactionId, rootBlockchainId);
    this.executor.startCall();
    callSegments(callGraph, new ArrayList<>(), rootBlockchainId);

    this.executor.doSignallingCalls();

    return this.executor.getRootEventSuccess();
  }

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

  private void callSegments(RlpList callGraph, List<BigInteger> callPath, BigInteger callerBlockchainId) throws Exception {
    List<RlpType> calls = callGraph.getValues();
    if (calls.get(0) instanceof  RlpList) {
      RlpList callerCall = (RlpList) calls.get(0);
      RlpString blockchainIdRlp = (RlpString) callerCall.getValues().get(0);
      BigInteger theCallerBlockchainId = blockchainIdRlp.asPositiveBigInteger();

      BigInteger callOffset = BigInteger.ONE;
      for (int i = 1; i < calls.size(); i++) {
        RlpList segCall = (RlpList) calls.get(i);
        List<BigInteger> nextCallPath = new ArrayList<>(callPath);
        nextCallPath.add(callOffset);
        callSegments(segCall, nextCallPath, theCallerBlockchainId);
        callOffset = callOffset.add(BigInteger.ONE);
      }

      // Now call the segment call that called all of the other segments.
      RlpList segCall = (RlpList) calls.get(0);
      List<BigInteger> nextCallPath = new ArrayList<>(callPath);
      nextCallPath.add(BigInteger.ZERO);
      callSegments(segCall, nextCallPath, callerBlockchainId);
    }
    else {
      if (callPath.size() == 1 && callPath.get(0).compareTo(BigInteger.ZERO) == 0) {
        this.executor.root();
      }
      else {
        RlpString blockchainIdRlp = (RlpString) calls.get(0);
        BigInteger blockchainId = blockchainIdRlp.asPositiveBigInteger();

        this.executor.segment(blockchainId, callerBlockchainId, callPath);
      }
    }
  }


  private static BigInteger callRootBlockchainId(RlpList callGraph) {
    List<RlpType> calls = callGraph.getValues();
    RlpList rootCall = (RlpList)calls.get(0);
    RlpString bcIdRlp = (RlpString) rootCall.getValues().get(0);
    BigInteger rootBcId = bcIdRlp.asPositiveBigInteger();
    LOG.info("Root blockchain detected as: 0x{}", rootBcId.toString(16));
    return rootBcId;
  }

}
