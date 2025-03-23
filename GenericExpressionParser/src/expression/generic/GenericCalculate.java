package expression.generic;

/**
 * @author LaptevIvan
 */
public interface GenericCalculate<T extends Number> {
    T add(T val1, T val2);

    T substract(T val1, T val2);

    T multiply(T val1, T val2);

    T divide(T val1, T val2);

    T unaryMinus(T val);

    T intConversion(int val);
}
