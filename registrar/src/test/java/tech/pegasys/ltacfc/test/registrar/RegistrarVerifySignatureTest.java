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
package tech.pegasys.ltacfc.test.registrar;

import org.junit.Test;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.registrar.RegistrarVoteTypes;
import tech.pegasys.ltacfc.test.TestIdentity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;

public class RegistrarVerifySignatureTest extends AbstractRegistrarTest {
  final byte[] plainText = new byte[]{0x00};

  @Test
  public void verifyOneSignature() throws Exception {
    setupWeb3();
    deployContract();
    BigInteger blockchainId = BigInteger.TEN;
    addBlockchain(blockchainId);
    TestIdentity newSigner = new TestIdentity();

    TransactionReceipt receipt = this.contract.proposeVote(
        RegistrarVoteTypes.VOTE_ADD_SIGNER.asBigInt(), blockchainId, newSigner.getAddressAsBigInt()).send();
    assert(receipt.isStatusOK());

    Sign.SignatureData signatureData = newSigner.sign(this.plainText);
    List<String> signers = new ArrayList<>();
    signers.add(newSigner.getAddress());
    List<byte[]> sigR = new ArrayList<>();
    sigR.add(signatureData.getR());
    List<byte[]> sigS = new ArrayList<>();
    sigS.add(signatureData.getS());
    List<BigInteger> sigV = new ArrayList<>();
    sigV.add(BigInteger.valueOf(signatureData.getV()[0]));

    // This will revert if the signature does not verify
    receipt = this.contract.verify(blockchainId, signers, sigR, sigS, sigV, this.plainText).send();
    assert(receipt.isStatusOK());
  }



  // TODO not enough signers
  // TODO two signers
  // TODO one invalid signature
  // TODO one valid and one invalid signature
  // TODO lengths of arrays not being correct
  // TODO one of signers not being registered for this blockchain

}
