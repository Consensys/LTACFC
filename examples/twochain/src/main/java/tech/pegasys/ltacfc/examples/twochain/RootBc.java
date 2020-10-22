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
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.cbc.CrossEventProof;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.RootBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.MerklePatriciaTrie;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.Proof;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RootBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(RootBc.class);

  static final String ROOT_BLOCKCHAIN_ID = "1F";
  static final String ROOT_URI = "http://127.0.0.1:8310/";
  // Have the polling interval equal to the block time.
  private static final String POLLING_INTERVAL = "2000";


  RootBlockchainContract rootBlockchainContract;
  LockableStorage lockableStorageContract;

  public RootBc() throws IOException {
    super(ROOT_BLOCKCHAIN_ID, ROOT_URI, DynamicGasProvider.Strategy.FREE.toString(), POLLING_INTERVAL);
  }

  public RootBc(String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(bcId, uri, gasPriceStrategy, blockPeriod);
  }


  public void deployContracts(BigInteger otherBlockchainId, String otherContractAddress) throws Exception {
    LOG.info("Deploy Root Blockchain Contracts");
    deployContracts();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.gasProvider,
        this.crossBlockchainControlContract.getContractAddress()).send();
    this.rootBlockchainContract =
        RootBlockchainContract.deploy(this.web3j, this.tm, this.gasProvider,
            this.crossBlockchainControlContract.getContractAddress(),
            otherBlockchainId,
            otherContractAddress,
            this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.rootBlockchainContract.getContractAddress()).send();
    LOG.info(" Root Blockchain Contract: {}", this.rootBlockchainContract.getContractAddress());
    LOG.info(" Lockable Storage Contract: {}", this.lockableStorageContract.getContractAddress());
  }

  public String getRlpFunctionSignature_SomeComplexBusinessLogic(BigInteger val) {
    return this.rootBlockchainContract.getRLP_someComplexBusinessLogic(val);
  }

  public TransactionReceipt start(BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    LOG.info("TxId: {}", transactionId.toString(16));
    LOG.info("Timeout: {}", timeout);
    BigInteger cG = new BigInteger(1, callGraph);
    LOG.info("Call Graph: {}", cG.toString(16));
    return this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
  }


  public TransactionReceipt root(CrossEventProof startProof, List<CrossEventProof> segProofs) throws Exception {
    segProofs.add(0, startProof);

    for (CrossEventProof proofInfo: segProofs) {
      TransactionReceipt txR = this.crossBlockchainControlContract.rootPrep(
          proofInfo.getBlockchainId(),
          proofInfo.getCrossBlockchainControlContract(),
          proofInfo.getTransactionReceiptRoot(),
          proofInfo.getTransactionReceipt(),
          proofInfo.getProofOffsets(),
          proofInfo.getProofs()).send();
      if (!txR.isStatusOK()) {
        throw new Exception("Root transaction failed");
      }
      LOG.info("Root 2 Events");
      List<CrossBlockchainControl.Root2EventResponse> root2EventResponses = this.crossBlockchainControlContract.getRoot2Events(txR);
      for (CrossBlockchainControl.Root2EventResponse root2EventResponse : root2EventResponses) {
        LOG.info("  Event:");
        LOG.info("   _bcId: {}", root2EventResponse._bcId.toString(16));
        LOG.info("   _cbc Contract: {}", root2EventResponse._cbcContract);
        LOG.info("   _receipt Root: {}", new BigInteger(1, root2EventResponse._receiptRoot).toString(16));
        LOG.info("   _encoded tx receipt: {}", new BigInteger(1, root2EventResponse._encodedTxReceipt).toString(16));
      }
    }

    TransactionReceipt txR = this.crossBlockchainControlContract.root().send();
//    TransactionReceipt txR = this.crossBlockchainControlContract.root(
//        segmentBlockchainIds, segmentBlockchainCBCs,
//        segmentTxReceiptRoots, segmentTxReceipts,
//        //segmentProofOffsets,
//        segmentProofs
//    ).send();
    if (!txR.isStatusOK()) {
      throw new Exception("Root transaction failed");
    }

    List<CrossBlockchainControl.RootEventResponse> rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
    CrossBlockchainControl.RootEventResponse rootEventResponse = rootEventResponses.get(0);
    LOG.info("Root Event:");
    LOG.info(" _crossBlockchainTransactionId: {}", rootEventResponse._crossBlockchainTransactionId.toString(16));
    LOG.info(" _success: {}", rootEventResponse._success);


    LOG.info("Dump Events");
    List<CrossBlockchainControl.DumpEventResponse> dumpEventResponses = this.crossBlockchainControlContract.getDumpEvents(txR);
    for (CrossBlockchainControl.DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.info("  Event:");
      LOG.info("   1: {}", dumpEventResponse._val1.toString(16));
      LOG.info("   2: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.info("   3: {}", dumpEventResponse._val3);
      LOG.info("   4: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }

    LOG.info("Call Events");
    List<CrossBlockchainControl.CallEventResponse> callEventResponses = this.crossBlockchainControlContract.getCallEvents(txR);
    for (CrossBlockchainControl.CallEventResponse callEventResponse : callEventResponses) {
      LOG.info("  Event:");
      LOG.info("   Expected Blockchain Id: {}", callEventResponse._expectedBlockchainId.toString(16));
      LOG.info("   Actual Blockchain Id: {}", callEventResponse._actualBlockchainId.toString(16));
      LOG.info("   Expected Contract: {}", callEventResponse._expectedContract);
      LOG.info("   Actual Contract: {}", callEventResponse._actualContract);
      LOG.info("   Expected Function Call: {}", new BigInteger(1, callEventResponse._expectedFunctionCall).toString(16));
      LOG.info("   Actual Function Call: {}", new BigInteger(1, callEventResponse._actualFunctionCall).toString(16));
      LOG.info("   Return Value: {}", new BigInteger(1, callEventResponse._retVal).toString(16));
    }

    BigInteger val = this.rootBlockchainContract.getLocalVal().send();
    BigInteger other = this.rootBlockchainContract.getLocalValOther().send();
    LOG.info("val: 0x{}", val.toString(16));
    LOG.info("other: 0x{}", other.toString(16));




//    List<List<BigInteger>> bi1 = new ArrayList<>();
//    List<BigInteger> bi2 = new ArrayList<>();
//    bi2.add(BigInteger.valueOf(23));
//    bi1.add(bi2);
//
//    LOG.info("root1 function sig: {}", buildRoot1FunctionSignature(bi1));
//    txR = this.crossBlockchainControlContract.root1(bi1).send();
////    TransactionReceipt txR = this.crossBlockchainControlContract.root(
////        segmentBlockchainIds, segmentBlockchainCBCs,
////        segmentTxReceiptRoots, segmentTxReceipts,
////        //segmentProofOffsets,
////        segmentProofs
////    ).send();
//    if (!txR.isStatusOK()) {
//      throw new Exception("Root transaction failed");
//    }
//
//    rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
//    rootEventResponse = rootEventResponses.get(0);
//    LOG.info("Root Event2:");
//    LOG.info(" _crossBlockchainTransactionId: {}", rootEventResponse._crossBlockchainTransactionId.toString(16));
//    LOG.info(" _success: {}", rootEventResponse._success);

    return txR;
  }


//  static String buildRoot1FunctionSignature(List<List<BigInteger>> _i) {
//    final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
//        FUNC_ROOT1,
//        Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicArray>(
//            org.web3j.abi.datatypes.DynamicArray.class,
//            org.web3j.abi.Utils.typeMap(_i, org.web3j.abi.datatypes.DynamicArray.class,
//                org.web3j.abi.datatypes.generated.Uint256.class))),
//        Collections.<TypeReference<?>>emptyList());
//    final List<Type> parameters = function.getInputParameters();
//    return buildMethodSignature(function.getName(), parameters);
//  }


//  static String buildRootFunctionSignature(CrossBlockchainControl.Info _start, CrossBlockchainControl.Info _seg0, CrossBlockchainControl.Info _seg1) {
//      final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
//          FUNC_ROOT,
//          Arrays.<Type>asList(_start,
//              _seg0,
//              _seg1),
//          Collections.<TypeReference<?>>emptyList());
//
//    final List<Type> parameters = function.getInputParameters();
//    return buildMethodSignature(function.getName(), parameters);
//  }
//
//  static String buildMethodSignature(
//      final String methodName, final List<Type> parameters) {
//
//    final StringBuilder result = new StringBuilder();
//    result.append(methodName);
//    result.append("(");
////    final String params =
////        parameters.stream().map(Type::getTypeAsString).collect(Collectors.joining(","));
////    result.append(params);
//
//    for (int i=0; i<parameters.size(); i++) {
//      if (i!=0) {
//        result.append(",");
//      }
//      result.append(parameters.get(i).getTypeAsString());
//    }
//    result.append(")");
//    return result.toString();
//  }


  public void OLD_getProofForTxReceipt(TransactionReceipt aReceipt) throws Exception {
    // Calculate receipt root based on logs for all receipts of all transactions in the block.
    String blockHash = aReceipt.getBlockHash();
    EthGetBlockTransactionCountByHash transactionCountByHash = this.web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    List<org.hyperledger.besu.ethereum.core.TransactionReceipt> besuReceipts = new ArrayList<>();

    BigInteger transactionIndex = BigInteger.ZERO;
    do {
      EthTransaction ethTransaction = this.web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
      Optional<Transaction> transaction = ethTransaction.getTransaction();
      assert(transaction.isPresent());
      String txHash = transaction.get().getHash();
      EthGetTransactionReceipt ethGetTransactionReceipt = this.web3j.ethGetTransactionReceipt(txHash).send();
      Optional<TransactionReceipt> mayBeReceipt = ethGetTransactionReceipt.getTransactionReceipt();
      assert(mayBeReceipt.isPresent());
      TransactionReceipt receipt = mayBeReceipt.get();

      // Convert to Besu objects
      List<org.hyperledger.besu.ethereum.core.Log> besuLogs = new ArrayList<>();

      String stateRootFromReceipt = receipt.getRoot();
      Hash root = (stateRootFromReceipt == null) ? null : Hash.fromHexString(receipt.getRoot());
      String statusFromReceipt = receipt.getStatus();
      int status = statusFromReceipt == null ? -1 : Integer.parseInt(statusFromReceipt.substring(2), 16);
      for (Log web3jLog: receipt.getLogs()) {
        org.hyperledger.besu.ethereum.core.Address addr = org.hyperledger.besu.ethereum.core.Address.fromHexString(web3jLog.getAddress());
        Bytes data = Bytes.fromHexString(web3jLog.getData());
        List<String> topics = web3jLog.getTopics();
        List<LogTopic> logTopics = new ArrayList<>();
        for (String topic: topics) {
          LogTopic logTopic = LogTopic.create(Bytes.fromHexString(topic));
          logTopics.add(logTopic);
        }
        besuLogs.add(new org.hyperledger.besu.ethereum.core.Log(addr, data, logTopics));
      }
      String revertReasonFromReceipt = receipt.getRevertReason();
      Bytes revertReason = revertReasonFromReceipt == null ? null : Bytes.fromHexString(receipt.getRevertReason());
      org.hyperledger.besu.ethereum.core.TransactionReceipt txReceipt =
          root == null ?
              new org.hyperledger.besu.ethereum.core.TransactionReceipt(status, receipt.getCumulativeGasUsed().longValue(),
                  besuLogs, java.util.Optional.ofNullable(revertReason))
              :
              new org.hyperledger.besu.ethereum.core.TransactionReceipt(root, receipt.getCumulativeGasUsed().longValue(),
                  besuLogs, java.util.Optional.ofNullable(revertReason));
      besuReceipts.add(txReceipt);

      // Increment for the next time through the loop.
      transactionIndex = transactionIndex.add(BigInteger.ONE);
    } while (transactionIndex.compareTo(txCount) != 0);

    final MerklePatriciaTrie<Bytes, Bytes> trie = trie();
    for (int i = 0; i < besuReceipts.size(); ++i) {
      Bytes rlpEncoding = RLP.encode(besuReceipts.get(i)::writeTo);
      trie.put(indexKey(i), rlpEncoding);
    }
    Bytes32 besuCalculatedReceiptsRoot = trie.getRootHash();
    String besuCalculatedReceiptsRootStr = besuCalculatedReceiptsRoot.toHexString();

    // TODO remove this check code that isn't needed
    EthBlock block = this.web3j.ethGetBlockByHash(aReceipt.getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    if (!besuCalculatedReceiptsRootStr.equalsIgnoreCase( receiptsRoot)) {
      throw new Error("Calculated transaction receipt root does not match actual receipt root");
    }


    BigInteger txIndex = aReceipt.getTransactionIndex();
    Bytes aKey = indexKey((int)txIndex.longValue());

    Proof<Bytes> simpleProof = trie.getValueWithSimpleProof(aKey);
    Bytes transactionReceipt = simpleProof.getValue().get();
    Bytes rlpOfNode = transactionReceipt;
    // Node references can be hashes or the node itself, if the node is less than 32 bytes.
    // Leaf nodes in Ethereum, leaves of Merkle Patricia Tries could be less than 32 bytes,
    // but no other nodes. For transaction receipts, it isn't possible even the leaf nodes
    // to be 32 bytes.
    Bytes32 nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(transactionReceipt);

    List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
    List<BigInteger> proofOffsets = new ArrayList<>();
    List<byte[]> proofs = new ArrayList<>();
    for (int j = proofList1.size()-1; j >=0; j--) {
      rlpOfNode = proofList1.get(j);
      proofOffsets.add(BigInteger.valueOf(findOffset(rlpOfNode, nodeHash)));
      proofs.add(rlpOfNode.toArray());
      nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);
    }
//    assertEquals(besuCalculatedReceiptsRoot.toHexString(), org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode).toHexString());

    try {
      this.txReceiptsRootStorageContract.verify(
          this.blockchainId,
          besuCalculatedReceiptsRoot.toArray(),
          transactionReceipt.toArray(),
          proofOffsets,
          proofs
      ).send();
      throw new Exception("Unexpectedly, no error while verifying");
    } catch (TransactionException ex) {
    }
  }


}
