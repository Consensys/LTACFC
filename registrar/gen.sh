#!/usr/bin/env bash
set -e
rm -rf build

HERE=registrar
BUILDDIR=$HERE/build
CONTRACTSDIR=$HERE/src/main/solidity
OUTPUTDIR=$HERE/src/main/java/tech/pegasys/ltacfc/soliditywrappers
PACKAGE=tech.pegasys.ltacfc.soliditywrappers
WEB3J=web3j

# compiling one file also compiles its dependendencies. We use overwrite to avoid the related warnings.
solc $CONTRACTSDIR/Registrar.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/VotingAlgMajority.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
solc $CONTRACTSDIR/VotingAlgMajorityWhoVoted.sol --allow-paths . --bin --abi --optimize -o $BUILDDIR --overwrite
# ls -al $BUILDDIR

$WEB3J solidity generate -a=$BUILDDIR/Registrar.abi -b=$BUILDDIR/Registrar.bin -o=$BUILDDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/VotingAlgMajority.abi -b=$BUILDDIR/VotingAlgMajority.bin -o=$BUILDDIR -p=$PACKAGE
$WEB3J solidity generate -a=$BUILDDIR/VotingAlgMajorityWhoVoted.abi -b=$BUILDDIR/VotingAlgMajorityWhoVoted.bin -o=$BUILDDIR -p=$PACKAGE

mkdir -p $OUTPUTDIR
cp $BUILDDIR/tech/pegasys/ltacfc/soliditywrappers/* $OUTPUTDIR/.
# ls -al $OUTPUTDIR


