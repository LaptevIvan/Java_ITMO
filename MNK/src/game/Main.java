package game;

import java.util.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        Map<Integer, String> typeOfPlayers = new TreeMap<>(Map.of(
                1,"HumanPlayer",
                2,"SequentialPlayer",
                3,"RandomPlayer"
        ));
        while (true) {
            try {
                SafetyScanner sc = new SafetyScanner();
                System.out.println("Settings of game\r\nCount of row");
                int row = Integer.parseInt(sc.next());
                System.out.println("Count of column");
                int col = Integer.parseInt(sc.next());
                System.out.println("Number of repetitions");
                int toWin = Integer.parseInt(sc.next());
                TicTacToeBoard board = new TicTacToeBoard(row, col, toWin);

                System.out.println("Would you play simple game one by one (input 0) or olympic tourney(input else)?");
                String variant = sc.next();
                int countOfPlayer = 2;
                Map<Integer, Player> players = new HashMap<>();
                if (!variant.equals("0")) {
                    System.out.println("How many players will take part in the tournament?");
                    countOfPlayer = Integer.parseInt(sc.next());
                    if (countOfPlayer < 1) {
                        throw new RuntimeException();
                    }
                }
                System.out.println("Input\t\t\tValue");
                for (Map.Entry<Integer, String> typeOfPlayer : typeOfPlayers.entrySet()) {
                    System.out.println(typeOfPlayer.getKey() + "\t\t\t" + typeOfPlayer.getValue());
                }
                Player newPlayer = null;
                for (int i = 1; i < countOfPlayer + 1; i++) {
                    System.out.println("Input " + i + " player:");
                    switch (Integer.parseInt(sc.next())) {
                        case 1:
                            newPlayer = new HumanPlayer();
                            break;
                        case 2:
                            newPlayer = new SequentialPlayer();
                            break;
                        case 3:
                            newPlayer = new RandomPlayer();
                            break;
                        default:
                            throw new RuntimeException();
                    }
                    players.put(i, newPlayer);
                }
                System.out.println("Do you want to see logs during gameplay? (n - no, else - yes)");
                boolean log = !sc.next().equals("n") ? true : false;
                if (variant.equals("0")) {
                    Game game = new Game(log, players.get(1), players.get(2));
                    System.out.println(game.play(board, true));
                } else {
                    Olimpic superGame = new Olimpic(log, players, board);
                    superGame.play();
                }
                break;
            } catch (NoSuchElementException closedInputStream) {
                System.out.println("Early termination of the game");
                break;
            } catch (RuntimeException otherError) {
                System.out.println("Incorrect input. Try again");
            }
        }
    }
}

