package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.soliditywrappers.TxReceiptsRootStorage;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
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
  }


}
