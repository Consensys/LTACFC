package tech.pegasys.ltacfc.common;

import org.apache.tuweni.bytes.Bytes;
import org.web3j.rlp.RlpString;

import java.math.BigInteger;

public class FormatConversion {
  public static final int BYTES_IN_ADDRESS = 20;

  public static byte[] addressStringToBytes(String address) {
    Bytes eventDataBytes = Bytes.fromHexString(address);
    byte[] addressBytes = eventDataBytes.toArray();

    if (addressBytes.length > BYTES_IN_ADDRESS) {
      throw new RuntimeException("Unexpected address length: " + addressBytes.length + " for address: " + address);
    }
    if (addressBytes.length < BYTES_IN_ADDRESS) {
      byte[] b = new byte[BYTES_IN_ADDRESS];
      System.arraycopy(addressBytes, 0, b, BYTES_IN_ADDRESS - addressBytes.length, addressBytes.length);
      addressBytes = b;
    }
    return addressBytes;
  }


  public static byte[] hexStringToByteArray(String hexString) {
    Bytes eventDataBytes = Bytes.fromHexString(hexString);
    return eventDataBytes.toArray();
  }


}
