# Layer Two Atomic Cross-Blockchain Function Call Protocol

This repo contains the Layer 2 Atomic Cross-Blockchain Function Call (LTACFC)
protocol contracts, test code, and Relay Node code. This is an implementation 
of this paper: https://arxiv.org/abs/2005.09790.

The directories:
* blockheader: Block header / transaction receipt root storage.
* common: Common test code.
* crossblockchaincontrol: Cross-Blockchain Control Contract.
* crypto: ECDSA and BLS signature verification contracts.
* gradle: build system related files.
* main: Runner for overall simulation.
* receipts: Transaction receipt processing.
* registrar: Registrar of public keys of transaction receipt root signers.
* rlp: RLP processing code from Hyperledger Besu.
* scripts: Scripts to set-up and run Hyperledger Besu.
* trie: Merkle Patricia Trie code from Hyperledger Besu.

