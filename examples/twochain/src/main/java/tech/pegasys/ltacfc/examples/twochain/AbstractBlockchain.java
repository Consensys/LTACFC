package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.hyperledger.besu.ethereum.rlp.RLPInput;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
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
import org.web3j.rlp.RlpEncoder;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.cbc.CrossEventProof;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.common.Tuple;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class AbstractBlockchain {
  private static final Logger LOG = LogManager.getLogger(AbstractBlockchain.class);

  // Have the polling interval equal to the block time.
  protected static final int POLLING_INTERVAL = 2000;
  // Retry requests to Ethereum Clients up to five times.
  protected static final int RETRY = 5;

  protected Credentials credentials;
  // A gas provider which indicates no gas is charged for transactions.
  protected static ContractGasProvider freeGasProvider =  new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);


  protected BigInteger blockchainId;
  protected String ipPort;
  protected String uri;

  protected AbstractBlockchain(BigInteger bcId, String ipPort) {
    this.blockchainId = bcId;
    this.ipPort = ipPort;
    this.uri = "http://" + ipPort + "/";

  }

  Registrar registrarContract;
  TxReceiptsRootStorage txReceiptsRootStorageContract;
  CrossBlockchainControl crossBlockchainControlContract;
  Web3j web3j;
  TransactionManager tm;


  public void setupWeb3(Credentials creds) throws Exception {
    this.credentials = creds;
    this.web3j = Web3j.build(new HttpService(this.uri), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    this.tm = new RawTransactionManager(this.web3j, this.credentials, this.blockchainId.longValue(), RETRY, POLLING_INTERVAL);
  }

  protected void deployContracts() throws Exception {
    this.registrarContract = Registrar.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.txReceiptsRootStorageContract =
        TxReceiptsRootStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
            this.registrarContract.getContractAddress()).send();
    this.crossBlockchainControlContract =
        CrossBlockchainControl.deploy(this.web3j, this.tm, this.freeGasProvider,
            this.blockchainId, this.txReceiptsRootStorageContract.getContractAddress()).send();
    LOG.info(" Registrar Contract: {}", this.registrarContract.getContractAddress());
    LOG.info(" TxReceiptRoot Contract: {}", this.txReceiptsRootStorageContract.getContractAddress());
    LOG.info(" Cross Blockchain Contract Contract: {}", this.crossBlockchainControlContract.getContractAddress());
  }

  public void registerSigner(AnIdentity signer) throws Exception {
    TransactionReceipt receipt1 = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), this.blockchainId, signer.getAddressAsBigInt()).send();
    if (!receipt1.isStatusOK()) {
      throw new Exception("Transaction to register signer failed");
    }
  }


  public byte[] getTransactionReceiptRoot(TransactionReceipt transactionReceipt) throws Exception {
    EthBlock block = this.web3j.ethGetBlockByHash(transactionReceipt.getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    Bytes32 receiptsRootBytes32 = Bytes32.fromHexString(receiptsRoot);
    return receiptsRootBytes32.toArray();
  }


  public void addTransactionReceiptRootToBlockchain(
      AnIdentity[] signers, BigInteger sourceBlockchainId, byte[] transactionReceiptRoot) throws Exception {
    // Add the transaction receipt root for the blockchain
    // Sign the txReceiptRoot
    List<String> theSigners = new ArrayList<>();
    List<byte[]> sigR = new ArrayList<>();
    List<byte[]> sigS = new ArrayList<>();
    List<BigInteger> sigV = new ArrayList<>();
    for (AnIdentity signer: signers) {
      Sign.SignatureData signatureData = signer.sign(transactionReceiptRoot);
      theSigners.add(signer.getAddress());
      sigR.add(signatureData.getR());
      sigS.add(signatureData.getS());
      sigV.add(BigInteger.valueOf(signatureData.getV()[0]));
    }

    // This will revert if the signature does not verify
    TransactionReceipt receipt5 = this.txReceiptsRootStorageContract.addTxReceiptRoot(sourceBlockchainId, theSigners, sigR, sigS, sigV, transactionReceiptRoot).send();
    if (!receipt5.isStatusOK()) {
      throw new Exception("Transaction to add transaction receipt root failed");
    }
  }


  public CrossEventProof getProofForTxReceipt(BigInteger blockchainId, String cbcContractAddress, TransactionReceipt aReceipt) throws Exception {
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
    Bytes encodedTransactionReceipt = simpleProof.getValue().get();
    Bytes rlpOfNode = encodedTransactionReceipt;
    // Node references can be hashes or the node itself, if the node is less than 32 bytes.
    // Leaf nodes in Ethereum, leaves of Merkle Patricia Tries could be less than 32 bytes,
    // but no other nodes. For transaction receipts, it isn't possible even the leaf nodes
    // to be 32 bytes.
    Bytes32 nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);

    List<Bytes> proofList1 = simpleProof.getProofRelatedNodes();
    List<BigInteger> proofOffsets = new ArrayList<>();
    List<byte[]> proofs = new ArrayList<>();
    for (int j = proofList1.size()-1; j >=0; j--) {
      rlpOfNode = proofList1.get(j);
      proofOffsets.add(BigInteger.valueOf(findOffset(rlpOfNode, nodeHash)));
      proofs.add(rlpOfNode.toArray());
      nodeHash = org.hyperledger.besu.crypto.Hash.keccak256(rlpOfNode);
    }
    if (!besuCalculatedReceiptsRoot.toHexString().equalsIgnoreCase(nodeHash.toHexString())) {
      throw new Error("Transaction receipt root calculated using proof did not match actual receipt root");
    }
    return new CrossEventProof(
        blockchainId,
        cbcContractAddress,
        getTransactionReceiptRoot(aReceipt),
        encodedTransactionReceipt.toArray(),
        proofOffsets,
        proofs);
  }




  public TransactionReceipt signalling(CrossEventProof rootProof, List<CrossEventProof> segProofs) throws Exception {
    segProofs.add(0, rootProof);

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
      LOG.info("RootPrep Events");
      List<CrossBlockchainControl.Root2EventResponse> root2EventResponses = this.crossBlockchainControlContract.getRoot2Events(txR);
      for (CrossBlockchainControl.Root2EventResponse root2EventResponse : root2EventResponses) {
        LOG.info("  Event:");
        LOG.info("   _bcId: {}", root2EventResponse._bcId.toString(16));
        LOG.info("   _cbc Contract: {}", root2EventResponse._cbcContract);
        LOG.info("   _receipt Root: {}", new BigInteger(1, root2EventResponse._receiptRoot).toString(16));
        LOG.info("   _encoded tx receipt: {}", new BigInteger(1, root2EventResponse._encodedTxReceipt).toString(16));
      }
    }

    TransactionReceipt txR = this.crossBlockchainControlContract.signalling().send();
