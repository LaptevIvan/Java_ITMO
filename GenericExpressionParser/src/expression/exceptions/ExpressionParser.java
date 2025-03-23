package expression.exceptions;


import expression.*;
import utils.expression.ListExpression;
import utils.expression.TripleExpression;
import utils.expression.exceptions.ListParser;
import utils.expression.exceptions.TripleParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author LaptevIvan
 */
public class ExpressionParser implements TripleParser, ListParser {
    private StringBuilder expression;
    private int balance;
    private int current;
    private int exceptionCurrent;
    private Map<String, Integer> variable;
    private String nextOp;
    private static final Map<Integer, Set<String>> priorities = Map.of(
            1, Set.of("*", "/"),
            2, Set.of("+", "-"),
            3, Set.of("|"),
            4, Set.of("^"),
            5, Set.of("&")
    );
    private static final int MIN_PRIORITY = priorities.size();
    private final Set<String> operations = Set.of("|", "&", "^", "+", "-", "*", "/");
    private final Set<Character> breakSymbols = Set.of('(', ')', '+', '-', '/', '*');
    private final Set<String> operationsUnary = Set.of("t0", "l0");
    private boolean variableName;

    @Override
    public TripleExpression parse(String expression) throws Exception {
        variableName = true;
        initialization(expression, List.of("x", "y", "z"));
        TripleExpression ans = parseBinaryOperation(MIN_PRIORITY);
        if (notIsEnd()) {
            throw new IncorrectOperation(getExpError());
        } else {
            return ans;
        }
    }

    @Override
    public ListExpression parse(String expression, List<String> variables) throws Exception {
        variableName = false;
        initialization(expression, variables);
        ListExpression ans = parseBinaryOperation(MIN_PRIORITY);
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
        if (var != null) {
            variable = new HashMap<>();
            for (int i = 0; i < var.size(); ++i) {
                variable.put(var.get(i), i);
            }
        }
    }

    private Exp parseBinaryOperation(int priority) throws ParsersException {
        if (priority == 0) {
            return parseEl();
        }
        Exp left = parseBinaryOperation(priority - 1);
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

    private Exp createExpression(String oper, Exp op1, Exp op2) throws ParsersException {
        return switch (oper) {
            case "t0" -> new LowerZeroBits(op1);
            case "l0" -> new HighZeroBits(op1);
            case "|" -> new bitOr(op1, op2);
            case "^" -> new bitXor(op1, op2);
            case "&" -> new bitAnd(op1, op2);
            case "+" -> new CheckedAdd(op1, op2);
            case "-" -> new CheckedSubtract(op1, op2);
            case "*" -> new CheckedMultiply(op1, op2);
            case "/" -> new CheckedDivide(op1, op2);
            default -> throw new IncorrectOperation("Unexpected value: " + oper);
        };
    }

    private boolean notIsEnd() {
        while (current < expression.length() && Character.isWhitespace(expression.charAt(current))) {
            ++current;
        }
        exceptionCurrent = current;
        return current != expression.length();
    }

    private Exp parseEl() throws ParsersException {
        if (notIsEnd()) {
            char symb = expression.charAt(current++);
            if (symb == '(') {
                String last = nextOp;
                nextOp = "";
                ++balance;
                ++exceptionCurrent;
                Exp el = parseBinaryOperation(MIN_PRIORITY);
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
                return new CheckedNegate(parseEl());
            } else {
                Exp e = parseVariable(symb);
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
        throw new IncorrectOperand(getExpError());
    }

    private Exp parseVariable(char symb) {
        StringBuilder nextTocken = new StringBuilder(Character.toString(symb));
        while (current < expression.length() && !Character.isWhitespace(expression.charAt(current)) && !breakSymbols.contains(expression.charAt(current))) {
            nextTocken.append(expression.charAt(current++));
        }
        if (variable.containsKey(nextTocken.toString())) {
            return variableName ? new Variable(nextTocken.toString()) : new Variable(variable.get(nextTocken.toString()));
        }
        nextOp = nextTocken.toString();
        return null;
    }

    private int expectedLength() {
        if (current == expression.length()) {
            return 0;
        } else if ("+-*/&^|".contains(Character.toString(expression.charAt(current)))) {
            return 1;
        }
        return -1;
    }

    private String nextOperation() throws IncorrectOperation {
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

    private Exp parseNum(char firstSymb) throws ParsersException {
        int end = current;
        while (end < expression.length() && Character.isDigit(expression.charAt(end))) {
            ++end;
        }
        String newConst = firstSymb + expression.substring(current, end);
        try {
            int intNewConst = Integer.parseInt(newConst);
            current = end;
            exceptionCurrent = current;
            return new Const(intNewConst);
        } catch (NumberFormatException error) {
            throw new IncorrectOperand(getExpError());
        }
    }

    private String getExpError() {
        expression.insert(exceptionCurrent, " --> ");
        return expression.toString();
    }
}