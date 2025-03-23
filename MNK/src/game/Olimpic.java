package game;

import java.util.*;

public class Olimpic {
    private List<List<Integer>> scores;
    private Map<Integer, Player> players;
    private List<Integer> currentPlayers;
    private List<Integer> losers;
    private int num1, num2;
    private Board board;
    private int degreeOfTwo;
    private boolean log, supLoser = false;
    private int notHasFinished;
    public Olimpic(boolean log, Map<Integer, Player> players, Board board) {
        this.log = log;
        this.players = players;
        this.currentPlayers = new ArrayList<>();
        for (int i = 1; i < players.size() + 1; i++) {
            this.currentPlayers.add(i);
        }
        this.board = board;
        this.scores = new ArrayList<>();
        this.degreeOfTwo = isDegreeOfTwo(players.size());
        this.notHasFinished = players.size();
    }
    public void play() {
        Random rnd = new Random();
        losers = new ArrayList<>();
        while (notHasFinished > 1) {
            while (currentPlayers.size() != 0) {
                num1 = currentPlayers.get(rnd.nextInt(currentPlayers.size()));
                Player currentPlayer1 = players.get(num1);
                currentPlayers.remove(currentPlayers.indexOf(num1));

                num2 = currentPlayers.get(rnd.nextInt(currentPlayers.size()));
                Player currentPlayer2 = players.get(num2);
                currentPlayers.remove(currentPlayers.indexOf(num2));

                Game game = new Game(log, currentPlayer1, num1, currentPlayer2, num2);
                int loser = unlessWin(game, true);
                if (loser > 0) {
                    losers.add(loser);
                    players.remove(loser);
                    if (degreeOfTwo == 0 && isDegreeOfTwo(losers.size() + currentPlayers.size()) == 2) {
                        endRound();
                        degreeOfTwo = 1;
                    }
                } else {
                    int superLoser = loser + 1 == -num1 ? num2 : num1;
                    players.remove(superLoser);
                    scores.add(0, List.of(superLoser));
                    supLoser = true;
                    boolean flag = false;
                    for (Map.Entry<Integer, Player> pl : players.entrySet()) {
                        if (pl.getValue() instanceof HumanPlayer) {
                            log("The Olympiad is disrupted, the player under the number is sent to the last place,\r\nall other players are distributed in places relative to the number of wins up to the present moment");
                            notHasFinished = 1;
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                notHasFinished--;
            }
            endRound();
        }
        List<Integer> first = new ArrayList<>();
        first.addAll(players.keySet());
        scores.add(first);
        for (int i = 0; i < scores.size(); i++) {
            if (supLoser) {
                supLoser = false;
                System.out.print("Disqualified) ");
            } else if (degreeOfTwo == 1) {
                degreeOfTwo = 2;
                System.out.print("did not pass the selection) ");
            } else {
                System.out.print(scores.size() - i + "th place) ");
            }
            for (int j = 0; j < scores.get(i).size(); j++) {
                System.out.print(scores.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private void endRound() {
        currentPlayers.clear();
        currentPlayers.addAll(players.keySet());
        if (!losers.isEmpty()) {
            scores.add(losers);
            losers = new ArrayList<>();
        }
    }

    private int unlessWin(Game game, boolean firstfirst) {
        int result = -1;
        while (result == 0 || result == -1) {
            result = game.play((Board) this.board.clone(), firstfirst);
            firstfirst = !firstfirst;
            log("\r\n=======================\r\n");
        }
        if (result < -1) {
            return result;
        }
        return result == num1 ? num2 : num1;
    }

    private int isDegreeOfTwo(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) ? 2 : 0;
    }
    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
