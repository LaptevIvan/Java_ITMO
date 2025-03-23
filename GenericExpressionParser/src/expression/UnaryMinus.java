package expression;

/**
 * @author LaptevIvan
 */
public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(Exp exp) {
        super(exp);
    }

    @Override
    protected int makeComposition() {
        return -getVal();
    }

    @Override
    protected String getSign() {
        return "-";
    }
}