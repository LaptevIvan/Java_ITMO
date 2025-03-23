package game;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position) {
        while (true) {
            int r = random.nextInt(position.getCntRow());
            int c = random.nextInt(position.getCntCol());
            final Move move = new Move(r, c, position.getTurn());
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
