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
package tech.pegasys.ltacfc.test.examples.twochain;

import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.CbcTestLockableStorage;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.examples.twochain.soliditywrappers.OtherBlockchainContract;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;

import static org.junit.Assert.assertEquals;


public class LockableStorageTest extends AbstractWeb3Test {
  OtherBlockchainContract otherBlockchainContract;
  LockableStorage lockableStorageContract;
  CbcTestLockableStorage mockCrossBlockchainControlContract;




  protected void deployContracts() throws Exception {
    this.mockCrossBlockchainControlContract = CbcTestLockableStorage.deploy(this.web3j, this.tm, this.freeGasProvider).send();
    this.lockableStorageContract = LockableStorage.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.mockCrossBlockchainControlContract.getContractAddress()).send();
    this.otherBlockchainContract = OtherBlockchainContract.deploy(this.web3j, this.tm, this.freeGasProvider,
        this.lockableStorageContract.getContractAddress()).send();
    this.lockableStorageContract.setBusinessLogicContract(this.otherBlockchainContract.getContractAddress()).send();
  }


  @Test
  public void checkDeployment() throws Exception {
    setupWeb3();
    deployContracts();

    assert(!this.lockableStorageContract.locked().send());
    assertEquals(this.lockableStorageContract.businessLogicContract().send(), this.otherBlockchainContract.getContractAddress());
  }


    @Test
  public void boolTestSingleBlockchain() throws Exception {
    setupWeb3();
    deployContracts();

    // Check the default value.
    assert (!this.otherBlockchainContract.getFlag().send());

    // Check the value after setting
    try {
      this.otherBlockchainContract.setFlag(true).send();
    } catch (TransactionException ex) {
      if (ex.getTransactionReceipt().isPresent()) {
        TransactionReceipt receipt = ex.getTransactionReceipt().get();
        System.out.println("Revert Reason: " + receipt.getRevertReason());
      }
      throw ex;
    }
    assert(this.otherBlockchainContract.getFlag().send());
  }




}

