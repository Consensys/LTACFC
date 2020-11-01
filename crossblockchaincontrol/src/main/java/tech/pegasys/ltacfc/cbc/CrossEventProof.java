/*
 * Copyright 2020 ConsenSys AG.
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
package tech.pegasys.ltacfc.cbc;

import java.math.BigInteger;
import java.util.List;

/**
 * Information to allow a transaction receipt to be verified within a Solidity contract.
 */
public class CrossEventProof {
  BigInteger blockchainId;
  String crossBlockchainControlContract;
  byte[] transactionReceiptRoot;
  byte[] transactionReceipt;
  List<BigInteger> proofOffsets;
  List<byte[]> proofs;

  /**
   * Create new proof.
   *
   * @param blockchainId  Blockchain that emitted the event / the transaction receipt relates to.
   * @param crossBlockchainControlContract Address of Cross-Blockchain Control Contract that emitted the event.
   * @param transactionReceiptRoot Transaction receipt root for the block that the event was emitted in. This
   *                              transaction receipt root needs to have been communicated to the receiving blockchain.
   * @param transactionReceipt Transaction receipt for receipt that emitted the event. Contains the event information.
   * @param proofOffsets Offsets within the proofs byte arrays of the RLP encoded hashes.
   * @param proofs RLP encoded hashes from the transaction receipt trie.
   */
  public CrossEventProof(
      BigInteger blockchainId,
      String crossBlockchainControlContract,
      byte[] transactionReceiptRoot,
      byte[] transactionReceipt,
      List<BigInteger> proofOffsets,
      List<byte[]> proofs) {

    this.blockchainId = blockchainId;
    this.crossBlockchainControlContract = crossBlockchainControlContract;
    this.transactionReceiptRoot = transactionReceiptRoot;
    this.transactionReceipt = transactionReceipt;
    this.proofOffsets = proofOffsets;
    this.proofs = proofs;
  }

  public BigInteger getBlockchainId() {
    return blockchainId;
  }

  public String getCrossBlockchainControlContract() {
    return crossBlockchainControlContract;
  }

  public byte[] getTransactionReceiptRoot() {
    return transactionReceiptRoot;
  }

  public byte[] getTransactionReceipt() {
    return transactionReceipt;
  }

  public List<BigInteger> getProofOffsets() {
    return proofOffsets;
  }

  public List<byte[]> getProofs() {
    return proofs;
  }

//  public CrossBlockchainControl.EventProof asEventProof() {
//    return new CrossBlockchainControl.EventProof(
//        this.blockchainId, this.crossBlockchainControlContract,
//        this.transactionReceiptRoot, this.transactionReceipt, this.proofOffsets, this.proofs);
//  }
}
