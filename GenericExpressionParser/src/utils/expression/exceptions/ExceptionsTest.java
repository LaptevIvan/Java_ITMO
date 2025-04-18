package utils.expression.exceptions;


import expression.exceptions.ExpressionParser;
import utils.base.Selector;
import utils.expression.ListExpression;
import utils.expression.TripleExpression;

import static utils.expression.parser.Operations.*;


/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class ExceptionsTest {
    private static final ExpressionParser PARSER = new ExpressionParser();
    private static final Operation TRIPLE = kind(TripleExpression.KIND, (expr, variables) -> PARSER.parse(expr));
    private static final Operation LIST = kind(ListExpression.KIND, PARSER::parse);

    private static final Operation PARENS = tester -> tester.parens("{", "}", "[", "]");


    public static final Selector SELECTOR = Selector.composite(ExceptionsTest.class, ExceptionsTester::new, "easy", "hard")
            .variant("Base", TRIPLE, ADD, SUBTRACT, MULTIPLY, DIVIDE, NEGATE)
            .variant("MinMax", MIN, MAX)
            .variant("Shifts", SHIFT_L, SHIFT_R, SHIFT_A)
            .variant("Parens", PARENS)
            .variant("List", LIST)
            .variant("Zeroes", L_ZEROES, T_ZEROES)
            .variant("PowLog2", CHECKED_POW_2, CHECKED_LOG_2)
            .selector();

    private ExceptionsTest() {
    }

    public static void main(final String... args) {
        SELECTOR.main(args);
    }
}
