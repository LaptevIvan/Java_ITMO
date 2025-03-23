package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position) {
//        Board board = (Board) position; // Теперь это не сработает
//        Move mv = new Move(0,0, position.getTurn());
//        board.makeMove(mv);
        for (int r = 0; r < position.getCntRow(); r++) {
            for (int c = 0; c < position.getCntCol(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
