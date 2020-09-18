package tech.pegasys.ltacfc.examples.twochain;

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
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.RootBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.soliditywrappers.TxReceiptsRootStorage;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
import java.util.concurrent.ScheduledThreadPoolExecutor;

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
  }

  public String getRlpFunctionSignature_SomeComplexBusinessLogic(BigInteger val) {
    return this.rootBlockchainContract.getRLP_someComplexBusinessLogic(val);
  }

  public TransactionReceipt start(BigInteger transactionId, BigInteger timeout, byte[] callGraph) throws Exception {
    LOG.info("TxId: {}", transactionId.toString(16));
    LOG.info("Timeout: {}", timeout);
    LOG.info("Call Graph: {}", callGraph);
    return this.crossBlockchainControlContract.start(transactionId, timeout, callGraph).send();
  }
}
