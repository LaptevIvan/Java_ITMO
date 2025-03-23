package expression.generic;

/**
 * @author LaptevIvan
 */
public class DoubleCalculator implements GenericCalculate<Double> {
    @Override
    public Double add(Double val1, Double val2) {
        return val1 + val2;
    }

    @Override
    public Double substract(Double val1, Double val2) {
        return val1 - val2;
    }

    @Override
    public Double multiply(Double val1, Double val2) {
        return val1 * val2;
    }

    @Override
    public Double divide(Double val1, Double val2) {
        return val1 / val2;
    }

    @Override
    public Double unaryMinus(Double val) {
        return -val;
    }

    @Override
    public Double intConversion(int val) {
        return (double) val;
    }
}
