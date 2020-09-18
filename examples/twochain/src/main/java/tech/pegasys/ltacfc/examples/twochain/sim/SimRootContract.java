package tech.pegasys.ltacfc.examples.twochain.sim;

import com.fasterxml.jackson.databind.node.BigIntegerNode;

import java.math.BigInteger;

public class SimRootContract {
  BigInteger val;

  SimOtherContract simOtherContract;

  public boolean someComplexBusinessLogicIfTrue = false;
  public BigInteger someComplexBusinessLogicSetValParameter = BigInteger.ZERO;
  public BigInteger someComplexBusinessLogicSetValuesParameter1 = BigInteger.ZERO;
  public BigInteger someComplexBusinessLogicSetValuesParameter2 = BigInteger.ZERO;


  public SimRootContract(SimOtherContract otherContract) {
    this.simOtherContract = otherContract;
  }

  public void someComplexBusinessLogic(BigInteger _val) {
    // Use the value on the other blockchain as a threshold
    BigInteger currentThreshold = this.simOtherContract.getVal();

    // if (_val > currentThreshold)
    if (_val.compareTo(currentThreshold) > 0) {
      this.someComplexBusinessLogicIfTrue = true;
      this.someComplexBusinessLogicSetValuesParameter1 = _val;
      this.someComplexBusinessLogicSetValuesParameter2 = currentThreshold;
      this.simOtherContract.setValues(this.someComplexBusinessLogicSetValuesParameter1, this.someComplexBusinessLogicSetValuesParameter2);
      setLocalVal(currentThreshold);
    }
    else {
      this.someComplexBusinessLogicIfTrue = false;
      this.someComplexBusinessLogicSetValParameter = BigInteger.ONE;
      setValRemote(this.someComplexBusinessLogicSetValParameter);
      setLocalVal(_val);
    }
  }

  public void setValRemote(BigInteger _val) {
    this.simOtherContract.setVal(_val);
  }

  public void getRemoteVal() {
    this.val = this.simOtherContract.getVal();
  }

  public void setLocalVal(BigInteger _val) {
    this.val = _val;
  }

  public BigInteger getLocalVal() {
    return this.val;
  }
}
