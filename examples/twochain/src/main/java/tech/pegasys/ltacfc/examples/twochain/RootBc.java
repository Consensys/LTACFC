package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.RootBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.soliditywrappers.TxReceiptsRootStorage;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.MerklePatriciaTrie;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.Proof;
import tech.pegasys.poc.witnesscodeanalysis.trie.ethereum.trie.SimpleMerklePatriciaTrie;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

import static tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl.FUNC_ROOT;
import static tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl.FUNC_ROOT1;

public class RootBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(RootBc.class);

  static final BigInteger ROOT_BLOCKCHAIN_ID = BigInteger.valueOf(31);
  static final String ROOT_IP_PORT = "127.0.0.1:8310";

  RootBlockchainContract rootBlockchainContract;
  LockableStorage lockableStorageContract;

  public RootBc() {
    super(ROOT_BLOCKCHAIN_ID, ROOT_IP_PORT);
  }


  public void deployContracts(BigInteger otherBlockchainId, String otherContractAddress) throws Exception {
    LOG.info("Deploy Root Blockchain Contracts");
    deployContracts();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.crossBlockchainControlContract.getContractAddress()).send();
    this.rootBlockchainContract =
        RootBlockchainContract.deploy(this.web3j, this.tm, this.freeGasProvider,
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


  public void root(
      byte[] startTxReceiptRoot, byte[] startTxReceipt, List<BigInteger> startProofOffsets, List<byte[]> startProof,
      List<BigInteger> segmentBlockchainIds, List<String> segmentBlockchainCBCs,
      List<byte[]> segmentTxReceiptRoots, List<byte[]> segmentTxReceipts, List<List<BigInteger>> segmentProofOffsets, List<List<byte[]>> segmentProofs) throws Exception {

      segmentBlockchainIds.add(0, this.blockchainId);
      segmentBlockchainCBCs.add(0, this.crossBlockchainControlContract.getContractAddress());
      segmentTxReceiptRoots.add(0, startTxReceiptRoot);
      segmentTxReceipts.add(0, startTxReceipt);
      segmentProofOffsets.add(0, startProofOffsets);
      segmentProofs.add(0, startProof);

//    RlpDumper.dump(RLP.input(Bytes.wrap(startTxReceipt)));

    CrossBlockchainControl.Info rootInfo = new CrossBlockchainControl.Info(segmentBlockchainIds.get(0), segmentBlockchainCBCs.get(0),
        segmentTxReceiptRoots.get(0), segmentTxReceipts.get(0), segmentProofOffsets.get(0), segmentProofs.get(0));
    CrossBlockchainControl.Info seg1Info = new CrossBlockchainControl.Info(segmentBlockchainIds.get(1), segmentBlockchainCBCs.get(1),
        segmentTxReceiptRoots.get(1), segmentTxReceipts.get(1), segmentProofOffsets.get(1), segmentProofs.get(1));
    CrossBlockchainControl.Info seg2Info = new CrossBlockchainControl.Info(segmentBlockchainIds.get(2), segmentBlockchainCBCs.get(2),
        segmentTxReceiptRoots.get(2), segmentTxReceipts.get(2), segmentProofOffsets.get(2), segmentProofs.get(2));


    for (int i = 0; i <= 2; i++) {

      TransactionReceipt txR = this.crossBlockchainControlContract.rootPrep(
          segmentBlockchainIds.get(i), segmentBlockchainCBCs.get(i),
          segmentTxReceiptRoots.get(i), segmentTxReceipts.get(i), segmentProofOffsets.get(i), segmentProofs.get(i)).send();
//    TransactionReceipt txR = this.crossBlockchainControlContract.root(
//        segmentBlockchainIds, segmentBlockchainCBCs,
//        segmentTxReceiptRoots, segmentTxReceipts,
//        //segmentProofOffsets,
//        segmentProofs
//    ).send();
      if (!txR.isStatusOK()) {
        throw new Exception("Root transaction failed");
      }


//    String rlpRoot = this.crossBlockchainControlContract.getRLP_root(rootInfo, seg1Info, seg2Info);
//    LOG.info("RLP Root Transaction: {}", rlpRoot);
//
////    LOG.info("root function sig: {}", buildRootFunctionSignature(rootInfo, seg1Info, seg2Info));
//
//    TransactionReceipt txR = this.crossBlockchainControlContract.root(rootInfo, seg1Info, seg2Info).send();
////    TransactionReceipt txR = this.crossBlockchainControlContract.root(
////        segmentBlockchainIds, segmentBlockchainCBCs,
////        segmentTxReceiptRoots, segmentTxReceipts,
////        //segmentProofOffsets,
////        segmentProofs
////    ).send();
//    if (!txR.isStatusOK()) {
//      throw new Exception("Root transaction failed");
//    }

//    List<CrossBlockchainControl.RootEventResponse> rootEventResponses = this.crossBlockchainControlContract.getRootEvents(txR);
//    CrossBlockchainControl.RootEventResponse rootEventResponse = rootEventResponses.get(0);
//    LOG.info("Root Event:");
//    LOG.info(" _crossBlockchainTransactionId: {}", rootEventResponse._crossBlockchainTransactionId.toString(16));
//    LOG.info(" _success: {}", rootEventResponse._success);

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
