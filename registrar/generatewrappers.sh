#!/usr/bin/env bash
rm -rf build

HERE=registrar
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/contracts
PACKAGE=tech.pegasys.ltacfc.soliditywrappers

cd ..

# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/Registrar.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
ls -al $BUILDDIR

WEB3J=web3j

$WEB3J solidity generate -a=$BUILDDIR/Registrar.abi -b=$BUILDDIR/Registrar.bin -o=$BUILDDIR -p=$PACKAGE

cd $HERE

ls -al build/tech/pegasys/ltacfc/soliditywrappers
