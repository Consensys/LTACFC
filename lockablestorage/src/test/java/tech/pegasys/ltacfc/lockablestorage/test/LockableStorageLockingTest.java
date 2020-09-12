package tech.pegasys.ltacfc.lockablestorage.test;

import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.exceptions.ContractCallException;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorage;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.LockableStorageWrapper;
import tech.pegasys.ltacfc.lockablestorage.soliditywrappers.MockCbcForLockableStorageTest;
import tech.pegasys.ltacfc.test.AbstractWeb3Test;

import java.math.BigInteger;

public class LockableStorageLockingTest extends AbstractLockableStorageTest {


  // By default, locking is off. As such, setting a uint256 will not encounter any locks.
  @Test
  public void singleBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();
  }

  // By default, locking is off. As such, setting a uint256 as part of a cross-blockchain
  // call will not encounter any locks.
  @Test
  public void crossBlockchainNotLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;
    BigInteger val = BigInteger.TEN;

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();
    // The mock CrossBlockchainControlContract should now indicate a cross-blockchain call.
    assert(!this.mockCrossBlockchainControlContract.isSingleBlockchainCall().send());

    this.storageWrapper.setUint256(theUint, val).send();

    // The contract should now be locked.
    assert(this.lockableStorageContract.locked().send());

    // Even when the value is in provisional storage, should be able to return the value.
    assert(this.storageWrapper.getUint256(theUint).send().compareTo(val) == 0);

  }

  // An exception is thrown if a locked contract is to be written to by a single blockchain call.
  @Test
  public void singleBlockchainLocked() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();

    // Zero Root Blockchain Id is deemed to indicate an no active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ZERO).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }
  }

  // An exception is thrown if a locked contract is to be written to by a single blockchain call.
  @Test
  public void crossBlockchainDifferentCall() throws Exception {
    setupWeb3();
    deployContracts();

    BigInteger theUint = BigInteger.ZERO;

    // Any non-zero Root Blockchain Id is deemed to indicate an active Cross-Blockchain call.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();

    this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();

    // Simulate another cross-blockchain call by using a different Root Blockchain Id.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.TWO).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.getUint256(theUint).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // thrown as expected
    }

    // Same root blockchain, different transaction.
    this.mockCrossBlockchainControlContract.setRootBlockchainId(BigInteger.ONE).send();
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ONE).send();

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.setUint256(theUint, BigInteger.ONE).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (TransactionException ex) {
      System.out.println("Exception thrown as expected: " + ex.getTransactionReceipt().get().getRevertReason());
    }

    // This will fail as the contract is locked.
    try {
      this.storageWrapper.getUint256(theUint).send();
      throw new Exception("Unexpectedly, no revert thrown");
    } catch (ContractCallException ex) {
      // Revert thrown as expected
    }

    // Same root blockchain and same transaction.
    this.mockCrossBlockchainControlContract.setCrossBlockchainTransactionId(BigInteger.ZERO).send();

    // Writing and reading should now work.
    this.storageWrapper.setUint256(theUint, BigInteger.TWO).send();
    assert(this.storageWrapper.getUint256(theUint).send().compareTo(BigInteger.TWO) == 0);
  }


}
