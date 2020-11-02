package tech.pegasys.ltacfc.common;

import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

public class CredentialsCreator {

  public static Credentials createCredentials() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
//    System.out.println("Priv2: " + privateKey);
    return Credentials.create(privateKey);
  }
}
