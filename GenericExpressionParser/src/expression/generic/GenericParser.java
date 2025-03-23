package expression.generic;

import expression.exceptions.ParsersException;

/**
 * @author LaptevIvan
 */
public interface GenericParser<T extends Number> {
    GenericExpression<T> parse(String expression) throws ParsersException;

    GenericCalculate<T> getCalc();
}
