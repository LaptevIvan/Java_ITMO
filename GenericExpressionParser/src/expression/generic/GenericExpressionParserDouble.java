package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericExpressionParserDouble extends GenericExpressionParser<Double> {
    public GenericExpressionParserDouble(GenericCalculate<Double> calc) {
        super(calc);
    }

    @Override
    protected Double getNewConst(String newConst) {
        return Double.parseDouble(newConst);
    }

    @Override
    protected boolean isPartNum(char symb) {
        return Character.isDigit(symb) || ".-+eE".indexOf(symb) != -1;
    }
}
