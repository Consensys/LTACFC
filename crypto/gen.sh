#!/usr/bin/env bash
set -e
rm -rf build

HERE=crypto
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java/tech/pegasys/ltacfc/soliditywrappers
PACKAGE=tech.pegasys.ltacfc.soliditywrappers
WEB3J=web3j

# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/BlsSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/EcdsaSignatureTest.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/BlsSignatureTest.abi -b=$BUILDDIR/BlsSignatureTest.bin -o=$BUILDDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/EcdsaSignatureTest.abi -b=$BUILDDIR/EcdsaSignatureTest.bin -o=$BUILDDIR -p=$PACKAGE

mkdir -p $OUTPUTDIR
cp $BUILDDIR/tech/pegasys/ltacfc/soliditywrappers/* $OUTPUTDIR/.
# ls -al $OUTPUTDIR


