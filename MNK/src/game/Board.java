package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Board extends Cloneable {
    Position getPosition();
    Result makeMove(Move move);
    Object clone();
}
