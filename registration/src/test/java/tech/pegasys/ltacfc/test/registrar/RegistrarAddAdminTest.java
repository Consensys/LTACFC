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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.TransactionManager;
import tech.pegasys.ltacfc.soliditywrappers.Registrar;

import static junit.framework.TestCase.assertFalse;

public class RegistrarAddAdminTest extends AbstractRegistrarTest {

  @Test
  public void contractDeployerIsAdmin() throws Exception {
    setupWeb3();
    deployContract();

    assert(this.contract.isAdmin(this.credentials.getAddress()).send());
  }


  @Test
  public void addAdmin() throws Exception {
    setupWeb3();
    deployContract();

    Credentials credentials2 = createNewIdentity();
    String cred2Address = credentials2.getAddress();

    TransactionReceipt receipt = this.contract.addAdmin(cred2Address).send();
    assert(receipt.isStatusOK());

    assert(this.contract.isAdmin(cred2Address).send());
  }

  // Do not allow a non-admin to add an admin.
  @Test
  public void failAddAdminByNonAdmin() throws Exception {
    setupWeb3();
    deployContract();

    Credentials credentials2 = createNewIdentity();
    TransactionManager tm2 = createTransactionManager(credentials2);
    Registrar regContract2 = deployContract(tm2);

    String cred2Address = credentials2.getAddress();

    try {
      TransactionReceipt receipt = regContract2.addAdmin(credentials2.getAddress()).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }

    assertFalse(this.contract.isAdmin(cred2Address).send());
  }

  // Do not allow an admin to be added twice.
  @Test
  public void failAddSameAdminTwice() throws Exception {
    setupWeb3();
    deployContract();

    try {
      TransactionReceipt receipt = this.contract.addAdmin(this.credentials.getAddress()).send();
      assertFalse(receipt.isStatusOK());
    } catch (TransactionException ex) {
      // Do nothing.
    }
  }

}
