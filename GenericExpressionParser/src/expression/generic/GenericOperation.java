package expression.generic;

import java.util.Objects;

/**
 * @author LaptevIvan
 */
public abstract class GenericOperation<T extends Number> implements GenericExpression<T> {
    private final GenericExpression<T> exp1, exp2;

    public GenericOperation(GenericExpression<T> exp1, GenericExpression<T> exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public final T superEvaluate(T x, T y, T z, GenericCalculate<T> calc) {
        return makeGenericComposition(exp1.superEvaluate(x, y, z, calc), exp2.superEvaluate(x, y, z, calc), calc);
    }

    @Override
    public String toString() {
        return "(" + exp1 + " " + getSign() + " " + exp2 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        GenericOperation<? extends Number> that = (GenericOperation<? extends Number>) o;
        return Objects.equals(exp1, that.exp1) && Objects.equals(exp2, that.exp2) && Objects.equals(getSign(), that.getSign());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), exp1, exp2, getSign());
    }

    protected abstract T makeGenericComposition(T val1, T val2, GenericCalculate<T> calc);

    protected abstract String getSign();
}
