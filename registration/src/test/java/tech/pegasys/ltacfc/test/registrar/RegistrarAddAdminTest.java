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
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;
import tech.pegasys.ltacfc.utils.crypto.EcdsaSignatureConversion;
import tech.pegasys.ltacfc.utils.crypto.KeyPairGen;

import java.math.BigInteger;

public class RegistrarAddAdminTest extends AbstractRegistrarTest {
  Registrar contract;

  @Test
  public void addAdmin() throws Exception {
    setupWeb3();
    deployContract();

    Credentials credentials2 = createNewIdentity();
    TransactionManager tm2 = createTransactionManager(credentials2);
    Registrar regContract2 = deployContract(tm2);

    String cred2Address = credentials2.getAddress();

    TransactionReceipt receipt = regContract2.addAdmin(credentials2.getAddress()).send();
    assert(receipt.isStatusOK());

    assert(this.contract.isAdmin(cred2Address).send());
  }
}
