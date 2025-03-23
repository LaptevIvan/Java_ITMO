package expression.generic;

/**
 * @author LaptevIvan
 */
public class NotCheckedIntCalculator implements GenericCalculate<Integer> {
    @Override
    public Integer add(Integer val1, Integer val2) {
        return val1 + val2;
    }

    @Override
    public Integer substract(Integer val1, Integer val2) {
        return val1 - val2;
    }

    @Override
    public Integer multiply(Integer val1, Integer val2) {
        return val1 * val2;
    }

    @Override
    public Integer divide(Integer val1, Integer val2) {
        return val1 / val2;
    }

    @Override
    public Integer unaryMinus(Integer val) {
        return -val;
    }

    @Override
    public Integer intConversion(int val) {
        return val;
    }
}
