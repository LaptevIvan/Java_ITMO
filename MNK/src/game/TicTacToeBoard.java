package game;

import java.util.Arrays;
import java.util.Map;

import static java.lang.Math.*;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TicTacToeBoard implements Board, IsValid {
    private final Cell[][] cells;
    private Cell turn;
    private int empty;
    private final int toWin;

    public TicTacToeBoard(int m, int n, int k) {
        if (m * n * k <= 0 || k > max(n, m) || (2 * k > m * n && k != 1)) {
            throw new IllegalArgumentException();
        }
        this.cells = new Cell[m][n];
        empty = m * n;
        toWin = k;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new Position() {
            private static final Map<Cell, Character> SYMBOLS = Map.of(
                    Cell.X, 'X',
                    Cell.O, 'O',
                    Cell.E, '.'
            );
            @Override
            public int getCntRow() {
                return cells.length;
            }

            @Override
            public int getCntCol() {
                return cells[0].length;
            }

            @Override
            public Cell getTurn() {
                return turn;
            }

            @Override
            public Cell getCell(int r, int c) {
                return cells[r][c];
            }

            @Override
            public boolean isValid(Move move) {
                return 0 <= move.getRow() && move.getRow() < cells.length
                        && 0 <= move.getColumn() && move.getColumn() < cells[0].length
                        && getCell(move.getRow(), move.getColumn()) == Cell.E
                        && move.getValue() == turn;
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder(" ");
                for (int i = 0; i < cells[0].length; i++) {
                    sb.append(i).append(" ");
                }
                for (int r = 0; r < cells.length; r++) {
                    sb.append("\n");
                    sb.append(r);
                    for (int c = 0; c < cells[0].length; c++) {
                        sb.append(SYMBOLS.get(cells[r][c])).append(" ".repeat(Integer.toString(c).length()));
                    }
                    sb.append("\n");
                }
                return sb.toString();
            }
        };
    }

    private boolean winTimeInRow(int r, int c, int inc1, int inc2, final Move move) {
        int cnt = 0;
        boolean callPoint = false;
        for (int i = 0; i < 2 * toWin - 1; i++) {
            if (-1 < r && r < cells.length && -1 < c && c < cells[0].length) {
                callPoint = r == move.getRow() && c == move.getColumn()? true : false;
                if (cells[r][c] == turn) {
                    cnt++;
                    if (cnt == toWin) {
                        return true;
                    }
                } else {
                    if (callPoint) {
                        break;
                    }
                    cnt = 0;
                }
            } else if (callPoint) {
                break;
            }
            r += inc1;
            c += inc2;
        }
        return false;
    }
    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            throw new RuntimeException();
        }
        int rowMove = move.getRow();
        int columnMove = move.getColumn();
        cells[rowMove][columnMove] = move.getValue();
        if (winTimeInRow(max(0, rowMove - (toWin - 1)), columnMove, 1, 0, move) ||
            winTimeInRow(rowMove - (toWin - 1), columnMove - (toWin - 1), 1, 1, move) ||
            winTimeInRow(rowMove, max(0, columnMove - (toWin - 1)), 0, 1, move) ||
            winTimeInRow(rowMove + toWin - 1, columnMove - (toWin - 1), -1, 1, move)) {
            return Result.WIN;
        }
        if (--empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }
    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < cells.length
                && 0 <= move.getColumn() && move.getColumn() < cells[0].length
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && move.getValue() == turn;
    }

    @Override
    public Object clone() {
        return new TicTacToeBoard(cells.length, cells[0].length, toWin);
    }
}

