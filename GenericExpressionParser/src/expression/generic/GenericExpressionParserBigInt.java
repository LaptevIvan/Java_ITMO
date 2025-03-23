package expression.generic;

import java.math.BigInteger;

/**
 * @author LaptevIvan
 */
public class GenericExpressionParserBigInt extends GenericExpressionParser<BigInteger> {
    public GenericExpressionParserBigInt(GenericCalculate<BigInteger> calc) {
        super(calc);
    }

    @Override
    protected BigInteger getNewConst(String newConst) {
        return new BigInteger(newConst);
    }

    @Override
    protected boolean isPartNum(char symb) {
        return Character.isDigit(symb);
    }
}
