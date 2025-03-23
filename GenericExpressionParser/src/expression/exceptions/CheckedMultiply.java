package expression.exceptions;

import expression.*;

/**
 * @author LaptevIvan
 */
public class CheckedMultiply extends Operation {
    public CheckedMultiply(Exp exp1, Exp exp2) {
        super(exp1, exp2);
    }

    @Override
    protected int makeComposition() {
        int a = getVal1();
        int b = getVal2();
        if ((b < 0 && (a < Integer.MAX_VALUE / b || a > checkedDivide(Integer.MIN_VALUE, b))) ||
                (b > 0 && (a > Integer.MAX_VALUE / b || a < Integer.MIN_VALUE / b))) {
            throw new CalculationErrors("Overflow");
        }
        return a * b;
    }

    private int checkedDivide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            return Integer.MAX_VALUE;
        }
        return a / b;
    }

    @Override
    protected String getSign() {
        return "*";
    }
}