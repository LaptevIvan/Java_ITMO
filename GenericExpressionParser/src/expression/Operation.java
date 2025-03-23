package expression;

import java.util.List;
import java.util.Objects;

/**
 * @author LaptevIvan
 */
public abstract class Operation implements Exp {
    private final Exp exp1, exp2;
    private int val1, val2;

    public Operation(Exp exp1, Exp exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public final int evaluate(int valueVar) {
        val1 = exp1.evaluate(valueVar);
        val2 = exp2.evaluate(valueVar);
        return makeComposition();
    }

    @Override
    public final int evaluate(int x, int y, int z) {
        val1 = exp1.evaluate(x, y, z);
        val2 = exp2.evaluate(x, y, z);
        return makeComposition();
    }

    @Override
    public final int evaluate(List<Integer> values) {
        val1 = exp1.evaluate(values);
        val2 = exp2.evaluate(values);
        return makeComposition();
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    @Override
    public String toString() {
        return "(" + exp1 + " " + getSign() + " " + exp2 + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operation) {
            Operation that = (Operation) obj;
            return obj.getClass().equals(this.getClass()) && exp1.equals(that.exp1) && exp2.equals(that.exp2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, exp2, getSign());
    }

    protected abstract int makeComposition();

    protected abstract String getSign();
}
