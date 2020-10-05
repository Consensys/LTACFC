package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.bouncycastle.crypto.prng.drbg.HashSP800DRBG;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.hyperledger.besu.ethereum.rlp.RLPInput;
import org.web3j.abi.EventEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.Tuple;
import tech.pegasys.ltacfc.examples.twochain.sim.SimOtherContract;
import tech.pegasys.ltacfc.examples.twochain.sim.SimRootContract;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static java.security.DrbgParameters.Capability.RESEED_ONLY;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    Credentials creds = createCredentials();

    // Set-up client side and deploy contracts on the blockchains.
    OtherBc otherBlockchain = new OtherBc();
    otherBlockchain.setupWeb3(creds);
    otherBlockchain.deployContracts();

    BigInteger otherBcId = otherBlockchain.blockchainId;
    String otherContractAddress = otherBlockchain.otherBlockchainContract.getContractAddress();

    RootBc rootBlockchain = new RootBc();
    rootBlockchain.setupWeb3(creds);
    rootBlockchain.deployContracts(otherBcId, otherContractAddress);

    AnIdentity signer = new AnIdentity();
    otherBlockchain.registerSigner(signer);
    rootBlockchain.registerSigner(signer);

    // Create simulators
    SimOtherContract simOtherContract = new SimOtherContract();
    SimRootContract simRootContract = new SimRootContract(simOtherContract);

    // Do some single blockchain calls to set things up.
    BigInteger param = BigInteger.TEN;
    simOtherContract.setVal(param);
    otherBlockchain.setVal(param);

    param = BigInteger.valueOf(7);
    simRootContract.someComplexBusinessLogic(param);

    String rlpFunctionCall_SomeComplexBusinessLogic = rootBlockchain.getRlpFunctionSignature_SomeComplexBusinessLogic(param);
    LOG.info("rlpFunctionCall_SomeComplexBusinessLogic: {}", rlpFunctionCall_SomeComplexBusinessLogic);
    String rlpFunctionCall_GetVal = otherBlockchain.getRlpFunctionSignature_GetVal();
    LOG.info("rlpFunctionCall_GetVal: {}", rlpFunctionCall_GetVal);
    String rlpFunctionCall_SetValues = null;
    String rlpFunctionCall_SetVal = null;
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      rlpFunctionCall_SetValues = otherBlockchain.getRlpFunctionSignature_SetValues(
          simRootContract.someComplexBusinessLogicSetValuesParameter1,
          simRootContract.someComplexBusinessLogicSetValuesParameter2);
      LOG.info("rlpFunctionCall_SetValues: {}", rlpFunctionCall_SetValues);
    }
    else {
      rlpFunctionCall_SetVal = otherBlockchain.getRlpFunctionSignature_SetVal(simRootContract.someComplexBusinessLogicSetValParameter);
      LOG.info("rlpFunctionCall_SetVal: {}", rlpFunctionCall_SetVal);
    }

    RlpList callGraph;
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      RlpList getVal = createLeafFunctionCall(otherBlockchain.blockchainId, otherBlockchain.otherBlockchainContract.getContractAddress(), rlpFunctionCall_GetVal);
      RlpList setValues = createLeafFunctionCall(otherBlockchain.blockchainId, otherBlockchain.otherBlockchainContract.getContractAddress(), rlpFunctionCall_SetValues);
      List<RlpType> calls = new ArrayList<>();
      calls.add(getVal);
      calls.add(setValues);
      callGraph = createRootFunctionCall(
          rootBlockchain.blockchainId, rootBlockchain.rootBlockchainContract.getContractAddress(), rlpFunctionCall_SomeComplexBusinessLogic, calls);
    }
    else {
      RlpList getVal = createLeafFunctionCall(otherBlockchain.blockchainId, otherBlockchain.otherBlockchainContract.getContractAddress(), rlpFunctionCall_GetVal);
      RlpList setVal = createLeafFunctionCall(otherBlockchain.blockchainId, otherBlockchain.otherBlockchainContract.getContractAddress(), rlpFunctionCall_SetVal);
      List<RlpType> calls = new ArrayList<>();
      calls.add(getVal);
      calls.add(setVal);
      callGraph = createRootFunctionCall(
          rootBlockchain.blockchainId, rootBlockchain.rootBlockchainContract.getContractAddress(), rlpFunctionCall_SomeComplexBusinessLogic, calls);
    }


    // TODO put this into the crypto module and do a better job or this + reseeding.
    final SecureRandom rand = SecureRandom.getInstance("DRBG",
        DrbgParameters.instantiation(256, RESEED_ONLY, new byte[]{0x01}));
    BigInteger crossBlockchainTransactionId1 = new BigInteger(255, rand);
    BigInteger timeout = BigInteger.valueOf(100);

    LOG.info("start");
    TransactionReceipt startTxReceipt = rootBlockchain.start(crossBlockchainTransactionId1, timeout, RlpEncoder.encode(callGraph));
    byte[] startTxReceiptRoot = rootBlockchain.getTransactionReceiptRoot(startTxReceipt);
    // Add tx receipt root so event will be trusted.
    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBlockchain.blockchainId, startTxReceiptRoot);
    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBlockchain.blockchainId, startTxReceiptRoot);
    // Work out the proof.
    Tuple<List<byte[]>, List<BigInteger>, byte[]> startRetVal = rootBlockchain.getProofForTxReceipt(startTxReceipt);
    List<byte[]> startProof = startRetVal.getFirst();
    List<BigInteger> startProofOffsets = startRetVal.getSecond();
    byte[] startEncodedTxReceiptBytes = startRetVal.getThird();

    //    Log startEventLog = startTxReceipt.getLogs().get(0);
