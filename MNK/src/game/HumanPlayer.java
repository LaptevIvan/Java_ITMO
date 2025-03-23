package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final SafetyScanner in;

    public HumanPlayer() {
        in = new SafetyScanner();
    }

    @Override
    public Move move(final Position position) {
        System.out.println("Position");
        System.out.println(position);
        System.out.println(position.getTurn() + "'s move");
        System.out.println("Enter row");
        int r = Integer.parseInt(in.next());
        System.out.println("Enter column");
        int c = Integer.parseInt(in.next());
        final Move move = new Move(r, c, position.getTurn());
        return move;
    }
}

