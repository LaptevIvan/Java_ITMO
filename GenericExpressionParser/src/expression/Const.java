package expression;

import java.util.List;
import java.util.Objects;

/**
 * @author LaptevIvan
 */
public class Const implements Exp {
    private final int value;
    private int depth;

    public Const(final int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const that = (Const) obj;
            if (this.value == that.value) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int evaluate(int var) {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public String toMiniString() {
        return Integer.toString(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value;
    }

    @Override
    public int evaluate(List<Integer> values) {
        return value;
    }
}

