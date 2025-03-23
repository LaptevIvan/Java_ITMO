package expression.exceptions;

/**
 * @author LaptevIvan
 */
public class IncorrectOperand extends ParsersException {
    public IncorrectOperand(String expression) {
        super("Incorrect operand for operation\t" + expression);
    }
}