//    TransactionReceipt txR = this.crossBlockchainControlContract.root(
//        segmentBlockchainIds, segmentBlockchainCBCs,
//        segmentTxReceiptRoots, segmentTxReceipts,
//        //segmentProofOffsets,
//        segmentProofs
//    ).send();
    if (!txR.isStatusOK()) {
      throw new Exception("Signalling transaction failed");
    }

    List<CrossBlockchainControl.SignallingEventResponse> sigEventResponses = this.crossBlockchainControlContract.getSignallingEvents(txR);
    CrossBlockchainControl.SignallingEventResponse sigEventResponse = sigEventResponses.get(0);
    LOG.info("Signalling Event:");
    LOG.info(" _rootBlockchainId: {}", sigEventResponse._rootBcId.toString(16));
    LOG.info(" _crossBlockchainTransactionId: {}", sigEventResponse._crossBlockchainTransactionId.toString(16));


    LOG.info("Dump Events");
    List<CrossBlockchainControl.DumpEventResponse> dumpEventResponses = this.crossBlockchainControlContract.getDumpEvents(txR);
    for (CrossBlockchainControl.DumpEventResponse dumpEventResponse : dumpEventResponses) {
      LOG.info("  Event:");
      LOG.info("   1: {}", dumpEventResponse._val1.toString(16));
      LOG.info("   2: {}", new BigInteger(1, dumpEventResponse._val2).toString(16));
      LOG.info("   3: {}", dumpEventResponse._val3);
      LOG.info("   4: {}", new BigInteger(1, dumpEventResponse._val4).toString(16));
    }

    return txR;
  }





  protected static int findOffset(Bytes rlpOfNode, Bytes nodeRef) {
    int sizeNodeRef = nodeRef.size();
    for (int i = 0; i < rlpOfNode.size() - sizeNodeRef; i++) {
      boolean found = true;
      for (int j = 0; j < sizeNodeRef; j++) {
        if (rlpOfNode.get(i+j) != nodeRef.get(j)) {
          found = false;
          break;
        }
      }
      if (found) {
        return i;
      }
    }
    return -1;
  }


  protected static MerklePatriciaTrie<Bytes, Bytes> trie() {
    return new SimpleMerklePatriciaTrie<>(b -> b);
  }

  protected static Bytes indexKey(final int i) {
    return RLP.encodeOne(UInt256.valueOf(i).toBytes().trimLeadingZeros());
  }






}
