package expression;

/**
 * @author LaptevIvan
 */
public class bitOr extends Operation {
    public bitOr(Exp exp1, Exp exp2) {
        super(exp1, exp2);
    }

    @Override
    protected int makeComposition() {
        return getVal1() | getVal2();
    }

    @Override
    protected String getSign() {
        return "|";
    }
}