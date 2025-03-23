package expression.generic;

import expression.exceptions.ParsersException;
import utils.expression.generic.Tabulator;

/**
 * @author LaptevIvan
 */
public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParsersException {
        return myTabulator(getParser(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] myTabulator(GenericParser<T> parser, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParsersException {
        GenericExpression<T> exp = parser.parse(expression);
        GenericCalculate<T> calc = parser.getCalc();
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Object[][][] ans = new Object[x][y][z];
        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                for (int k = 0; k < z; ++k) {
                    try {
                        ans[i][j][k] = exp.superEvaluate(calc.intConversion(x1 + i), calc.intConversion(y1 + j), calc.intConversion(z1 + k), calc);
                    } catch (ArithmeticException e) {
                        ans[i][j][k] = null;
                    }
                }
            }
        }
        return ans;
    }

    public GenericParser<? extends Number> getParser(String mode) {
        return switch (mode) {
            case "i" -> new GenericExpressionParserInt(new IntegerCalculator());
            case "d" -> new GenericExpressionParserDouble(new DoubleCalculator());
            case "bi" -> new GenericExpressionParserBigInt(new BigIntegerCalculator());
            case "u" -> new GenericExpressionParserInt(new NotCheckedIntCalculator());
            case "b" -> new GenericExpressionParserByte(new ByteCalculator());
            default -> null;
        };
    }
}
