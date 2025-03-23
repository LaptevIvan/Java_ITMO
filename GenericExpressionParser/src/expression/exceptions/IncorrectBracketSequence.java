package expression.exceptions;

/**
 * @author LaptevIvan
 */
public class IncorrectBracketSequence extends ParsersException {
    public IncorrectBracketSequence(String exp) {
        super("Incorrect bracket sequence in expression\t" + exp);
    }
}
