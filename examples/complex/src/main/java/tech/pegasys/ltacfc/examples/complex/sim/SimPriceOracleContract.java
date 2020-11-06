package tech.pegasys.ltacfc.examples.complex.sim;

import tech.pegasys.ltacfc.examples.complex.Bc4Oracle;

import java.math.BigInteger;

public class SimPriceOracleContract {
  private BigInteger price;
  private Bc4Oracle bc4Oracle;

  public SimPriceOracleContract(Bc4Oracle contract) {
    this.bc4Oracle = contract;
  }

  public void setPrice(BigInteger newPrice) {
    this.price = newPrice;
  }

  public BigInteger getPrice() {
    return this.price;
  }

  public String getRlpFunctionSignature_getPrice() {
    return this.bc4Oracle.getRlpFunctionSignature_GetPrice();
  }
}
