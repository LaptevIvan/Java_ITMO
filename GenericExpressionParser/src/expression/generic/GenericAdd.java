package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericAdd<T extends Number> extends GenericOperation<T> {
    public GenericAdd(GenericExpression<T> exp1, GenericExpression<T> exp2) {
        super(exp1, exp2);
    }

    @Override
    protected T makeGenericComposition(T val1, T val2, GenericCalculate<T> calc) {
        return calc.add(val1, val2);
    }

    @Override
    protected String getSign() {
        return "+";
    }
}
