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
import org.hyperledger.besu.crypto.Hash;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.soliditywrappers.CbcSignedEvent;
import tech.pegasys.ltacfc.soliditywrappers.CbcTxRootTransfer;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;

import java.io.IOException;
import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public abstract class AbstractCbc extends AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractCbc.class);

  public static byte[] START_EVENT_SIGNATURE = Hash.keccak256(Bytes.wrap("Start(uint256,address,uint256,bytes)".getBytes())).toArray();
  public static Bytes START_EVENT_SIGNATURE_BYTES = Bytes.wrap(START_EVENT_SIGNATURE);
  public static byte[] SEGMENT_EVENT_SIGNATURE = Hash.keccak256(Bytes.wrap("Segment(uint256,bytes32,uint256[],address[],bool,bytes)".getBytes())).toArray();
  public static Bytes SEGMENT_EVENT_SIGNATURE_BYTES = Bytes.wrap(SEGMENT_EVENT_SIGNATURE);
  public static byte[] ROOT_EVENT_SIGNATURE = Hash.keccak256(Bytes.wrap("Root(uint256,bool)".getBytes())).toArray();
  public static Bytes ROOT_EVENT_SIGNAUTRE_BYTES = Bytes.wrap(ROOT_EVENT_SIGNATURE);


  Registrar registrarContract;


  protected AbstractCbc(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
      super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  protected void deployContracts() throws Exception {
    this.registrarContract = Registrar.deploy(this.web3j, this.tm, this.gasProvider).send();
    LOG.info(" Registrar Contract: {}", this.registrarContract.getContractAddress());
  }

  public void registerSignerThisBlockchain(AnIdentity signer) throws Exception {
    registerSigner(signer, this.blockchainId);
  }

  public void registerSigner(AnIdentity signer, BigInteger bcId) throws Exception {
    LOG.info("Registering signer 0x{} as signer for blockchain 0x{} in registration contract on blockchain 0x{}",
        signer.getAddress(), bcId.toString(16), this.blockchainId.toString(16));
    TransactionReceipt receipt1 = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), bcId, signer.getAddressAsBigInt()).send();
    if (!receipt1.isStatusOK()) {
      throw new Exception("Transaction to register signer failed");
    }
  }

  public static BigInteger generateRandomCrossBlockchainTransactionId() throws NoSuchAlgorithmException {
    // TODO put this into the crypto module and do a better job or this + reseeding.
    final SecureRandom rand = SecureRandom.getInstance("DRBG",
        DrbgParameters.instantiation(256, RESEED_ONLY, new byte[]{0x01}));
    return new BigInteger(255, rand);
  }

  public abstract String getCbcContractAddress();




  public static class CallEventResponse extends BaseEventResponse {
    public BigInteger _expectedBlockchainId;
    public BigInteger _actualBlockchainId;
    public String _expectedContract;
    public String _actualContract;
    public byte[] _expectedFunctionCall;
    public byte[] _actualFunctionCall;
    public byte[] _retVal;
    public CallEventResponse(BigInteger _expectedBlockchainId, BigInteger _actualBlockchainId, String _expectedContract,
                             String _actualContract, byte[] _expectedFunctionCall, byte[] _actualFunctionCall, byte[] _retVal) {
      this._expectedBlockchainId = _expectedBlockchainId;
      this._actualBlockchainId = _actualBlockchainId;
      this._expectedContract = _expectedContract;
      this._actualContract = _actualContract;
      this._expectedFunctionCall = _expectedFunctionCall;
      this._actualFunctionCall = _actualFunctionCall;
      this._retVal = _retVal;
    }
  }

  public static class CallFailureEventResponse extends BaseEventResponse {
    public String _revertReason;
    public CallFailureEventResponse(String _revertReason) {
      this._revertReason = _revertReason;
    }
  }

  public static class DumpEventResponse extends BaseEventResponse {
    public BigInteger _val1;
    public byte[] _val2;
    public String _val3;
    public byte[] _val4;
    public DumpEventResponse(BigInteger _val1, byte[] _val2, String _val3, byte[] _val4) {
      this._val1 = _val1;
      this._val2 = _val2;
      this._val3 = _val3;
      this._val4 = _val4;
    }
  }

  public static class NotEnoughCallsEventResponse extends BaseEventResponse {
    public BigInteger _expectedNumberOfCalls;
    public BigInteger _actualNumberOfCalls;
    public NotEnoughCallsEventResponse(BigInteger _expectedNumberOfCalls, BigInteger _actualNumberOfCalls) {
      this._expectedNumberOfCalls = _expectedNumberOfCalls;
      this._actualNumberOfCalls = _actualNumberOfCalls;
    }
  }

  public static class RootEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public Boolean _success;
    public RootEventResponse(BigInteger _crossBlockchainTransactionId, Boolean _success) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._success = _success;
    }
  }

  public static class SegmentEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public byte[] _hashOfCallGraph;
    public List<BigInteger> _callPath;
    public List<String> _lockedContracts;
    public Boolean _success;
    public byte[] _returnValue;
    public SegmentEventResponse(BigInteger _crossBlockchainTransactionId, byte[] _hashOfCallGraph, List<BigInteger> _callPath,
        List<String> _lockedContracts, Boolean _success, byte[] _returnValue) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._hashOfCallGraph = _hashOfCallGraph;
      this._callPath = _callPath;
      this._lockedContracts = _lockedContracts;
      this._success = _success;
      this._returnValue = _returnValue;
    }
  }

  public static class SignallingEventResponse extends BaseEventResponse {
    public BigInteger _rootBcId;
    public BigInteger _crossBlockchainTransactionId;
    public SignallingEventResponse(BigInteger _rootBcId, BigInteger _crossBlockchainTransactionId) {
      this._rootBcId = _rootBcId;
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
    }
  }

  public static class StartEventResponse extends BaseEventResponse {
    public BigInteger _crossBlockchainTransactionId;
    public String _caller;
    public BigInteger _timeout;
    public byte[] _callGraph;
    public StartEventResponse(BigInteger _crossBlockchainTransactionId, String _caller, BigInteger _timeout, byte[] _callGraph) {
      this._crossBlockchainTransactionId = _crossBlockchainTransactionId;
      this._caller = _caller;
      this._timeout = _timeout;
      this._callGraph = _callGraph;
    }
  }

  protected void showCallEvents(List<CallEventResponse> callEventResponses) {
    LOG.info("Call Events");
    if (callEventResponses.isEmpty()) {
      LOG.info(" None");
    }
    for (CallEventResponse callEventResponse : callEventResponses) {
      LOG.info(" Event:");
      LOG.info("   Expected Blockchain Id: 0x{}", callEventResponse._expectedBlockchainId.toString(16));
      LOG.info("   Actual Blockchain Id: 0x{}", callEventResponse._actualBlockchainId.toString(16));
      LOG.info("   Expected Contract: {}", callEventResponse._expectedContract);
      LOG.info("   Actual Contract: {}", callEventResponse._actualContract);
      LOG.info("   Expected Function Call: {}", new BigInteger(1, callEventResponse._expectedFunctionCall).toString(16));
      LOG.info("   Actual Function Call: {}", new BigInteger(1, callEventResponse._actualFunctionCall).toString(16));
      LOG.info("   Return Value: {}", new BigInteger(1, callEventResponse._retVal).toString(16));
    }
  }

  protected void showCallFailureEvents(List<CallFailureEventResponse> callFailureEventResponses) {
    LOG.info("Call Failure Events");
    if (callFailureEventResponses.isEmpty()) {
      LOG.info(" None");
    }
    for (CallFailureEventResponse callFailureEventResponse : callFailureEventResponses) {
      LOG.info(" Revert Reason: {}", callFailureEventResponse._revertReason);
    }
  }


  protected void showDumpEvents(List<DumpEventResponse> dumpEventResponses) {
    LOG.info("Dump Events");
    if (dumpEventResponses.isEmpty()) {
      LOG.info(" None");
    }
    for (DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.info(" Event:");
      LOG.info("  Uint256: {}", dumpEventResponse._val1.toString(16));
      LOG.info("  Bytes32: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.info("  Address: {}", dumpEventResponse._val3);
      LOG.info("  Bytes: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }
  }

  protected void showNotEnoughCallsEvents(List<NotEnoughCallsEventResponse> notEnoughCallsEventResponses) {
    LOG.info("Not Enough Call Events");
    if (notEnoughCallsEventResponses.isEmpty()) {
      LOG.info(" None");
    }
    for (NotEnoughCallsEventResponse notEnoughCallsEventResponse: notEnoughCallsEventResponses) {
      LOG.info("  Event:");
      LOG.info("   Actual Number of Calls: {}", notEnoughCallsEventResponse._actualNumberOfCalls);
      LOG.info("   Expected Number of Calls: {}", notEnoughCallsEventResponse._expectedNumberOfCalls);
    }
  }

  protected void showRootEvents(List<RootEventResponse> rootEventResponses) {
    if (rootEventResponses.size() != 1) {
      LOG.error("Unexpected number of root events emitted: {}", rootEventResponses.size());
    }
    LOG.info("Root Event:");
    for (RootEventResponse rootEventResponse: rootEventResponses) {
      LOG.info(" _crossBlockchainTransactionId: 0x{}", rootEventResponse._crossBlockchainTransactionId.toString(16));
      LOG.info(" _success: {}", rootEventResponse._success);
    }
    if (rootEventResponses.size() != 1) {
      throw new RuntimeException("Undexpected number of root events: " + rootEventResponses.size());
    }
  }

  protected void showSegmentEvents(List<SegmentEventResponse> segmentEventResponses) {
    if (segmentEventResponses.size() != 1) {
      LOG.error("Unexpected number of segment events emitted: {}", segmentEventResponses.size());
    }
    LOG.info("Segment Event:");
    for (SegmentEventResponse segmentEventResponse : segmentEventResponses) {
      LOG.info(" Cross-Blockchain Transaction Id: 0x{}", segmentEventResponse._crossBlockchainTransactionId.toString(16));
      StringBuilder calls = new StringBuilder();
      for (BigInteger partOfCallPath : segmentEventResponse._callPath) {
        calls.append("[");
        calls.append(partOfCallPath);
        calls.append("] ");
      }
      LOG.info(" Call Path: {}", calls);
      LOG.info(" Hash Of Call Graph: {}", new BigInteger(1, segmentEventResponse._hashOfCallGraph).toString(16));
      LOG.info(" Success: {}", segmentEventResponse._success);
      LOG.info(" Return Value: {}", new BigInteger(1, segmentEventResponse._returnValue).toString(16));
      StringBuilder lockedContracts = new StringBuilder();
      for (String lockedContract: segmentEventResponse._lockedContracts) {
        calls.append("[");
        calls.append(lockedContract);
        calls.append("] ");
      }
      LOG.info(" Locked Contracts: [{}]", lockedContracts);
    }
    if (segmentEventResponses.size() != 1) {
      throw new RuntimeException("Undexpected number of segment events: " + segmentEventResponses.size());
    }
  }
}
