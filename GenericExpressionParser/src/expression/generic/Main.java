package expression.generic;

/**
 * @author LaptevIvan
 */
public class Main { 
    public static void main(String[] args) throws Exception {
        GenericTabulator g = new GenericTabulator();
        Object[][][] table = g.tabulate(args[0], args[1], -2, 2, -2, 2, -2, 2);
        for (int i = 0; i <= 4; ++i) {
            for (int j = 0; j <= 4; ++j) {
                for (int k = 0; k <= 4; ++k) {
                    System.out.print(table[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
