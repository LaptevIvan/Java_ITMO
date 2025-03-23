package expression.generic;

import java.util.Objects;

/**
 * @author LaptevIvan
 */
public class GenericConst<T extends Number> implements GenericExpression<T> {
    private final T value;

    public GenericConst(final T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        GenericConst<? extends Number> that = (GenericConst<? extends Number>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public T superEvaluate(T x, T y, T z, GenericCalculate<T> calc) {
        return value;
    }
}
