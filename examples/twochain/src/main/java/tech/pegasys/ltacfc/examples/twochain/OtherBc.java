package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tuweni.bytes.Bytes;
import org.hyperledger.besu.ethereum.rlp.RLP;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.rlp.RlpDumper;
import tech.pegasys.ltacfc.soliditywrappers.CrossBlockchainControl;

import java.math.BigInteger;
import java.util.List;

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
    LOG.info(" Other Blockchain Contract: {}", this.otherBlockchainContract.getContractAddress());
    LOG.info(" Lockable Storage Contract: {}", this.lockableStorageContract.getContractAddress());
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

  public boolean storageIsLocked() throws Exception {
    Boolean isLocked = this.lockableStorageContract.locked().send();
    return  isLocked;
  }

  public TransactionReceipt segment(
      BigInteger rootBlockchainId, String rootBlockchainCBC, byte[] startTxReceiptRoot, byte[] startTxReceipt, List<BigInteger> proofOffsets, List<byte[]> proof,
      List<BigInteger> callPath) throws Exception {

    RlpDumper.dump(RLP.input(Bytes.wrap(startTxReceipt)));

    TransactionReceipt txR = this.crossBlockchainControlContract.segment(rootBlockchainId, rootBlockchainCBC,
        startTxReceiptRoot, startTxReceipt, proofOffsets, proof, callPath).send();
    if (!txR.isStatusOK()) {
      throw new Exception("Segment transaction failed");
    }

    LOG.info("Cross Bc Id: {}", this.crossBlockchainControlContract.activeCallCrossBlockchainTransactionId().send().toString(16));
    LOG.info("CallGraph: {}", new BigInteger(1, this.crossBlockchainControlContract.activeCallGraph().send()).toString(16));

    List<CrossBlockchainControl.SegmentEventResponse> segmentEventResponses = this.crossBlockchainControlContract.getSegmentEvents(txR);
    CrossBlockchainControl.SegmentEventResponse segmentEventResponse = segmentEventResponses.get(0);
    LOG.info("Segment Event:");
    LOG.info(" _crossBlockchainTransactionId: {}", segmentEventResponse._crossBlockchainTransactionId.toString(16));
    LOG.info(" _callPath len: {}", segmentEventResponse._callPath.size());
    LOG.info(" _hashOfCallGraph: {}", new BigInteger(1, segmentEventResponse._hashOfCallGraph).toString(16));
    LOG.info(" _success: {}", segmentEventResponse._success);
    LOG.info(" _returnValue: {}", new BigInteger(1, segmentEventResponse._returnValue).toString(16));
    LOG.info(" num locked contracts: {}", segmentEventResponse._lockedContracts.size());
//    for (String lockedContractAddress: segmentEventResponse._lockedContracts) {
//      LOG.info(" locked contracts: {}", lockedContractAddress);
//    }

    return txR;
  }

  private void check(byte[] txReceipt) {
  }

}
