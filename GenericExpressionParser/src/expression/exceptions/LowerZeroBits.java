package expression.exceptions;

import expression.Exp;
import expression.UnaryOperation;

/**
 * @author LaptevIvan
 */
public class LowerZeroBits extends UnaryOperation {

    public LowerZeroBits(Exp exp) {
        super(exp);
    }

    @Override
    protected int makeComposition() {
        return Integer.numberOfTrailingZeros(getVal());
    }

    @Override
    protected String getSign() {
        return "t0";
    }
}
