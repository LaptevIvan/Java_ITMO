package expression.generic;

/**
 * @author LaptevIvan
 */
public interface GenericExpression<T extends Number> {
    T superEvaluate(T x, T y, T z, GenericCalculate<T> calc);
}
