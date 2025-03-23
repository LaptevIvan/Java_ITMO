package game;

import java.util.NoSuchElementException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final boolean log;
    private final Player player, anotherPlayer;
    private final int numPlayer, numAnotherPlayer;
    public Game(final boolean log, final Player player, int numPlayer, final Player anotherPlayer, int numAnotherPlayer) {
        this.log = log;
        this.player = player;
        this.numPlayer = numPlayer;
        this.anotherPlayer = anotherPlayer;
        this.numAnotherPlayer = numAnotherPlayer;
    }

    public Game(final boolean log, final Player player, final Player anotherPlayer) {
        this(log, player, 1, anotherPlayer, 2);
    }

    public int play(Board board, boolean firstfirst) {
        while (true) {
            if (firstfirst) {
                final int result1 = move(board, player, numPlayer);
                if (result1 != -1) {
                    return result1;
                }
            }
            firstfirst = true;
            final int result2 = move(board, anotherPlayer, numAnotherPlayer);
            if (result2 != -1) {
                return result2;
            }
        }
    }
    private int move(final Board board, final Player player, int num) {
        int num2 = num == numPlayer ? numAnotherPlayer : numPlayer;
        Result result = Result.UNKNOWN;
        boolean Flag = true;
        while (Flag) {
            try {
                final Move move = player.move(board.getPosition());
                result = board.makeMove(move);
                Flag = false;
                log("Player " + num + " move: " + move);
                log("Position:\n" + board.getPosition());
            }catch (NoSuchElementException closedInputStream) {
                Flag = false;
                result = Result.THWARTED;
                break;
            } catch (RuntimeException error) {
                if (!(player instanceof HumanPlayer)) {
                    Flag = false;
                    log("Player " + num + " made an unacceptable move and lost");
                    result = Result.LOSE;
                } else {
                    System.out.println("The move is invalid, try again");
                }
            }
        }

        switch (result) {
            case WIN:
                log(num + " player has won game");
                return num;

            case LOSE:
                log(num + " player has won game");
                return num2;

            case DRAW:
                log("Draw");
                return 0;

            case THWARTED:
                log("The game is thwarted by player number " + num + ". Technical victory awarded to the player " + num2);
                return -num2 - 1;

            default:
                return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}

