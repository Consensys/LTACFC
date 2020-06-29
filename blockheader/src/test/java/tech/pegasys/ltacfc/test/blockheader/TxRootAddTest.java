/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package tech.pegasys.ltacfc.test.blockheader;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.soliditywrappers.TxReceiptsRootStorage;
import tech.pegasys.ltacfc.test.TestIdentity;
import tech.pegasys.ltacfc.test.registrar.AbstractRegistrarTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;

public class TxRootAddTest extends AbstractRegistrarTest {
  final byte[] txReceiptRoot = new byte[32];

  TxReceiptsRootStorage txReceiptRootStorageContract;

  protected void deployTxReceiptRootStorageContract() throws Exception {
    this.txReceiptRootStorageContract = TxReceiptsRootStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.registrarContract.getContractAddress()).send();
  }


  @Test
  public void addTxReceipt() throws Exception {
    setupWeb3();
    deployContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);

    // Set-up one signer for the blockchain
    TestIdentity newSigner = new TestIdentity();
    TransactionReceipt receipt = this.registrarContract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt()).send();
    assert(receipt.isStatusOK());

    deployTxReceiptRootStorageContract();

    // Sign the txReceiptRoot
    Sign.SignatureData signatureData = newSigner.sign(this.txReceiptRoot);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // Check that the receipt root is has been registered.
    boolean containsReceiptRoot = this.txReceiptRootStorageContract.containsTxReceiptRoot(blockchainId, this.txReceiptRoot).send();
    assertFalse(containsReceiptRoot);

    // This will revert if the signature does not verify
    receipt = this.txReceiptRootStorageContract.addTxReceiptRoot(blockchainId, signers, sigR, sigS, sigV, this.txReceiptRoot).send();
    assert(receipt.isStatusOK());

    // Check that the receipt root is has been registered.
    containsReceiptRoot = this.txReceiptRootStorageContract.containsTxReceiptRoot(blockchainId, this.txReceiptRoot).send();
    assert(containsReceiptRoot);
  }
}

