package tech.pegasys.ltacfc.examples.complex.sim;

import tech.pegasys.ltacfc.examples.complex.Bc1TradeWallet;

import java.math.BigInteger;

// Note: only need to simulate enough to know what the parameters for function calls should be.
public class SimTradeWallet {
  private String executeTrade_seller;
  private BigInteger executeTrade_quantity;
  private Bc1TradeWallet rootContract;

  SimBusLogicContract simBusLogicContract;

  public SimTradeWallet(Bc1TradeWallet contract, SimBusLogicContract businessLogicContract) {
    this.rootContract = contract;
    this.simBusLogicContract = businessLogicContract;
  }

  public void executeTrade(String msgSender, String seller, BigInteger quantity) throws Exception {
    this.executeTrade_seller = seller;
    this.executeTrade_quantity = quantity;

    this.simBusLogicContract.stockShipment(seller, msgSender, quantity);
  }

  public String getRlpFunctionSignature_executeTrade() {
    return this.rootContract.getRlpFunctionSignature_ExecuteTrade(this.executeTrade_seller, this.executeTrade_quantity);
  }
}
