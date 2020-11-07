package tech.pegasys.ltacfc.examples.complex.sim;

import tech.pegasys.ltacfc.examples.complex.Bc5Stock;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SimStockContract {
  private String transfer_from;
  private String transfer_to;
  private BigInteger transfer_quantity;
  private Bc5Stock bc5Stock;

  Map<String, BigInteger> stockLevels = new HashMap<>();

  public SimStockContract(Bc5Stock contract) {
    this.bc5Stock = contract;
  }


  public void setStock(String account, BigInteger newQuantity) {
    this.stockLevels.put(account, newQuantity);
  }

  public void transfer(String from, String to, BigInteger quantity) throws Exception {
    this.transfer_from = from;
    this.transfer_to = to;
    this.transfer_quantity = quantity;

    BigInteger fromBalance = getStock(from);
    BigInteger toBalance = getStock(to);
    if (fromBalance.longValue() < quantity.longValue()) {
      throw new Exception("Stock transfer: insufficient balance. From stock: " + fromBalance + ", Transfer quantity: " + quantity);
    }

    setStock(from, fromBalance.subtract(quantity));
    setStock(to, toBalance.add(quantity));
  }

  public BigInteger getStock(String account) {
    return this.stockLevels.get(account);
  }

  public String getRlpFunctionSignature_transfer() {
    return this.bc5Stock.getRlpFunctionSignature_Transfer(this.transfer_from, this.transfer_to, this.transfer_quantity);
  }

}
