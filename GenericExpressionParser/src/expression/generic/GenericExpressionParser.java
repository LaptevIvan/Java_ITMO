package expression.generic;

import expression.exceptions.IncorrectBracketSequence;
import expression.exceptions.IncorrectOperand;
import expression.exceptions.IncorrectOperation;
import expression.exceptions.ParsersException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LaptevIvan
 */
public abstract class GenericExpressionParser<T extends Number> implements GenericParser<T> {
    private StringBuilder expression;
    private int balance;
    private int current;
    private int exceptionCurrent;
    private Map<String, Integer> variable;
    private String nextOp;
    private static final Map<Integer, Set<String>> priorities = Map.of(
            1, Set.of("*", "/"),
            2, Set.of("+", "-")
    );
    private static final int MIN_PRIORITY = priorities.size();
    private final Set<String> operations = Set.of("+", "-", "*", "/");
    private final Set<Character> breakSymbols = Set.of('(', ')', '+', '-', '/', '*');
    private final Set<String> operationsUnary = Set.of("");

    private final GenericCalculate<T> calc;

    public GenericExpressionParser(GenericCalculate<T> calc) {
        this.calc = calc;
    }

    public GenericExpression<T> parse(String expression) throws ParsersException {
        initialization(expression, List.of("x", "y", "z"));
        GenericExpression<T> ans = parseBinaryOperation(MIN_PRIORITY);
        if (notIsEnd()) {
            throw new IncorrectOperation(getExpError());
        } else {
            return ans;
        }
    }

    private void initialization(String expression, List<String> var) {
        this.nextOp = "";
        this.balance = 0;
        this.current = 0;
        this.exceptionCurrent = 0;
        this.expression = new StringBuilder(expression);
        this.variable = new HashMap<>();
        for (int i = 0; i < var.size(); ++i) {
            this.variable.put(var.get(i), i);
        }
    }

    private GenericExpression<T> parseBinaryOperation(int priority) throws ParsersException {
        if (priority == 0) {
            return parseEl();
        }
        GenericExpression<T> left = parseBinaryOperation(priority - 1);
        Set<String> currentOperations = priorities.get(priority);
        while (notIsEnd() && currentOperations.contains(nextOperation())) {
            current += nextOp.length();
            exceptionCurrent = current;
            String copy = nextOp;
            nextOp = "";
            left = createExpression(copy, left, parseBinaryOperation(priority - 1));
        }
        return left;
    }

    protected GenericExpression<T> createExpression(String oper, GenericExpression<T> op1, GenericExpression<T> op2) throws ParsersException {
        return switch (oper) {
            case "+" -> new GenericAdd<>(op1, op2);
            case "-" -> new GenericSubtract<>(op1, op2);
            case "*" -> new GenericMultiply<>(op1, op2);
            case "/" -> new GenericDivide<>(op1, op2);
            default -> throw new IncorrectOperation("Unexpected value: " + oper);
        };
    }

    protected boolean notIsEnd() {
        while (current < expression.length() && Character.isWhitespace(expression.charAt(current))) {
            ++current;
        }
        exceptionCurrent = current;
        return current != expression.length();
    }

    private GenericExpression<T> parseEl() throws ParsersException {
        if (notIsEnd()) {
            char symb = expression.charAt(current++);
            if (symb == '(') {
                String last = nextOp;
                nextOp = "";
                ++balance;
                ++exceptionCurrent;
                GenericExpression<T> el = parseBinaryOperation(MIN_PRIORITY);
                if (current == expression.length() || expression.charAt(current) != ')') {
                    throw new IncorrectBracketSequence(getExpError());
                }
                ++current;
                nextOp = last;
                --balance;
                exceptionCurrent = current;
                return el;
            } else if (Character.isDigit(symb)) {
                return parseNum(symb);
            } else if (symb == '-') {
                if (current == expression.length()) {
                    throw new IncorrectOperand(getExpError());
                }
                if (Character.isDigit(expression.charAt(current))) {
                    return parseNum(symb);
                }
                ++exceptionCurrent;
                return new GenericUnaryMinus<>(parseEl());
            } else {
                GenericExpression<T> e = parseVariable(symb);
                if (e != null) {
                    return e;
                }
                if (operationsUnary.contains(nextOp)) {
                    exceptionCurrent = current;
                    String copy = nextOp;
                    nextOp = "";
                    return createExpression(copy, parseEl(), null);
                }
            }
        }
        if (balance > 0) {
            throw new IncorrectBracketSequence(getExpError());
        }
        throw new IncorrectOperand(getExpError());
    }

    protected GenericExpression<T> parseVariable(char symb) {
        StringBuilder nextTocken = new StringBuilder(Character.toString(symb));
        while (current < expression.length() && !Character.isWhitespace(expression.charAt(current)) && !breakSymbols.contains(expression.charAt(current))) {
            nextTocken.append(expression.charAt(current++));
        }
        if (variable.containsKey(nextTocken.toString())) {
            return new GenericVariable<>(nextTocken.toString());
        }
        nextOp = nextTocken.toString();
        return null;
    }

    protected int expectedLength() {
        if (current == expression.length()) {
            return 0;
        } else if ("+-*/".contains(Character.toString(expression.charAt(current)))) {
            return 1;
        }
        return -1;
    }

    protected String nextOperation() throws IncorrectOperation {
        if (nextOp.isEmpty()) {
            int length = expectedLength();
            if (length > 0) {
                nextOp = expression.substring(current, current + length);
                if (operations.contains(nextOp)) {
                    return nextOp;
                }
                throw new IncorrectOperation(getExpError());
            } else if (length == 0) {
                nextOp = "end";
                return nextOp;
            } else if (expression.charAt(current) == ')' && balance > 0) {
                nextOp = ")";
                return nextOp;
            }
            throw new IncorrectOperation(getExpError());
        }
        return nextOp;
    }

    private GenericExpression<T> parseNum(char firstSymb) throws ParsersException {
        int end = current;
        while (end < expression.length() && isPartNum(expression.charAt(end))) {
            ++end;
        }
        String newConst = firstSymb + expression.substring(current, end);
        try {
            T intNewConst = getNewConst(newConst);
            current = end;
            exceptionCurrent = current;
            return new GenericConst<>(intNewConst);
        } catch (NumberFormatException error) {
            throw new IncorrectOperand(getExpError());
        }
    }

    protected String getExpError() {
        expression.insert(exceptionCurrent, " --> ");
        return expression.toString();
    }

    @Override
    public GenericCalculate<T> getCalc() {
        return calc;
    }

    protected abstract T getNewConst(String newConst);

    protected abstract boolean isPartNum(char symb);
}
