#!/usr/bin/env bash
set -e
rm -rf build

HERE=blockheader
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java/tech/pegasys/ltacfc/soliditywrappers
PACKAGE=tech.pegasys.ltacfc.soliditywrappers
WEB3J=web3j

# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/TxReceiptsRootStorage.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR
$WEB3J solidity generate -a=$BUILDDIR/TxReceiptsRootStorage.abi -b=$BUILDDIR/TxReceiptsRootStorage.bin -o=$BUILDDIR -p=$PACKAGE
mkdir -p $OUTPUTDIR
cp $BUILDDIR/tech/pegasys/ltacfc/soliditywrappers/* $OUTPUTDIR/.
# ls -al $OUTPUTDIR


