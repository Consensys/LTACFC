package tech.pegasys.ltacfc.examples.complex.sim;

import tech.pegasys.ltacfc.examples.complex.Bc3Balances;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SimBalancesContract {
  private String transfer_from;
  private String transfer_to;
  private BigInteger transfer_amount;
  private Bc3Balances bc3Balances;

  Map<String, BigInteger> balances = new HashMap<>();

  public SimBalancesContract(Bc3Balances contract) {
    this.bc3Balances = contract;
  }


  public void setBalance(String account, BigInteger newBalance) {
    this.balances.put(account, newBalance);
  }

  public void transfer(String from, String to, BigInteger amount) throws Exception {
    this.transfer_from = from;
    this.transfer_to = to;
    this.transfer_amount = amount;

    BigInteger fromBalance = getBalance(from);
    BigInteger toBalance = getBalance(to);
    if (fromBalance.longValue() < amount.longValue()) {
      throw new Exception("Value transfer: insufficient balance. From balance: " + fromBalance + " Transfer Amount: " + amount);
    }

    setBalance(from, fromBalance.subtract(amount));
    setBalance(to, toBalance.add(amount));
  }

  public BigInteger getBalance(String account) {
    return this.balances.get(account);
  }

  public String getRlpFunctionSignature_transfer() {
    return this.bc3Balances.getRlpFunctionSignature_Transfer(this.transfer_from, this.transfer_to, this.transfer_amount);
  }

}
