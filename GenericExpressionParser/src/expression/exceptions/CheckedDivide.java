package expression.exceptions;

import expression.*;

/**
 * @author LaptevIvan
 */
public class CheckedDivide extends Operation {
    public CheckedDivide(Exp exp1, Exp exp2) {
        super(exp1, exp2);
    }

    @Override
    protected int makeComposition() {
        int a = getVal1();
        int b = getVal2();
        if (b == 0) {
            throw new CalculationErrors("Division by zero");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new CalculationErrors("Overflow");
        }
        return a / b;
    }

    @Override
    protected String getSign() {
        return "/";
    }
}