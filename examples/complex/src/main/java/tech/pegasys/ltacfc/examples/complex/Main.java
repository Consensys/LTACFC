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
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.cbc.CbcManager;
import tech.pegasys.ltacfc.cbc.engine.AbstractCbcExecutor;
import tech.pegasys.ltacfc.cbc.engine.CbcExecutorTxReceiptRootTransfer;
import tech.pegasys.ltacfc.cbc.engine.ExecutionEngine;
import tech.pegasys.ltacfc.cbc.engine.SerialExecutionEngine;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.CredentialsCreator;
import tech.pegasys.ltacfc.common.CrossBlockchainConsensus;
import tech.pegasys.ltacfc.common.PropertiesLoader;
import tech.pegasys.ltacfc.common.StatsHolder;
import tech.pegasys.ltacfc.examples.complex.sim.SimBalancesContract;
import tech.pegasys.ltacfc.examples.complex.sim.SimBusLogicContract;
import tech.pegasys.ltacfc.examples.complex.sim.SimPriceOracleContract;
import tech.pegasys.ltacfc.examples.complex.sim.SimTradeWallet;
import tech.pegasys.ltacfc.examples.complex.sim.SimStockContract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static tech.pegasys.ltacfc.cbc.CallGraphHelper.*;

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

    Bc1TradeWallet bc1TradeWalletBlockchain = new Bc1TradeWallet(creds, root.bcId, root.uri, root.gasPriceStrategy, root.period);
    Bc2BusLogic bc2BusLogicBlockchain = new Bc2BusLogic(creds, bc2.bcId, bc2.uri, bc2.gasPriceStrategy, bc2.period);
    Bc3Balances bc3BalancesBlockchain = new Bc3Balances(creds, bc3.bcId, bc3.uri, bc3.gasPriceStrategy, bc3.period);
    Bc4Oracle bc4OracleBlockchain = new Bc4Oracle(creds, bc4.bcId, bc4.uri, bc4.gasPriceStrategy, bc4.period);
    Bc5Stock bc5StockBlockchain = new Bc5Stock(creds, bc5.bcId, bc5.uri, bc5.gasPriceStrategy, bc5.period);

    CbcManager cbcManager = new CbcManager(consensusMethodology);
    cbcManager.addBlockchainAndDeployContracts(creds, root);
    cbcManager.addBlockchainAndDeployContracts(creds, bc2);
    cbcManager.addBlockchainAndDeployContracts(creds, bc3);
    cbcManager.addBlockchainAndDeployContracts(creds, bc4);
    cbcManager.addBlockchainAndDeployContracts(creds, bc5);


    // Set-up client side and deploy contracts on the blockchains.
    BigInteger bc3BcId = bc3BalancesBlockchain.getBlockchainId();
    bc3BalancesBlockchain.deployContracts(cbcManager.getCbcAddress(bc3BcId));
    String balancesContractAddress = bc3BalancesBlockchain.balancesContract.getContractAddress();

    BigInteger bc4BcId = bc4OracleBlockchain.getBlockchainId();
    bc4OracleBlockchain.deployContracts(cbcManager.getCbcAddress(bc4BcId));
    String priceOracleContractAddress = bc4OracleBlockchain.priceOracleContract.getContractAddress();

    BigInteger bc5BcId = bc5StockBlockchain.getBlockchainId();
    bc5StockBlockchain.deployContracts(cbcManager.getCbcAddress(bc5BcId));
    String stockContractAddress = bc5StockBlockchain.stockContract.getContractAddress();

    BigInteger bc2BcId = bc2BusLogicBlockchain.getBlockchainId();
    bc2BusLogicBlockchain.deployContracts(cbcManager.getCbcAddress(bc2BcId),
        bc3BcId, balancesContractAddress, bc4BcId, priceOracleContractAddress, bc5BcId, stockContractAddress);
    String businessLogicContractAddress = bc2BusLogicBlockchain.busLogicContract.getContractAddress();

    BigInteger rootBcId = bc1TradeWalletBlockchain.getBlockchainId();
    bc1TradeWalletBlockchain.deployContracts(cbcManager.getCbcAddress(rootBcId), bc2BcId, businessLogicContractAddress);
    String tradeWalletContractAddress = bc1TradeWalletBlockchain.tradeWalletContract.getContractAddress();

    // To make the example simple, just have one signer, and have the same signer for all blockchains.
    // Note that signers only need to be registered against blockchains that they will consume
    // events from.
    AnIdentity signer = new AnIdentity();
    cbcManager.registerSignerOnAllBlockchains(signer);

    // Create simulators
    SimStockContract simStockContract = new SimStockContract(bc5StockBlockchain);
    SimBalancesContract simBalancesContract = new SimBalancesContract(bc3BalancesBlockchain);
    SimPriceOracleContract simPriceOracleContract = new SimPriceOracleContract(bc4OracleBlockchain);
    SimBusLogicContract simBusLogicContract = new SimBusLogicContract(bc2BusLogicBlockchain, simPriceOracleContract, simBalancesContract, simStockContract);
    SimTradeWallet simTradeWallet = new SimTradeWallet(bc1TradeWalletBlockchain, simBusLogicContract);

    // Do some single blockchain calls to set things up, to show that values have changed.
    // Ensure the simulator is set-up the same way.
    BigInteger buyerInitialBalance = BigInteger.valueOf(10000000);
    BigInteger sellerInitialBalance = BigInteger.valueOf(10);
    String buyerAddress = creds.getAddress();
    String sellerAddress = CredentialsCreator.createCredentials().getAddress();
    BigInteger buyerInitialStock = BigInteger.valueOf(1);
    BigInteger sellerInitialStock = BigInteger.valueOf(100);
    BigInteger price = BigInteger.valueOf(77);

    simPriceOracleContract.setPrice(price);
    bc4OracleBlockchain.setPrice(price);

    simStockContract.setStock(buyerAddress, buyerInitialStock);
    bc5StockBlockchain.setStock(buyerAddress, buyerInitialStock);
    simStockContract.setStock(sellerAddress, sellerInitialStock);
    bc5StockBlockchain.setStock(sellerAddress, sellerInitialStock);

    simBalancesContract.setBalance(buyerAddress, buyerInitialBalance);
    bc3BalancesBlockchain.setBalance(buyerAddress, buyerInitialBalance);
    simBalancesContract.setBalance(sellerAddress, sellerInitialBalance);
    bc3BalancesBlockchain.setBalance(sellerAddress, sellerInitialBalance);


    // Simulate passing in the parameter value 7 for the cross-blockchain call.
    BigInteger quantity = BigInteger.valueOf(7);
    simTradeWallet.executeTrade(buyerAddress, sellerAddress, quantity);

    String rlpRootExecuteTrade = simTradeWallet.getRlpFunctionSignature_executeTrade();
    String rlpBusLogicStockShipment = simBusLogicContract.getRlpFunctionSignature_stockShipment();
    String rlpPriceOracleGetPrice = simPriceOracleContract.getRlpFunctionSignature_getPrice();
    String rlpBalancesTransfer = simBalancesContract.getRlpFunctionSignature_transfer();
    String rlpStockTransfer = simStockContract.getRlpFunctionSignature_transfer();

    RlpList getPrice = createLeafFunctionCall(bc4BcId, priceOracleContractAddress, rlpPriceOracleGetPrice);
    RlpList balanceTransfer = createLeafFunctionCall(bc3BcId, balancesContractAddress, rlpBalancesTransfer);
    RlpList stockTransfer = createLeafFunctionCall(bc5BcId, stockContractAddress, rlpStockTransfer);
    List<RlpType> busLogicCalls = new ArrayList<>();
    busLogicCalls.add(getPrice);
    busLogicCalls.add(balanceTransfer);
    busLogicCalls.add(stockTransfer);
    RlpList businessLogic = createIntermediateFunctionCall(bc2BcId, businessLogicContractAddress, rlpBusLogicStockShipment, busLogicCalls);
    List<RlpType> rootCalls = new ArrayList<>();
    rootCalls.add(businessLogic);
    RlpList callGraph = createRootFunctionCall(rootBcId, tradeWalletContractAddress, rlpRootExecuteTrade, rootCalls);

    AbstractCbcExecutor executor;
    switch (consensusMethodology) {
      case TRANSACTION_RECEIPT_SIGNING:
        executor = new CbcExecutorTxReceiptRootTransfer(cbcManager);
        break;
      default:
        throw new RuntimeException("Not implemented yet");
    }

    ExecutionEngine executionEngine = new SerialExecutionEngine(executor);
    executionEngine.execute(callGraph, 300);
    boolean success = false;

