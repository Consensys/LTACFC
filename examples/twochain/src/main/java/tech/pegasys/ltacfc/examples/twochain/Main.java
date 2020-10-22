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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.CrossEventProof;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.examples.twochain.sim.SimOtherContract;
import tech.pegasys.ltacfc.examples.twochain.sim.SimRootContract;
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
    LOG.info("Started");
    Credentials creds;
    OtherBc otherBlockchain;
    RootBc rootBlockchain;


    switch (args.length) {
      case 0:
        creds = createCredentials();
        otherBlockchain = new OtherBc();
        rootBlockchain = new RootBc();
        break;
      case 1:
        PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
        creds = propsLoader.getCredentials();
        String bcId = propsLoader.getProperty("OTHER_BC_ID");
        LOG.info(" OTHER_BC_ID: {}", bcId);
        String uri = propsLoader.getProperty("OTHER_URI");
        LOG.info(" OTHER_URI: {}", uri);
        String gasPriceStrategy = propsLoader.getProperty("OTHER_GAS");
        LOG.info(" OTHER_GAS: {}", gasPriceStrategy);
        String blockPeriod = propsLoader.getProperty("OTHER_PERIOD");
        LOG.info(" OTHER_PERIOD: {}", blockPeriod);
        otherBlockchain = new OtherBc(bcId, uri, gasPriceStrategy, blockPeriod);

        bcId = propsLoader.getProperty("ROOT_BC_ID");
        LOG.info(" ROOT_BC_ID: {}", bcId);
        uri = propsLoader.getProperty("ROOT_URI");
        LOG.info(" ROOT_URI: {}", uri);
        gasPriceStrategy = propsLoader.getProperty("ROOT_GAS");
        LOG.info(" ROOT_GAS: {}", gasPriceStrategy);
        blockPeriod = propsLoader.getProperty("ROOT_PERIOD");
        LOG.info(" ROOT_PERIOD: {}", blockPeriod);
        rootBlockchain = new RootBc(bcId, uri, gasPriceStrategy, blockPeriod);
        break;
      default:
        LOG.info("Usage: [properties file name]");
        return;
    }


    // Set-up client side and deploy contracts on the blockchains.
    otherBlockchain.setupWeb3(creds);
    otherBlockchain.deployContracts();

    BigInteger otherBcId = otherBlockchain.blockchainId;
    String otherContractAddress = otherBlockchain.otherBlockchainContract.getContractAddress();

    rootBlockchain.setupWeb3(creds);
    rootBlockchain.deployContracts(otherBcId, otherContractAddress);


    BigInteger rootBcId = rootBlockchain.blockchainId;
    String rootBcCbcContractAddr = rootBlockchain.crossBlockchainControlContract.getContractAddress();
    String otherBcCbcContractAddr = otherBlockchain.crossBlockchainControlContract.getContractAddress();

    AnIdentity signer = new AnIdentity();
    otherBlockchain.registerSignerThisBlockchain(signer);
    otherBlockchain.registerSigner(signer, rootBcId);
    rootBlockchain.registerSignerThisBlockchain(signer);
    rootBlockchain.registerSigner(signer, otherBcId);

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
    CrossEventProof startProof = rootBlockchain.getProofForTxReceipt(rootBcId, rootBcCbcContractAddr, startTxReceipt);
    // Add tx receipt root so event will be trusted.
    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, startProof.getTransactionReceiptRoot());
    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, startProof.getTransactionReceiptRoot());


    // Set of all segment proofs needed for the root call.
    List<CrossEventProof> allSegmentProofs = new ArrayList<>();
    // Set of all segments need for the signal call on Other Blockchain.
    List<CrossEventProof> signalSegProofs = new ArrayList<>();


    LOG.info("segment: getVal");
    List<BigInteger> getValCallPath = new ArrayList<>();
    getValCallPath.add(BigInteger.ONE);
    TransactionReceipt segGetValTxReceipt = otherBlockchain.segment(startProof, getValCallPath);
    CrossEventProof segGetValProof = otherBlockchain.getProofForTxReceipt(otherBcId, otherBcCbcContractAddr, segGetValTxReceipt);
    allSegmentProofs.add(segGetValProof);
    // Add tx receipt root so event will be trusted.
    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());
    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());

    if (simRootContract.someComplexBusinessLogicIfTrue) {
      LOG.info("segment: setValues");
      List<BigInteger> setValuesCallPath = new ArrayList<>();
      setValuesCallPath.add(BigInteger.TWO);
      TransactionReceipt segSetValuesTxReceipt = otherBlockchain.segment(startProof, setValuesCallPath);
      CrossEventProof segSetValuesProof = otherBlockchain.getProofForTxReceipt(otherBcId, otherBcCbcContractAddr, segSetValuesTxReceipt);
      allSegmentProofs.add(segSetValuesProof);
      signalSegProofs.add(segSetValuesProof);
      // Add tx receipt root so event will be trusted.
      otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
      rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
    } else {

      LOG.info("segment: setVal");
      List<BigInteger> setValCallPath = new ArrayList<>();
      setValCallPath.add(BigInteger.TWO);
      TransactionReceipt segSetValTxReceipt = otherBlockchain.segment(startProof, setValCallPath);
      CrossEventProof segSetValProof = otherBlockchain.getProofForTxReceipt(otherBcId, otherBcCbcContractAddr, segSetValTxReceipt);
      allSegmentProofs.add(segSetValProof);
      signalSegProofs.add(segSetValProof);
      // Add tx receipt root so event will be trusted.
      otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
      rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
    }

    LOG.info("root");
    TransactionReceipt rootTxReceipt = rootBlockchain.root(startProof, allSegmentProofs);
    CrossEventProof rootProof = rootBlockchain.getProofForTxReceipt(rootBcId, rootBcCbcContractAddr, rootTxReceipt);
    // Add tx receipt root so event will be trusted.
    otherBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());
//    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());

    LOG.info("signalling");
    // Do a signal call on all blockchain that have had segment calls that have caused contracts to be locked.
    otherBlockchain.signalling(rootProof, signalSegProofs);


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
