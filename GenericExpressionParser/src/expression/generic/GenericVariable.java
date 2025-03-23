package expression.generic;

import java.util.Objects;

/**
 * @author LaptevIvan
 */
public class GenericVariable<T extends Number> implements GenericExpression<T> {
    private final String strVar;

    public GenericVariable(final String name) {
        strVar = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(o.getClass())) return false;
        GenericVariable<? extends Number> that = (GenericVariable<? extends Number>) o;
        return Objects.equals(strVar, that.strVar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), strVar);
    }

    @Override
    public String toString() {
        return strVar;
    }

    @Override
    public T superEvaluate(T x, T y, T z, GenericCalculate<T> calc) {
        return switch (strVar) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> null;
        };
    }
}
