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
package tech.pegasys.ltacfc.examples.complex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Credentials;
import tech.pegasys.ltacfc.cbc.AbstractBlockchain;
import tech.pegasys.ltacfc.examples.complex.soliditywrappers.BusLogic;


import java.io.IOException;
import java.math.BigInteger;

public class Bc2BusLogic extends AbstractBlockchain {
  static final Logger LOG = LogManager.getLogger(Bc2BusLogic.class);

  BusLogic busLogicContract;

  public Bc2BusLogic(Credentials credentials, String bcId, String uri, String gasPriceStrategy, String blockPeriod) throws IOException {
    super(credentials, bcId, uri, gasPriceStrategy, blockPeriod);
  }

  public void deployContracts(
      String cbc,
      BigInteger balancesBcId, String balances,
      BigInteger oracleBcId, String oracle,
      BigInteger stockBcId, String stock) throws Exception {
    LOG.info("Deploy Business Logic Contract to blockchain 0x{}", this.blockchainId.toString(16));
    this.busLogicContract = BusLogic.deploy(this.web3j, this.tm, this.gasProvider,
        cbc, balancesBcId, balances, oracleBcId, oracle, stockBcId, stock).send();
    LOG.info(" BusLogic Contract: {}", this.busLogicContract.getContractAddress());
  }

  public String getRlpFunctionSignature_StockShipment(String seller, String buyer, BigInteger quantity) {
    return this.busLogicContract.getRLP_stockShipment(seller, buyer, quantity);
  }
}
