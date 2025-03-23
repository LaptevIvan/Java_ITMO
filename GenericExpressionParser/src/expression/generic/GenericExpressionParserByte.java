package expression.generic;

/**
 * @author LaptevIvan
 */
public class GenericExpressionParserByte extends GenericExpressionParser<Byte> {
    public GenericExpressionParserByte(GenericCalculate<Byte> calc) {
        super(calc);
    }

    @Override
    protected Byte getNewConst(String newConst) {
        return Byte.parseByte(newConst);
    }

    @Override
    protected boolean isPartNum(char symb) {
        return Character.isDigit(symb);
    }
}
