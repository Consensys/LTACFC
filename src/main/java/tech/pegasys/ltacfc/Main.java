package tech.pegasys.ltacfc;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.soliditywrappers.BlockHeaderStorage;
import tech.pegasys.ltacfc.utils.KeyPairGen;

import java.math.BigInteger;
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
  private TransactionManager tm;
  private Credentials credentials;
  // A gas provider which indicates no gas is charged for transactions.
  private ContractGasProvider freeGasProvider =  new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT);


  String blockHeaderStorageContractAddress;
  BlockHeaderStorage blockHeaderStorageContract;

  String crossBlockchainControlContractAddress;
  CrossBlockchainControl crossBlockchainControlContract;

  public static void main(String[] args) throws Exception {
    (new Main()).doStuff();
  }

  public void doStuff() throws Exception {

    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
//    System.out.println("Priv2: " + privateKey);
    this.credentials = Credentials.create(privateKey);

    this.web3j = Web3j.build(new HttpService(URI), POLLING_INTERVAL, new ScheduledThreadPoolExecutor(5));
    this.tm = new RawTransactionManager(this.web3j, this.credentials, BLOCKCHAIN_ID.longValue(), RETRY, POLLING_INTERVAL);

    deployContracts();

    LOG.info("Initial values");
    LOG.info(" Val1: {}", this.blockHeaderStorageContract.val1().send());
    TransactionReceipt receipt1 = this.blockHeaderStorageContract.run().send();
    LOG.info(" set(5) Receipt: {}", receipt1);
    LOG.info(" Val1: {}", this.blockHeaderStorageContract.val1().send());
  }


  private void loadContracts() {
    LOG.info("Loading contracts");
    LOG.info(" Block Header Storage contract: {}", this.blockHeaderStorageContract);
    this.blockHeaderStorageContract = BlockHeaderStorage.load(
        this.blockHeaderStorageContractAddress, this.web3j, this.tm, this.freeGasProvider);
    LOG.info(" Cross-Blockchain Control contract: {}", this.crossBlockchainControlContractAddress);
    this.crossBlockchainControlContract = CrossBlockchainControl.load(
        this.crossBlockchainControlContractAddress, this.web3j, this.tm, this.freeGasProvider);
  }


  public void deployContracts() throws Exception {
    LOG.info("Deploying contracts");
    this.blockHeaderStorageContract =
        BlockHeaderStorage.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.blockHeaderStorageContractAddress = this.blockHeaderStorageContract.getContractAddress();
    LOG.info(" Block Header Storage contract deployed on at address: {}", this.blockHeaderStorageContractAddress);

    this.crossBlockchainControlContract =
        CrossBlockchainControl.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.crossBlockchainControlContractAddress = this.crossBlockchainControlContract.getContractAddress();
    LOG.info(" Cross-Blockchain Control contract deployed on at address: {}", this.crossBlockchainControlContractAddress);


  }
}
