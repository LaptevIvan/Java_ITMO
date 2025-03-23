package expression.generic;

import java.util.Objects;

/**
 * @author LaptevIvan
 */
public abstract class GenericUnaryOperation<T extends Number> implements GenericExpression<T> {
    private final GenericExpression<T> exp;

    public GenericUnaryOperation(GenericExpression<T> exp) {
        this.exp = exp;
    }

    @Override
    public final T superEvaluate(T x, T y, T z, final GenericCalculate<T> calc) {
        return makeGenericComposition(exp.superEvaluate(x, y, z, calc), calc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        GenericUnaryOperation<? extends Number> that = (GenericUnaryOperation<? extends Number>) o;
        return Objects.equals(exp, that.exp) && Objects.equals(getSign(), that.getSign());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), exp, getSign());
    }

    @Override
    public String toString() {
        return getSign() + "(" + exp + ")";
    }

    protected abstract T makeGenericComposition(T val, GenericCalculate<T> calc);

    protected abstract String getSign();
}
