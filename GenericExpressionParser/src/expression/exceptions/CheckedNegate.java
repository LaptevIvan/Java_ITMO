package expression.exceptions;

import expression.*;

/**
 * @author LaptevIvan
 */
public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(Exp exp) {
        super(exp);
    }

    @Override
    protected int makeComposition() {
        int op = getVal();
        if (op == Integer.MIN_VALUE) {
            throw new CalculationErrors("Overflow");
        }
        return -op;
    }

    @Override
    protected String getSign() {
        return "-";
    }
}