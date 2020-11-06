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
package tech.pegasys.ltacfc.examples.complex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.AbstractCbc;
import tech.pegasys.ltacfc.cbc.CrossBlockchainControlSignedEvents;
import tech.pegasys.ltacfc.cbc.CrossBlockchainControlTxReceiptRootTransfer;
import tech.pegasys.ltacfc.cbc.SignedEvent;
import tech.pegasys.ltacfc.cbc.TxReceiptRootTransferEventProof;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;
import tech.pegasys.ltacfc.common.PropertiesLoader;
import tech.pegasys.ltacfc.common.StatsHolder;
import tech.pegasys.ltacfc.examples.complex.sim.SimOtherContract;
import tech.pegasys.ltacfc.examples.complex.sim.SimRootContract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static tech.pegasys.ltacfc.cbc.CallGraphHelper.createLeafFunctionCall;
import static tech.pegasys.ltacfc.cbc.CallGraphHelper.createRootFunctionCall;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    StatsHolder.log("Start");
    LOG.info("Started");

    if (args.length != 1) {
      LOG.info("Usage: [properties file name]");
      return;
    }

    PropertiesLoader propsLoader = new PropertiesLoader(args[0]);
    Credentials creds = propsLoader.getCredentials();
    PropertiesLoader.BlockchainInfo root = propsLoader.getBlockchainInfo("ROOT");
    PropertiesLoader.BlockchainInfo bc2 = propsLoader.getBlockchainInfo("BC2");
    PropertiesLoader.BlockchainInfo bc3 = propsLoader.getBlockchainInfo("BC3");
    PropertiesLoader.BlockchainInfo bc4 = propsLoader.getBlockchainInfo("BC4");
    PropertiesLoader.BlockchainInfo bc5 = propsLoader.getBlockchainInfo("BC5");
    CrossBlockchainConsensus consensusMethodology = propsLoader.getConsensusMethodology();
    StatsHolder.log(consensusMethodology.name());

    RootBc rootBlockchain = new RootBc(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2BusLogic bc2BusLogicBlockchain = new Bc2BusLogic(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
    Bc3Balances bc3BalancesBlockchain = new Bc3Balances(creds, bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
    Bc4Oracle bc4OracleBlockchain = new Bc4Oracle(creds, bc4.bcId, bc4.uri, bc4.gasPriceStrategy, bc4.period);
    Bc5Stock bc5StockBlockchain = new Bc5Stock(creds, bc5.bcId, bc5.uri, bc5.gasPriceStrategy, bc5.period);

    CrossBlockchainControlTxReceiptRootTransfer rootBlockchainCbcTxRootTransfer = null;
    CrossBlockchainControlTxReceiptRootTransfer bc2BlockchainCbcTxRootTransfer = null;
    CrossBlockchainControlTxReceiptRootTransfer bc3BlockchainCbcTxRootTransfer = null;
    CrossBlockchainControlTxReceiptRootTransfer bc4BlockchainCbcTxRootTransfer = null;
    CrossBlockchainControlTxReceiptRootTransfer bc5BlockchainCbcTxRootTransfer = null;
    CrossBlockchainControlSignedEvents rootBlockchainCbcSignedEvents = null;
    CrossBlockchainControlSignedEvents bc2BlockchainCbcSignedEvents = null;
    CrossBlockchainControlSignedEvents bc3BlockchainCbcSignedEvents = null;
    CrossBlockchainControlSignedEvents bc4BlockchainCbcSignedEvents = null;
    CrossBlockchainControlSignedEvents bc5BlockchainCbcSignedEvents = null;
    AbstractCbc rootBlockchainCbc = null;
    AbstractCbc bc2BlockchainCbc = null;
    AbstractCbc bc3BlockchainCbc = null;
    AbstractCbc bc4BlockchainCbc = null;
    AbstractCbc bc5BlockchainCbc = null;
    String rootBlockchainCbcContractAddress;
    String bc2BlockchainCbcContractAddress;
    String bc3BlockchainCbcContractAddress;
    String bc4BlockchainCbcContractAddress;
    String bc5BlockchainCbcContractAddress;

    switch (consensusMethodology) {
      case TRANSACTION_RECEIPT_SIGNING:
        rootBlockchainCbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
        bc2BlockchainCbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
        bc3BlockchainCbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(creds, bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
        bc4BlockchainCbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(creds, bc4.bcId, bc4.uri, bc4.gasPriceStrategy, bc4.period);
        bc5BlockchainCbcTxRootTransfer = new CrossBlockchainControlTxReceiptRootTransfer(creds, bc5.bcId, bc5.uri, bc5.gasPriceStrategy, bc5.period);
        rootBlockchainCbcTxRootTransfer.deployContracts();
        bc2BlockchainCbcTxRootTransfer.deployContracts();
        bc3BlockchainCbcTxRootTransfer.deployContracts();
        bc4BlockchainCbcTxRootTransfer.deployContracts();
        bc5BlockchainCbcTxRootTransfer.deployContracts();
        rootBlockchainCbcContractAddress = rootBlockchainCbcTxRootTransfer.getCbcContractAddress();
        bc2BlockchainCbcContractAddress = bc2BlockchainCbcTxRootTransfer.getCbcContractAddress();
        bc3BlockchainCbcContractAddress = bc3BlockchainCbcTxRootTransfer.getCbcContractAddress();
        bc4BlockchainCbcContractAddress = bc4BlockchainCbcTxRootTransfer.getCbcContractAddress();
        bc5BlockchainCbcContractAddress = bc5BlockchainCbcTxRootTransfer.getCbcContractAddress();
        rootBlockchainCbc = rootBlockchainCbcTxRootTransfer;
        bc2BlockchainCbc = bc2BlockchainCbcTxRootTransfer;
        bc3BlockchainCbc = bc3BlockchainCbcTxRootTransfer;
        bc4BlockchainCbc = bc4BlockchainCbcTxRootTransfer;
        bc5BlockchainCbc = bc5BlockchainCbcTxRootTransfer;
        break;
      case EVENT_SIGNING:
        rootBlockchainCbcSignedEvents = new CrossBlockchainControlSignedEvents(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
        bc2BlockchainCbcSignedEvents = new CrossBlockchainControlSignedEvents(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
        bc3BlockchainCbcSignedEvents = new CrossBlockchainControlSignedEvents(creds, bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
        bc4BlockchainCbcSignedEvents = new CrossBlockchainControlSignedEvents(creds, bc4.bcId, bc4.uri, bc4.gasPriceStrategy, bc4.period);
        bc5BlockchainCbcSignedEvents = new CrossBlockchainControlSignedEvents(creds, bc5.bcId, bc5.uri, bc5.gasPriceStrategy, bc5.period);
        rootBlockchainCbcSignedEvents.deployContracts();
        bc2BlockchainCbcSignedEvents.deployContracts();
        bc3BlockchainCbcSignedEvents.deployContracts();
        bc4BlockchainCbcSignedEvents.deployContracts();
        bc5BlockchainCbcSignedEvents.deployContracts();
        rootBlockchainCbcContractAddress = rootBlockchainCbcSignedEvents.getCbcContractAddress();
        bc2BlockchainCbcContractAddress = bc2BlockchainCbcSignedEvents.getCbcContractAddress();
        bc3BlockchainCbcContractAddress = bc3BlockchainCbcSignedEvents.getCbcContractAddress();
        bc4BlockchainCbcContractAddress = bc4BlockchainCbcSignedEvents.getCbcContractAddress();
        bc5BlockchainCbcContractAddress = bc5BlockchainCbcSignedEvents.getCbcContractAddress();
        rootBlockchainCbc = rootBlockchainCbcSignedEvents;
        bc2BlockchainCbc = bc2BlockchainCbcSignedEvents;
        bc3BlockchainCbc = bc3BlockchainCbcSignedEvents;
        bc4BlockchainCbc = bc4BlockchainCbcSignedEvents;
        bc5BlockchainCbc = bc5BlockchainCbcSignedEvents;
        break;
      default:
        throw new RuntimeException("Unknown consensus methodology");
    }


//    // Set-up client side and deploy contracts on the blockchains.
//    bc2BusLogicBlockchain.deployContracts(bc2BlockchainCbcContractAddress);
//    BigInteger otherBcId = bc2BusLogicBlockchain.getBlockchainId();
//    String otherBlockchainContractAddress = bc2BusLogicBlockchain.busLogicContract.getContractAddress();
//
//    rootBlockchain.deployContracts(rootBlockchainCbcContractAddress, otherBcId, otherBlockchainContractAddress);
//    BigInteger rootBcId = rootBlockchain.getBlockchainId();
//
//    // To make the example simple, just have one signer, and have the same signer for all blockchains.
//    AnIdentity signer = new AnIdentity();
//    bc2BlockchainCbc.registerSignerThisBlockchain(signer);
//    bc2BlockchainCbc.registerSigner(signer, rootBcId);
//    rootBlockchainCbc.registerSignerThisBlockchain(signer);
//    rootBlockchainCbc.registerSigner(signer, otherBcId);
//
//    // Create simulators
//    SimOtherContract simOtherContract = new SimOtherContract();
//    SimRootContract simRootContract = new SimRootContract(simOtherContract);

//    // Do some single blockchain calls to set things up, to show that values have changed.
//    // Ensure the simulator is set-up the same way.
//    BigInteger otherBcValInitialValue = BigInteger.valueOf(77);
//    simOtherContract.setVal(otherBcValInitialValue);
//    bc2BusLogicBlockchain.setVal(otherBcValInitialValue);
//
//    BigInteger rootBcVal1InitialValue = BigInteger.valueOf(78);
//    simRootContract.setVal1(rootBcVal1InitialValue);
//    rootBlockchain.setVal1(rootBcVal1InitialValue);
//    BigInteger rootBcVal2InitialValue = BigInteger.valueOf(79);
//    simRootContract.setVal2(rootBcVal2InitialValue);
//    rootBlockchain.setVal2(rootBcVal2InitialValue);
//
//    // Simulate passing in the parameter value 7 for the cross-blockchain call.
//    BigInteger param = BigInteger.valueOf(7);
//    simRootContract.someComplexBusinessLogic(param);
//
//    String rlpFunctionCall_SomeComplexBusinessLogic = rootBlockchain.getRlpFunctionSignature_SomeComplexBusinessLogic(param);
//    LOG.info("rlpFunctionCall_SomeComplexBusinessLogic: {}", rlpFunctionCall_SomeComplexBusinessLogic);
//    String rlpFunctionCall_GetVal = bc2BusLogicBlockchain.getRlpFunctionSignature_GetVal();
//    LOG.info("rlpFunctionCall_GetVal: {}", rlpFunctionCall_GetVal);
//    String rlpFunctionCall_SetValues = null;
//    String rlpFunctionCall_SetVal = null;
//    if (simRootContract.someComplexBusinessLogicIfTrue) {
//      rlpFunctionCall_SetValues = bc2BusLogicBlockchain.getRlpFunctionSignature_SetValues(
//          simRootContract.someComplexBusinessLogicSetValuesParameter1,
//          simRootContract.someComplexBusinessLogicSetValuesParameter2);
//      LOG.info("rlpFunctionCall_SetValues: {}", rlpFunctionCall_SetValues);
//    }
//    else {
//      rlpFunctionCall_SetVal = bc2BusLogicBlockchain.getRlpFunctionSignature_SetVal(simRootContract.someComplexBusinessLogicSetValParameter);
//      LOG.info("rlpFunctionCall_SetVal: {}", rlpFunctionCall_SetVal);
//    }
//
//    RlpList callGraph;
//    if (simRootContract.someComplexBusinessLogicIfTrue) {
//      RlpList getVal = createLeafFunctionCall(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_GetVal);
//      RlpList setValues = createLeafFunctionCall(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_SetValues);
//      List<RlpType> calls = new ArrayList<>();
//      calls.add(getVal);
//      calls.add(setValues);
//      callGraph = createRootFunctionCall(
//          rootBcId, rootBlockchain.rootBlockchainContract.getContractAddress(), rlpFunctionCall_SomeComplexBusinessLogic, calls);
//    }
//    else {
//      RlpList getVal = createLeafFunctionCall(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_GetVal);
//      RlpList setVal = createLeafFunctionCall(otherBcId, otherBlockchainContractAddress, rlpFunctionCall_SetVal);
//      List<RlpType> calls = new ArrayList<>();
//      calls.add(getVal);
//      calls.add(setVal);
//      callGraph = createRootFunctionCall(
//          rootBcId, rootBlockchain.rootBlockchainContract.getContractAddress(), rlpFunctionCall_SomeComplexBusinessLogic, calls);
//    }
//
//    BigInteger crossBlockchainTransactionId1 = AbstractCbc.generateRandomCrossBlockchainTransactionId();
//    BigInteger timeout = BigInteger.valueOf(300);
//
//    LOG.info("start");
//    boolean success;
//    switch (consensusMethodology) {
//      case TRANSACTION_RECEIPT_SIGNING:
//        TransactionReceipt startTxReceipt = rootBlockchainCbcTxRootTransfer.start(crossBlockchainTransactionId1, timeout, RlpEncoder.encode(callGraph));
//        TxReceiptRootTransferEventProof startProof = rootBlockchainCbcTxRootTransfer.getStartEventProof(startTxReceipt);
//
//        // Add tx receipt root so event will be trusted.
//        bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, startProof.getTransactionReceiptRoot());
//        rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, startProof.getTransactionReceiptRoot());
//
//
//        // Set of all segment proofs needed for the root call.
//        List<TxReceiptRootTransferEventProof> allSegmentProofs = new ArrayList<>();
//        // Set of all segments need for the signal call on Other Blockchain.
//        List<TxReceiptRootTransferEventProof> signalSegProofs = new ArrayList<>();
//
//
//        LOG.info("segment: getVal");
//        StatsHolder.log("segment: getVal");
//        List<BigInteger> getValCallPath = new ArrayList<>();
//        getValCallPath.add(BigInteger.ONE);
//        TransactionReceipt segGetValTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, getValCallPath);
//        TxReceiptRootTransferEventProof segGetValProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segGetValTxReceipt);
//        allSegmentProofs.add(segGetValProof);
//        // Add tx receipt root so event will be trusted.
//        bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());
//        rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segGetValProof.getTransactionReceiptRoot());
//
//        if (simRootContract.someComplexBusinessLogicIfTrue) {
//          LOG.info("segment: setValues");
//          StatsHolder.log("segment: setValues");
//          List<BigInteger> setValuesCallPath = new ArrayList<>();
//          setValuesCallPath.add(BigInteger.TWO);
//          TransactionReceipt segSetValuesTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, setValuesCallPath);
//          TxReceiptRootTransferEventProof segSetValuesProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segSetValuesTxReceipt);
//          allSegmentProofs.add(segSetValuesProof);
//          signalSegProofs.add(segSetValuesProof);
//          // Add tx receipt root so event will be trusted.
//          bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
//          rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValuesProof.getTransactionReceiptRoot());
//
//        } else {
//          LOG.info("segment: setVal");
//          StatsHolder.log("segment: setVal");
//          List<BigInteger> setValCallPath = new ArrayList<>();
//          setValCallPath.add(BigInteger.TWO);
//          TransactionReceipt segSetValTxReceipt = bc2BlockchainCbcTxRootTransfer.segment(startProof, setValCallPath);
//          TxReceiptRootTransferEventProof segSetValProof = bc2BlockchainCbcTxRootTransfer.getSegmentEventProof(segSetValTxReceipt);
//          allSegmentProofs.add(segSetValProof);
//          signalSegProofs.add(segSetValProof);
//          // Add tx receipt root so event will be trusted.
//          bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
//          rootBlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, otherBcId, segSetValProof.getTransactionReceiptRoot());
//        }
//
//        LOG.info("root");
//        TransactionReceipt rootTxReceipt = rootBlockchainCbcTxRootTransfer.root(startProof, allSegmentProofs);
//        TxReceiptRootTransferEventProof rootProof = rootBlockchainCbcTxRootTransfer.getRootEventProof(rootTxReceipt);
//        // Add tx receipt root so event will be trusted.
//        bc2BlockchainCbcTxRootTransfer.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());
////    rootBlockchain.addTransactionReceiptRootToBlockchain(new AnIdentity[]{signer}, rootBcId, rootProof.getTransactionReceiptRoot());
//
//        LOG.info("signalling");
//        // Do a signal call on all blockchain that have had segment calls that have caused contracts to be locked.
//        bc2BlockchainCbcTxRootTransfer.signalling(rootProof, signalSegProofs);
//
//        success = rootBlockchainCbcTxRootTransfer.getRootEventSuccess();
//
//        rootBlockchainCbcTxRootTransfer.shutdown();
//        bc2BlockchainCbcTxRootTransfer.shutdown();
//        break;
//
//
//      case EVENT_SIGNING:
//        byte[] startEventData = rootBlockchainCbcSignedEvents.start(crossBlockchainTransactionId1, timeout, RlpEncoder.encode(callGraph));
//        SignedEvent signedStartEvent = new SignedEvent(new AnIdentity[]{signer},
//            rootBcId, rootBlockchainCbcContractAddress, AbstractCbc.START_EVENT_SIGNATURE, startEventData);
//
//        // Set of all segment event information needed for the root call.
//        List<SignedEvent> allSegmentEvents = new ArrayList<>();
//        // Set of all segments need for the signal call on Other Blockchain.
//        List<SignedEvent> signalSegEvents = new ArrayList<>();
//
//
//        LOG.info("segment: getVal");
//        StatsHolder.log("segment: getVal");
//        getValCallPath = new ArrayList<>();
//        getValCallPath.add(BigInteger.ONE);
//        byte[] segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, getValCallPath);
//        SignedEvent segGetValEvent = new SignedEvent(new AnIdentity[]{signer},
//            otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//        allSegmentEvents.add(segGetValEvent);
//
//        if (simRootContract.someComplexBusinessLogicIfTrue) {
//          LOG.info("segment: setValues");
//          StatsHolder.log("segment: setValues");
//          List<BigInteger> setValuesCallPath = new ArrayList<>();
//          setValuesCallPath.add(BigInteger.TWO);
//          segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, setValuesCallPath);
//          SignedEvent segSetValuesEvent = new SignedEvent(new AnIdentity[]{signer},
//              otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//          allSegmentEvents.add(segSetValuesEvent);
//          signalSegEvents.add(segSetValuesEvent);
//        } else {
//
//          LOG.info("segment: setVal");
//          StatsHolder.log("segment: setVal");
//          List<BigInteger> setValCallPath = new ArrayList<>();
//          setValCallPath.add(BigInteger.TWO);
//          segEventData = bc2BlockchainCbcSignedEvents.segment(signedStartEvent, setValCallPath);
//          SignedEvent segSetValEvent = new SignedEvent(new AnIdentity[]{signer},
//              otherBcId, bc2BlockchainCbcContractAddress, AbstractCbc.SEGMENT_EVENT_SIGNATURE, segEventData);
//          allSegmentEvents.add(segSetValEvent);
//          signalSegEvents.add(segSetValEvent);
//        }
//
//        LOG.info("root");
//        byte[] rootEventData = rootBlockchainCbcSignedEvents.root(signedStartEvent, allSegmentEvents);
//        SignedEvent rootEvent = new SignedEvent(new AnIdentity[]{signer},
//            rootBcId, rootBlockchainCbcContractAddress, AbstractCbc.ROOT_EVENT_SIGNATURE, rootEventData);
//
//        LOG.info("signalling");
//        // Do a signal call on all blockchain that have had segment calls that have caused contracts to be locked.
//        bc2BlockchainCbcSignedEvents.signalling(rootEvent, signalSegEvents);
//
//        success = rootBlockchainCbcSignedEvents.getRootEventSuccess();
//
//        rootBlockchainCbcSignedEvents.shutdown();
//        bc2BlockchainCbcSignedEvents.shutdown();
//        break;
//
//      default:
//        throw new RuntimeException("Unknown consensus type");
//    }
//
//
//    LOG.info("Cross-Blockchain Transaction was successful: {}", success);
//    if (success) {
//      LOG.info(" Simulated expected values: Root val1: {}, val2: {}, Other val: {}",
//          simRootContract.getVal1(), simRootContract.getVal2(), simOtherContract.getVal());
//    }
//    else {
//      LOG.info(" Expect unchanged initial values: Root val1: {}, val2: {}, Other val: {}",
//          rootBcVal1InitialValue, rootBcVal2InitialValue, otherBcValInitialValue);
//    }
//    rootBlockchain.showValues();
//    bc2BusLogicBlockchain.showValues();
//    LOG.info(" Other contract's storage is locked: {}", bc2BusLogicBlockchain.storageIsLocked());
//
//    rootBlockchain.shutdown();
//    bc2BusLogicBlockchain.shutdown();
//
//    StatsHolder.log("End");
//    StatsHolder.print();
  }
}
