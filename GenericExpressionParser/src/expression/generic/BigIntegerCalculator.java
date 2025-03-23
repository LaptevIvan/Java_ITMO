package expression.generic;

import java.math.BigInteger;

/**
 * @author LaptevIvan
 */
public class BigIntegerCalculator implements GenericCalculate<BigInteger> {
    @Override
    public BigInteger add(BigInteger val1, BigInteger val2) {
        return val1.add(val2);
    }

    @Override
    public BigInteger substract(BigInteger val1, BigInteger val2) {
        return val1.subtract(val2);
    }

    @Override
    public BigInteger multiply(BigInteger val1, BigInteger val2) {
        return val1.multiply(val2);
    }

    @Override
    public BigInteger divide(BigInteger val1, BigInteger val2) {
        return val1.divide(val2);
    }

    @Override
    public BigInteger unaryMinus(BigInteger val) {
        return val.multiply(new BigInteger("-1"));
    }

    @Override
    public BigInteger intConversion(int val) {
        return new BigInteger(Integer.toString(val));
    }
}
