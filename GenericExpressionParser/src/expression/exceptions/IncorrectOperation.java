package expression.exceptions;

/**
 * @author LaptevIvan
 */
public class IncorrectOperation extends ParsersException {
    public IncorrectOperation(String expression) {
        super("Incorrect operation in expression\t" + expression);
    }
}
