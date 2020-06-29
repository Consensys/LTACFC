#!/usr/bin/env bash
rm -rf build

BUILDDIR=./build
CONTRACTSDIR=solidity
PACKAGE=tech.pegasys.ltacfc.soliditywrappers


# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/Test.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
ls -al $BUILDDIR

WEB3J=web3j

$WEB3J solidity generate -a=$BUILDDIR/Test.abi -b=$BUILDDIR/Test.bin -o=$BUILDDIR -p=$PACKAGE


ls -al build/tech/pegasys/ltacfc/soliditywrappers
