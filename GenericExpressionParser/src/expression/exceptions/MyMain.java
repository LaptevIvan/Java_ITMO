package expression.exceptions;

import utils.expression.TripleExpression;

/**
 * @author LaptevIvan
 */
public class MyMain {
    public static void main(String[] args) throws Exception {
        ExpressionParser p = new ExpressionParser();
        TripleExpression e = p.parse("1000000*x*x*x*x*x/(x-1)");
        System.out.println("x f");
        for (int x = 0; x < 11; x++) {
            System.out.print(x + " ");
            try {
                System.out.println(e.evaluate(x, 0, 0));
            } catch (CalculationErrors er) {
                System.out.println(er.getMessage());
            }
        }
    }
}

