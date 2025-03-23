package expression;

import java.util.List;

/**
 * @author LaptevIvan
 */
public abstract class UnaryOperation implements Exp {
    private final Exp exp;
    private int val;

    public UnaryOperation(Exp exp) {
        this.exp = exp;
    }

    @Override
    public int evaluate(int x) {
        val = exp.evaluate(x);
        return makeComposition();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        val = exp.evaluate(x, y, z);
        return makeComposition();
    }

    @Override
    public int evaluate(List<Integer> values) {
        val = exp.evaluate(values);
        return makeComposition();
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return getSign() + "(" + exp + ")";
    }

    protected abstract int makeComposition();

    protected abstract String getSign();
}
