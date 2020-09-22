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
    System.out.println("rlpFunctionCall_SomeComplexBusinessLogic: " + rlpFunctionCall_SomeComplexBusinessLogic);
    String rlpFunctionCall_GetVal = otherBlockchain.getRlpFunctionSignature_GetVal();
    String rlpFunctionCall_SetValues = null;
    String rlpFunctionCall_SetVal = null;
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      rlpFunctionCall_SetValues = otherBlockchain.getRlpFunctionSignature_SetValues(
          simRootContract.someComplexBusinessLogicSetValuesParameter1,
          simRootContract.someComplexBusinessLogicSetValuesParameter2);
    }
    else {
      rlpFunctionCall_SetVal = otherBlockchain.getRlpFunctionSignature_SetVal(simRootContract.someComplexBusinessLogicSetValParameter);
    }

    RlpList callGraph;
    if (simRootContract.someComplexBusinessLogicIfTrue) {
      RlpList getVal = createLeafFunctionCall(otherBlockchain.blockchainId, rlpFunctionCall_GetVal);
      RlpList setValues = createLeafFunctionCall(otherBlockchain.blockchainId, rlpFunctionCall_SetValues);
      callGraph = createRootFunctionCall(rootBlockchain.blockchainId, rlpFunctionCall_SomeComplexBusinessLogic,
        new RlpList(
            getVal,
            setValues
        ));
    }
    else {
      RlpList getVal = createLeafFunctionCall(otherBlockchain.blockchainId, rlpFunctionCall_GetVal);
      RlpList setVal = createLeafFunctionCall(otherBlockchain.blockchainId, rlpFunctionCall_SetVal);
      callGraph = createRootFunctionCall(rootBlockchain.blockchainId, rlpFunctionCall_SomeComplexBusinessLogic,
          new RlpList(
              getVal,
              setVal
          ));
    }


    // TODO put this into the crypto module and do a better job or this + reseeding.
    final SecureRandom rand = SecureRandom.getInstance("DRBG",
        DrbgParameters.instantiation(256, RESEED_ONLY, new byte[]{0x01}));
    BigInteger crossBlockchainTransactionId1 = new BigInteger(255, rand);
    BigInteger timeout = BigInteger.valueOf(100);

    LOG.info("start");
    TransactionReceipt startTxReceipt = rootBlockchain.start(crossBlockchainTransactionId1, timeout, RlpEncoder.encode(callGraph));
    byte[] transactionReceiptRoot = rootBlockchain.getTransactionReceiptRoot(startTxReceipt);
    Log startEventLog = startTxReceipt.getLogs().get(0);
    String eventData = startEventLog.getData();
    LOG.info("Event Address: {}", startEventLog.getAddress());
    LOG.info("Event Topics length: {}", startEventLog.getTopics().size());
    LOG.info("Event Topics(0): {}", startEventLog.getTopics().get(0));
    LOG.info("Event Data: {}", eventData);

    List<CrossBlockchainControl.StartEventResponse> startEventResponses = rootBlockchain.crossBlockchainControlContract.getStartEvents(startTxReceipt);
    for (CrossBlockchainControl.StartEventResponse startEventResponse: startEventResponses) {
      LOG.info("TxId: {}", startEventResponse._crossBlockchainTransactionId.toString(16));
      LOG.info("Timeout: {}", startEventResponse._timeout.toString(16));
      LOG.info("Call Graph: {}", new BigInteger(1, startEventResponse._callGraph).toString(16));
    }


    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBlockchain.blockchainId, transactionReceiptRoot);
    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBlockchain.blockchainId, transactionReceiptRoot);

    LOG.info("Start Event encoded: {}", EventEncoder.encode(CrossBlockchainControl.START_EVENT));
    LOG.info("Root BC CBS Address: {}", rootBlockchain.crossBlockchainControlContract.getContractAddress());

    Tuple<List<byte[]>, List<BigInteger>, byte[]> retVal = rootBlockchain.getProofForTxReceipt(startTxReceipt);
    List<byte[]> proof = retVal.getFirst();
    List<BigInteger> proofOffsets = retVal.getSecond();
    byte[] encodedStartTxReceiptBytes = retVal.getThird();

    RLPInput rlpIn = RLP.input(Bytes.wrap(encodedStartTxReceiptBytes));
    rlpIn.enterList();
    rlpIn.readBytes();
    Bytes rec = rlpIn.readBytes();
    LOG.info("outer");
    RlpDumper.dump(RLP.input(Bytes.wrap(encodedStartTxReceiptBytes)));
    LOG.info("inner");
    RlpDumper.dump(RLP.input(rec));



    List<BigInteger> callPath = new ArrayList<>();
    callPath.add(BigInteger.ONE);


    otherBlockchain.segment(
        rootBlockchain.blockchainId, rootBlockchain.crossBlockchainControlContract.getContractAddress(),
        transactionReceiptRoot, encodedStartTxReceiptBytes, proofOffsets, proof, callPath);
    //TODO






  }


  public static RlpList createRootFunctionCall(BigInteger blockchainId, String rlpBytesAsString, RlpList calledFunctions) {
    return new RlpList(
        createFunctionCall(blockchainId, rlpBytesAsString),
        calledFunctions
    );
  }

  public static RlpList createIntermediateFunctionCall(BigInteger blockchainId, String rlpBytesAsString, RlpList calledFunctions) {
    return new RlpList(
        createFunctionCall(blockchainId, rlpBytesAsString),
        calledFunctions
    );
  }

  public static RlpList createLeafFunctionCall(BigInteger blockchainId, String rlpBytesAsString) {
    return createFunctionCall(blockchainId, rlpBytesAsString);
  }

  public static RlpList createFunctionCall(BigInteger blockchainId, String rlpBytesAsString) {
    return new RlpList(
        RlpString.create(blockchainId),
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
