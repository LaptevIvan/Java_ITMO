import java.util.Arrays;
import java.util.Scanner;
public class ReverseMinR {
    public static void main(String[] args) {
        int[][] table = new int[10][];
        Scanner scannerRow = new Scanner(System.in);
        int numberRows = 0;
        while (scannerRow.hasNextLine()) { 
            if (numberRows >= table.length) {
                int[][] newTable = new int[numberRows * 2][];
                for (int i = 0; i < table.length; i++) {
                    newTable[i] = Arrays.copyOf(table[i], table[i].length);
                }
                table = newTable;
            }
            table[numberRows] = new int[10];
            String line = scannerRow.nextLine();
            Scanner scannerNumber = new Scanner(line);
            int reallySize = 0;
            while (scannerNumber.hasNextInt()) {
                if (reallySize >= table[numberRows].length) {
                    table[numberRows] = Arrays.copyOf(table[numberRows], reallySize * 2);
                }
                table[numberRows][reallySize] = scannerNumber.nextInt();
                reallySize++;
            }
            scannerNumber.close();
            table[numberRows] = Arrays.copyOf(table[numberRows], reallySize);
            numberRows++;
        }
        scannerRow.close();
        for (int i = 0; i < numberRows; i++) {
            int Min = Integer.MAX_VALUE;
            for (int j = 0; j < table[i].length; j++) {
                Min = Math.min(Min, table[i][j]);
                System.out.print(Min + " ");
            }
            System.out.println();
        }
    }
}