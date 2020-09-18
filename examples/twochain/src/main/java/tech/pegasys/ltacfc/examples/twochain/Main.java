package tech.pegasys.ltacfc.examples.twochain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;

public class Main {
  static final Logger LOG = LogManager.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    Credentials creds = createCredentials();

    OtherBc otherBlockchain = new OtherBc();
    otherBlockchain.setupWeb3(creds);
    otherBlockchain.deployContracts();

    BigInteger otherBcId = otherBlockchain.blockchainId;
    String otherContractAddress = otherBlockchain.otherBlockchainContract.getContractAddress();

    RootBc rootBlockchain = new RootBc();
    rootBlockchain.setupWeb3(creds);
    rootBlockchain.deployContracts(otherBcId, otherContractAddress);



  }


  public static Credentials createCredentials() throws Exception {
    String privateKey = new KeyPairGen().generateKeyPairGetPrivateKey();
//    System.out.println("Priv2: " + privateKey);
    return Credentials.create(privateKey);
  }




}
