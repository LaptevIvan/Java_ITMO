package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericExpressionParserInt extends GenericExpressionParser<Integer> {
    public GenericExpressionParserInt(GenericCalculate<Integer> calc) {
        super(calc);
    }

    @Override
    protected Integer getNewConst(String newConst) {
        return Integer.parseInt(newConst);
    }

    @Override
    protected boolean isPartNum(char symb) {
        return Character.isDigit(symb);
    }
}
