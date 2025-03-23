package expression.exceptions;

import expression.Exp;
import expression.UnaryOperation;

/**
 * @author LaptevIvan
 */
public class HighZeroBits extends UnaryOperation {

    public HighZeroBits(Exp exp) {
        super(exp);
    }

    @Override
    protected int makeComposition() {
        return Integer.numberOfLeadingZeros(getVal());
    }

    @Override
    protected String getSign() {
        return "l0";
    }
}