//
//
//
//        BigInteger crossBlockchainTransactionId1 = AbstractCbc.generateRandomCrossBlockchainTransactionId();
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
//        if (simTradeWallet.someComplexBusinessLogicIfTrue) {
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
//        if (simTradeWallet.someComplexBusinessLogicIfTrue) {
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
//    LOG.info("Cross-Blockchain Transaction was successful: {}", success);
//    if (success) {
//      LOG.info(" Simulated expected values: Root val1: {}, val2: {}, Other val: {}",
//          simTradeWallet.getVal1(), simTradeWallet.getVal2(), simBusLogicContract.getVal());
//    }
//    else {
//      LOG.info(" Expect unchanged initial values: Root val1: {}, val2: {}, Other val: {}",
//          rootBcVal1InitialValue, rootBcVal2InitialValue, otherBcValInitialValue);
//    }
//    bc1TradeWalletBlockchain.showValues();
//    bc2BusLogicBlockchain.showValues();
//    LOG.info(" Other contract's storage is locked: {}", bc2BusLogicBlockchain.storageIsLocked());

    bc1TradeWalletBlockchain.shutdown();
    bc2BusLogicBlockchain.shutdown();
    bc3BalancesBlockchain.shutdown();
    bc4OracleBlockchain.shutdown();
    bc5StockBlockchain.shutdown();

    StatsHolder.log("End");
    StatsHolder.print();
  }
}
