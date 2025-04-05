import java.io.IOException;
import java.util.Arrays;
public class Reverse {
    public static void main(String[] args) {
        int[][] table = new int[10][];
		try {
			MyScanner scannerRow = new MyScanner(System.in);
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
				MyScanner scannerNumber = new MyScanner(line);
				int reallySize = 0;
				while (scannerNumber.hasNextInt()) {
					if (reallySize >= table[numberRows].length) {
						table[numberRows] = Arrays.copyOf(table[numberRows], reallySize * 2);
					}
					table[numberRows][reallySize++] = scannerNumber.nextInt();
				}
				scannerNumber.close();
				table[numberRows] = Arrays.copyOf(table[numberRows], reallySize);
				numberRows++;
			}
			scannerRow.close();
			for (int i = numberRows - 1; i > -1; i--) {
				for (int j = table[i].length - 1; j > -1; j--) {
					System.out.print(table[i][j] + " ");
				}
				System.out.println();
			}
		} catch (IOException errorReading) {
		 	System.err.println(errorReading.getMessage());
		}
    }
}