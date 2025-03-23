package expression.generic;

import expression.exceptions.CalculationErrors;

/**
 * @author LaptevIvan
 */
public class IntegerCalculator implements GenericCalculate<Integer> {
    @Override
    public Integer add(Integer val1, Integer val2) {
        if ((val2 > 0 && val1 > Integer.MAX_VALUE - val2) || (val2 < 0 && val1 < Integer.MIN_VALUE - val2)) {
            throw new CalculationErrors("Overflow");
        }
        return val1 + val2;
    }

    @Override
    public Integer substract(Integer val1, Integer val2) {
        if ((val2 > 0 && val1 < Integer.MIN_VALUE + val2) || (val2 < 0 && val1 > Integer.MAX_VALUE + val2)) {
            throw new CalculationErrors("Overflow");
        }
        return val1 - val2;
    }

    @Override
    public Integer multiply(Integer val1, Integer val2) {
        if ((val2 < 0 && (val1 < Integer.MAX_VALUE / val2 || val1 > checkedDivide(Integer.MIN_VALUE, val2))) ||
                (val2 > 0 && (val1 > Integer.MAX_VALUE / val2 || val1 < Integer.MIN_VALUE / val2))) {
            throw new CalculationErrors("Overflow");
        }
        return val1 * val2;
    }

    private int checkedDivide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            return Integer.MAX_VALUE;
        }
        return a / b;
    }

    @Override
    public Integer divide(Integer val1, Integer val2) {
        if (val2 == 0) {
            throw new CalculationErrors("Division by zero");
        }
        if (val1 == Integer.MIN_VALUE && val2 == -1) {
            throw new CalculationErrors("Overflow");
        }
        return val1 / val2;
    }

    @Override
    public Integer unaryMinus(Integer val) {
        if (val == Integer.MIN_VALUE) {
            throw new CalculationErrors("Overflow");
        }
        return -val;
    }

    @Override
    public Integer intConversion(int val) {
        return val;
    }
}
