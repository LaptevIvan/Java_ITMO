package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Position extends IsValid {

    int getCntRow();
    int getCntCol();

    Cell getTurn();

    Cell getCell(int r, int c);
}
