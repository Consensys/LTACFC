package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import tech.pegasys.ltacfc.common.AnIdentity;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.soliditywrappers.TxReceiptsRootStorage;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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



}
