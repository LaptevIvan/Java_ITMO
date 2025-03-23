package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericUnaryMinus<T extends Number> extends GenericUnaryOperation<T> {
    public GenericUnaryMinus(GenericExpression<T> exp) {
        super(exp);
    }

    @Override
    protected T makeGenericComposition(T val, GenericCalculate<T> calc) {
        return calc.unaryMinus(val);
    }

    @Override
    protected String getSign() {
        return "-";
    }
}