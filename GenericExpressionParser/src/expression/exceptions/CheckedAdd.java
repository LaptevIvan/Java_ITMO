package expression.exceptions;

import expression.*;

/**
 * @author LaptevIvan
 */
public class CheckedAdd extends Operation {
    public CheckedAdd(Exp exp1, Exp exp2) {
        super(exp1, exp2);
    }

    @Override
    protected int makeComposition() {
        int a = getVal1();
        int b = getVal2();
        if ((b > 0 && a > Integer.MAX_VALUE - b) || (b < 0 && a < Integer.MIN_VALUE - b)) {
            throw new CalculationErrors("Overflow");
        }
        return a + b;
    }

    @Override
    protected String getSign() {
        return "+";
    }
}