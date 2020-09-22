package tech.pegasys.ltacfc.examples.twochain;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import org.apache.tuweni.units.bigints.UInt256;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.hyperledger.besu.ethereum.rlp.RLPInput;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpList;
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

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.Function;

public class OtherBc extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(OtherBc.class);

  static final BigInteger OTHER_BLOCKCHAIN_ID = BigInteger.valueOf(31);
  static final String OTHER_IP_PORT = "127.0.0.1:8310";

  OtherBlockchainContract otherBlockchainContract;
  LockableStorage lockableStorageContract;


  public OtherBc() {
    super(OTHER_BLOCKCHAIN_ID, OTHER_IP_PORT);
  }


  public void deployContracts() throws Exception {
    LOG.info("Deploy Other Blockchain Contracts");
    super.deployContracts();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.crossBlockchainControlContract.getContractAddress()).send();
    this.otherBlockchainContract =
        OtherBlockchainContract.deploy(this.web3j, this.tm, this.freeGasProvider,
          this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.otherBlockchainContract.getContractAddress()).send();
  }

  public void setVal(BigInteger val) throws Exception {
    this.otherBlockchainContract.setVal(val).send();
  }

  public String getRlpFunctionSignature_GetVal() {
    return this.otherBlockchainContract.getRLP_getVal();
  }

  public String getRlpFunctionSignature_SetVal(BigInteger val) {
    return this.otherBlockchainContract.getRLP_setVal(val);
  }

  public String getRlpFunctionSignature_SetValues(BigInteger val1, BigInteger val2) {
    return this.otherBlockchainContract.getRLP_setValues(val1, val2);
  }

  public void segment(
      BigInteger rootBlockchainId, String rootBlockchainCBC, byte[] startTxReceiptRoot, byte[] startTxReceipt, List<BigInteger> proofOffsets, List<byte[]> proof,
      List<BigInteger> callPath) throws Exception {

    RlpDumper.dump(RLP.input(Bytes.wrap(startTxReceipt)));

    TransactionReceipt txR = this.crossBlockchainControlContract.segment(rootBlockchainId, rootBlockchainCBC,
        startTxReceiptRoot, startTxReceipt, proofOffsets, proof, callPath).send();
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    LOG.info("Cross Bc Id: {}", this.crossBlockchainControlContract.activeCallCrossBlockchainTransactionId().send().toString(16));
    LOG.info("Call: {}", new BigInteger(1, this.crossBlockchainControlContract.activeCall().send()).toString(16));
//    LOG.info("Timeout: {}", this.crossBlockchainControlContract.tempActiveTimeout().send().toString(16));

  }

  private void check(byte[] txReceipt) {
  }

}
