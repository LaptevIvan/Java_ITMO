package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericDivide<T extends Number> extends GenericOperation<T> {

    public GenericDivide(GenericExpression<T> exp1, GenericExpression<T> exp2) {
        super(exp1, exp2);
    }

    @Override
    protected T makeGenericComposition(T val1, T val2, GenericCalculate<T> calc) {
        return calc.divide(val1, val2);
    }

    @Override
    protected String getSign() {
        return "/";
    }
}

