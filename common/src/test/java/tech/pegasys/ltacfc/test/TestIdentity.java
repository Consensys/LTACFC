package tech.pegasys.ltacfc.test;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;

public class TestIdentity {
  private ECKeyPair keyPair;
  private String address;

  public TestIdentity() {
    KeyPairGen keyGen = new KeyPairGen();
    String privateKey = keyGen.generateKeyPairGetPrivateKey();
    this.keyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
    this.address = Keys.getAddress(keyPair.getPublicKey().toString(16));
  }

  public Sign.SignatureData sign(byte[] plainText) {
    return Sign.signMessage(plainText, this.keyPair);
  }

  public ECKeyPair getKeyPair() {
    return keyPair;
  }

  public String getAddress() {
    return address;
  }

  public BigInteger getAddressAsBigInt() {
    return new BigInteger(this.address, 16);
//    return new BigInteger(this.address.substring(2), 16);
  }
}
