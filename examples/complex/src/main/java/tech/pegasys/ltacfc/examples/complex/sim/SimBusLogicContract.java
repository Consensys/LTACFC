package tech.pegasys.ltacfc.examples.complex.sim;

import tech.pegasys.ltacfc.examples.complex.Bc2BusLogic;

import java.math.BigInteger;

public class SimBusLogicContract {
  private String stockShipment_seller;
  private String stockShipment_buyer;
  private BigInteger stockShipment_quantity;
  private Bc2BusLogic bc2BusLogic;

  SimPriceOracleContract priceOracleContract;
  SimBalancesContract balancesContract;
  SimStockContract stockContract;

  public SimBusLogicContract(Bc2BusLogic contract, SimPriceOracleContract oracleContract, SimBalancesContract balancesContract, SimStockContract stockContract) {
    this.bc2BusLogic = contract;
    this.priceOracleContract = oracleContract;
    this.balancesContract = balancesContract;
    this.stockContract = stockContract;
  }

  public void stockShipment(String seller, String buyer, BigInteger quantity) throws Exception {
    this.stockShipment_seller = seller;
    this.stockShipment_buyer = buyer;
    this.stockShipment_quantity = quantity;


    BigInteger currentPrice = this.priceOracleContract.getPrice();
    BigInteger cost = currentPrice.multiply(quantity);

    // To address pays for goods.
    this.balancesContract.transfer(buyer, seller, cost);

    // Goods are shipped from From to To.
    this.stockContract.transfer(seller, buyer, quantity);
  }

  public String getRlpFunctionSignature_stockShipment() {
    return this.bc2BusLogic.getRlpFunctionSignature_StockShipment(this.stockShipment_seller, this.stockShipment_buyer, this.stockShipment_quantity);
  }
}