//    String eventData = startEventLog.getData();
//    LOG.info("Event Address: {}", startEventLog.getAddress());
//    LOG.info("Event Topics length: {}", startEventLog.getTopics().size());
//    LOG.info("Event Topics(0): {}", startEventLog.getTopics().get(0));
//    LOG.info("Event Data: {}", eventData);

//    List<CrossBlockchainControl.StartEventResponse> startEventResponses = rootBlockchain.crossBlockchainControlContract.getStartEvents(startTxReceipt);
//    for (CrossBlockchainControl.StartEventResponse startEventResponse: startEventResponses) {
//      LOG.info("TxId: {}", startEventResponse._crossBlockchainTransactionId.toString(16));
//      LOG.info("Timeout: {}", startEventResponse._timeout.toString(16));
//      LOG.info("Call Graph: {}", new BigInteger(1, startEventResponse._callGraph).toString(16));
//    }




    // Prepare for root transaction
    List<BigInteger> segmentBlockchainIds = new ArrayList<>();
    List<String> segmentBlockchainCBCs = new ArrayList<>();
    List<byte[]> segmentTxReceiptRoots = new ArrayList<>();
    List<byte[]> segmentTxReceipts = new ArrayList<>();
    List<List<BigInteger>> segmentProofOffsets = new ArrayList<>();
    List<List<byte[]>> segmentProofs = new ArrayList<>();

    LOG.info("segment: getVal");
    List<BigInteger> getValCallPath = new ArrayList<>();
    getValCallPath.add(BigInteger.ONE);
    TransactionReceipt segGetValTxReceipt = otherBlockchain.segment(
        rootBlockchain.blockchainId, rootBlockchain.crossBlockchainControlContract.getContractAddress(),
        startTxReceiptRoot, startEncodedTxReceiptBytes, startProofOffsets, startProof, getValCallPath);
    byte[] segGetValTxReceiptRoot = otherBlockchain.getTransactionReceiptRoot(segGetValTxReceipt);
    // Add tx receipt root so event will be trusted.
    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segGetValTxReceiptRoot);
    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segGetValTxReceiptRoot);

    BigInteger segGetValBlockchainId = otherBlockchain.blockchainId;
    String segGetValBlockchainCBC = otherBlockchain.crossBlockchainControlContract.getContractAddress();

    // Work out the proof.
    Tuple<List<byte[]>, List<BigInteger>, byte[]> segGetValRetVal = otherBlockchain.getProofForTxReceipt(segGetValTxReceipt);
    List<byte[]> segGetValProof = segGetValRetVal.getFirst();
    List<BigInteger> segGetValProofOffsets = segGetValRetVal.getSecond();
    byte[] segGetValEncodedTxReceiptBytes = segGetValRetVal.getThird();

    segmentBlockchainIds.add(segGetValBlockchainId);
    segmentBlockchainCBCs.add(segGetValBlockchainCBC);
    segmentTxReceiptRoots.add(segGetValTxReceiptRoot);
    segmentTxReceipts.add(segGetValEncodedTxReceiptBytes);
    segmentProofOffsets.add(segGetValProofOffsets);
    segmentProofs.add(segGetValProof);



    if (simRootContract.someComplexBusinessLogicIfTrue) {
      LOG.info("segment: setValues");
      List<BigInteger> setValuesCallPath = new ArrayList<>();
      setValuesCallPath.add(BigInteger.TWO);
      TransactionReceipt segSetValuesTxReceipt = otherBlockchain.segment(
          rootBlockchain.blockchainId, rootBlockchain.crossBlockchainControlContract.getContractAddress(),
          startTxReceiptRoot, startEncodedTxReceiptBytes, startProofOffsets, startProof, setValuesCallPath);
      byte[] segSetValuesTxReceiptRoot = otherBlockchain.getTransactionReceiptRoot(segSetValuesTxReceipt);
      // Add tx receipt root so event will be trusted.
      otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segSetValuesTxReceiptRoot);
      rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segSetValuesTxReceiptRoot);

      BigInteger segSetValuesBlockchainId = otherBlockchain.blockchainId;
      String segSetValuesBlockchainCBC = otherBlockchain.crossBlockchainControlContract.getContractAddress();

      // Work out the proof.
      Tuple<List<byte[]>, List<BigInteger>, byte[]> segSetValuesRetVal = otherBlockchain.getProofForTxReceipt(segSetValuesTxReceipt);
      List<byte[]> segSetValuesProof = segSetValuesRetVal.getFirst();
      List<BigInteger> segSetValuesProofOffsets = segSetValuesRetVal.getSecond();
      byte[] segSetValuesEncodedTxReceiptBytes = segSetValuesRetVal.getThird();

      segmentBlockchainIds.add(segSetValuesBlockchainId);
      segmentBlockchainCBCs.add(segSetValuesBlockchainCBC);
      segmentTxReceiptRoots.add(segSetValuesTxReceiptRoot);
      segmentTxReceipts.add(segSetValuesEncodedTxReceiptBytes);
      segmentProofOffsets.add(segSetValuesProofOffsets);
      segmentProofs.add(segSetValuesProof);
    } else {

      LOG.info("segment: setVal");
      List<BigInteger> setValCallPath = new ArrayList<>();
      setValCallPath.add(BigInteger.TWO);
      TransactionReceipt segSetValTxReceipt = otherBlockchain.segment(
          rootBlockchain.blockchainId, rootBlockchain.crossBlockchainControlContract.getContractAddress(),
          startTxReceiptRoot, startEncodedTxReceiptBytes, startProofOffsets, startProof, setValCallPath);
      byte[] segSetValTxReceiptRoot = otherBlockchain.getTransactionReceiptRoot(segSetValTxReceipt);
      // Add tx receipt root so event will be trusted.
      otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segSetValTxReceiptRoot);
      rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBlockchain.blockchainId, segSetValTxReceiptRoot);

      BigInteger segSetValBlockchainId = otherBlockchain.blockchainId;
      String segSetValBlockchainCBC = otherBlockchain.crossBlockchainControlContract.getContractAddress();

      // Work out the proof.
      Tuple<List<byte[]>, List<BigInteger>, byte[]> segSetValRetVal = otherBlockchain.getProofForTxReceipt(segSetValTxReceipt);
      List<byte[]> segSetValProof = segSetValRetVal.getFirst();
      List<BigInteger> segSetValProofOffsets = segSetValRetVal.getSecond();
      byte[] segSetValEncodedTxReceiptBytes = segSetValRetVal.getThird();

      segmentBlockchainIds.add(segSetValBlockchainId);
      segmentBlockchainCBCs.add(segSetValBlockchainCBC);
      segmentTxReceiptRoots.add(segSetValTxReceiptRoot);
      segmentTxReceipts.add(segSetValEncodedTxReceiptBytes);
      segmentProofOffsets.add(segSetValProofOffsets);
      segmentProofs.add(segSetValProof);

    }

    LOG.info("root");
    rootBlockchain.root(
        startTxReceiptRoot, startEncodedTxReceiptBytes, startProofOffsets, startProof,
        segmentBlockchainIds, segmentBlockchainCBCs,
        segmentTxReceiptRoots, segmentTxReceipts, segmentProofOffsets, segmentProofs);

    LOG.info(" Other contract's storage is locked: {}", otherBlockchain.storageIsLocked());



  }


  public static RlpList createRootFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString, List<RlpType>  calledFunctions) {
    return createIntermediateFunctionCall(blockchainId, contractAddress, rlpBytesAsString, calledFunctions);
  }

  public static RlpList createIntermediateFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString, List<RlpType> calledFunctions) {
    RlpList func = createFunctionCall(blockchainId, contractAddress, rlpBytesAsString);
    calledFunctions.add(0, (RlpType) func);
    return new RlpList(calledFunctions);
  }

  public static RlpList createLeafFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString) {
    return createFunctionCall(blockchainId, contractAddress, rlpBytesAsString);
  }

  public static RlpList createFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString) {
    return new RlpList(
        RlpString.create(blockchainId),
        toRlpString(contractAddress),
        toRlpString(rlpBytesAsString)
    );
  }



  public static RlpString toRlpString(String rlpBytesAsString) {
    return RlpString.create(new BigInteger(rlpBytesAsString.substring(2), 16));
  }

  public static Credentials createCredentials() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
//    System.out.println("Priv2: " + privateKey);
    return Credentials.create(privateKey);
  }
}
