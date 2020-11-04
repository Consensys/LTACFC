package tech.pegasys.ltacfc.cbc;

import java.math.BigInteger;
import java.util.List;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import tech.pegasys.ltacfc.common.FormatConversion;


public class CallGraphHelper {


  public static RlpList createRootFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString, List<RlpType> calledFunctions) {
    return createIntermediateFunctionCall(blockchainId, contractAddress, rlpBytesAsString, calledFunctions);
  }

  public static RlpList createIntermediateFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString, List<RlpType> calledFunctions) {
    RlpList func = createFunctionCall(blockchainId, contractAddress, rlpBytesAsString);
    calledFunctions.add(0, (RlpType) func);
    return new RlpList(calledFunctions);
  }

  public static RlpList createLeafFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString) {
    return createFunctionCall(blockchainId, contractAddress, rlpBytesAsString);
  }

  private static RlpList createFunctionCall(BigInteger blockchainId, String contractAddress, String rlpBytesAsString) {
    return new RlpList(
        RlpString.create(blockchainId),
        RlpString.create(FormatConversion.addressStringToBytes(contractAddress)),
        RlpString.create(FormatConversion.hexStringToByteArray(rlpBytesAsString))
    );
  }
}
