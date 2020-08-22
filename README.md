# Layer Two Atomic Cross-Blockchain Function Call Protocol

This repo contains the Layer 2 Atomic Cross-Blockchain Function Call (LTACFC)
protocol solidity, test code, and Relay Node code. This is an implementation 
of this paper: https://arxiv.org/abs/2005.09790.

The directories:
* blockheader: Block header / transaction receipt root storage.
* common: Common test code.
* crossblockchaincontrol: Cross-Blockchain Control Contract.
* crypto: ECDSA and BLS signature verification solidity.
* gradle: build system related files.
* main: Runner for overall simulation.
* receipts: Transaction receipt processing.
* registrar: Registrar of blockchain public keys for transaction receipt root signing.
* rlp: RLP processing code from Hyperledger Besu.
* scripts: Scripts to set-up and run Hyperledger Besu.
* trie: Merkle Patricia Trie code from Hyperledger Besu.



## Building
This repo relies on a special version of Web3J. To create this:
* Clone the repo to the directory ./LTACFC/..  : git clone https://github.com/drinkcoffee/web3j-rlp
* cd ./TLACFC/web3j-rlp
* ./gradlew build
* cd codegen/build/distributions
* tar xvf codegen-4.7.0-SNAPSHOT.tar

