package tech.pegasys.ltacfc;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.ethereum.core.Hash;
import org.hyperledger.besu.ethereum.core.LogTopic;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.events.ReceiptsProcessing;
import tech.pegasys.ltacfc.soliditywrappers.Test;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Main {

  private static final Logger LOG = LogManager.getLogger(Main.class);

  private static final BigInteger BLOCKCHAIN_ID = BigInteger.valueOf(31);
  private static final String IP_PORT = "127.0.0.1:8310";
  private static final String URI = "http://" + IP_PORT + "/";


  // Have the polling interval equal to the block time.
  private static final int POLLING_INTERVAL = 2000;
  // Retry requests to Ethereum Clients up to five times.
  private static final int RETRY = 5;

  private Web3j web3j;
  private TransactionManager tm0;
  private TransactionManager tm1;
  private Credentials credentials0;
  private Credentials credentials1;
  // A gas provider which indicates no gas is charged for transactions.
  private ContractGasProvider freeGasProvider =  new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);

  String testContractAddress;
  Test testContract0;
  Test testContract1;

  public static void main(String[] args) throws Exception {
    (new Main()).doStuff();
  }

  public void doStuff() throws Exception {

    String privateKey0 = new KeyPairGen().generateKeyPairGetPrivateKey();
    this.credentials0 = Credentials.create(privateKey0);
    String privateKey1 = new KeyPairGen().generateKeyPairGetPrivateKey();
    this.credentials1 = Credentials.create(privateKey1);

    this.web3j = Web3j.build(new HttpService(URI), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    this.tm0 = new RawTransactionManager(this.web3j, this.credentials0, BLOCKCHAIN_ID.longValue(), RETRY, POLLING_INTERVAL);
    this.tm1 = new RawTransactionManager(this.web3j, this.credentials1, BLOCKCHAIN_ID.longValue(), RETRY, POLLING_INTERVAL);

    deployContracts();

    LOG.info("Check Start Event and Create Proof");
    BigInteger id = BigInteger.TWO;
    final TransactionReceipt[] receipts = new TransactionReceipt[2];

    CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture1 = this.testContract0.start(BigInteger.TEN).sendAsync();
    CompletableFuture<TransactionReceipt> transactionReceiptCompletableFuture2 = this.testContract1.start(BigInteger.valueOf(20)).sendAsync();
    CompletableFuture<Void> combinedFuture
        = CompletableFuture.allOf(transactionReceiptCompletableFuture1, transactionReceiptCompletableFuture2);
    combinedFuture.get();

    receipts[0] = transactionReceiptCompletableFuture1.get();
    receipts[1] = transactionReceiptCompletableFuture2.get();

    LOG.info(transactionReceiptCompletableFuture1.isDone());
    LOG.info(transactionReceiptCompletableFuture2.isDone());


    // Print out the receipts.
    for (int i=0; i<receipts.length; i++) {
      LOG.info(" Tx Receipt ({}): {}", i, receipts[i]);
      if (receipts[i] == null) {
        LOG.error("Unexpectedly receipt[{}] is null", i);
      }
      else {
        List<Log> logs = receipts[i].getLogs();
        for (Log log: logs) {
          LOG.info("  {}", log);
        }

        List<Test.Event1EventResponse> e1Events = this.testContract0.getEvent1Events(receipts[i]);
        LOG.info("  Event1(0): Id: {}", e1Events.get(0).id);
        LOG.info("  Event1(1): Id: {}", e1Events.get(1).id);
        List<Test.Event2EventResponse> e2Events = this.testContract0.getEvent2Events(receipts[i]);
        LOG.info("  Event2(0): Id: {}", e2Events.get(0).id);
        List<Test.Event3EventResponse> e3Events = this.testContract0.getEvent3Events(receipts[i]);
        LOG.info("  Event3(0): Id: {}", e3Events.get(0).id);
      }
    }

    // Show the receipts root that has been included in the block.
    if (receipts[0] == null || receipts[0].getBlockHash() == null) {
      LOG.error("unexpectedly, receipts == null or block hash in receipt is null");
      return;
    }
    EthBlock block = web3j.ethGetBlockByHash(receipts[0].getBlockHash(), false).send();
    EthBlock.Block b1 = block.getBlock();
    String receiptsRoot = b1.getReceiptsRoot();
    LOG.info("Receipts Root from block: {}", receiptsRoot);


    // Calculate receipt root based on logs for all receipts of all transactions in the block.
    String txReceiptRootFromReceipt = receipts[0].getRoot();
    String blockHash = receipts[0].getBlockHash();
    EthGetBlockTransactionCountByHash transactionCountByHash = web3j.ethGetBlockTransactionCountByHash(blockHash).send();
    BigInteger txCount = transactionCountByHash.getTransactionCount();

    List<org.hyperledger.besu.ethereum.core.TransactionReceipt> besuReceipts = new ArrayList<>();

    BigInteger transactionIndex = BigInteger.ZERO;
    do {
      EthTransaction ethTransaction = this.web3j.ethGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
      Optional<Transaction> transaction = ethTransaction.getTransaction();
      if (!transaction.isPresent()) {
        LOG.error("Transaction at index {} is null!", transactionIndex);
        return;
      }
      String txHash = transaction.get().getHash();
      EthGetTransactionReceipt ethGetTransactionReceipt = this.web3j.ethGetTransactionReceipt(txHash).send();
      Optional<TransactionReceipt> mayBeReceipt = ethGetTransactionReceipt.getTransactionReceipt();
      if (!mayBeReceipt.isPresent()) {
        LOG.error("Transaction receipt at index {} is null!", transactionIndex);
        return;
      }
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

    Hash receiptsRoot1 = ReceiptsProcessing.receiptsRoot(besuReceipts);
    LOG.info("Calculated Receipts Root: {}", receiptsRoot1);

    ReceiptsProcessing.logTrie(besuReceipts);

    System.exit(0);
  }


  public void deployContracts() throws Exception {
    LOG.info("Deploying contracts");
    this.testContract0 =
        Test.deploy(this.web3j, this.tm0, this.freeGasProvider).send();
    this.testContractAddress = this.testContract0.getContractAddress();
    LOG.info(" Test contract deployed on at address: {}", this.testContractAddress);

    this.testContract1 = Test.load(this.testContractAddress, this.web3j, this.tm1, this.freeGasProvider);
  }
}
