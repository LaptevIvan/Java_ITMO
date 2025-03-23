package expression;

import java.util.List;
import java.util.Objects;

/**
 * @author LaptevIvan
 */
public class Variable implements Exp {
    private final int var;
    private final String strVar;

    public Variable(final String name) {
        strVar = name;
        switch (name) {
            case "x" -> this.var = 0;
            case "y" -> this.var = 1;
            case "z" -> this.var = 2;
            default -> this.var = -1;
        }
    }

    public Variable(int num) {
        this.strVar = "$" + num;
        this.var = num;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable that = (Variable) obj;
            return this.var == that.var;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(var, strVar);
    }

    @Override
    public int evaluate(int valueVar) {
        return var == 0 ? valueVar : 0;
    }

    @Override
    public String toString() {
        return strVar;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (var) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> 0;
        };
    }

    @Override
    public int evaluate(List<Integer> values) {
        return values.get(var);
    }
}
