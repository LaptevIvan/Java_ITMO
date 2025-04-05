import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReverseMinRAbc {
	static Map<Character, Character> lettersDigItems = Map.ofEntries(
			Map.entry('-','-'),
			Map.entry('a','0'),
			Map.entry('b','1'),
			Map.entry('c','2'),
			Map.entry('d','3'),
			Map.entry('e','4'),
			Map.entry('f','5'),
			Map.entry('g','6'),
			Map.entry('h','7'),
			Map.entry('i','8'),
			Map.entry('j','9')
	);
	private final static Map<Character, Character> lettersDig = new HashMap<>(lettersDigItems);

	static Map<Character, Character> digitLetItems = Map.ofEntries(
			Map.entry('-','-'),
			Map.entry('0','a'),
			Map.entry('1','b'),
			Map.entry('2','c'),
			Map.entry('3','d'),
			Map.entry('4','e'),
			Map.entry('5','f'),
			Map.entry('6','g'),
			Map.entry('7','h'),
			Map.entry('8','i'),
			Map.entry('9','j')
	);
	private final static Map<Character, Character> digitLet = new HashMap<>(digitLetItems);


	public static String code(int num) {
		String number = String.valueOf(num);
		StringBuilder wordNum = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			wordNum.append(digitLet.get(number.charAt(i)));
		}
		return wordNum.toString();
	}
	public static int decode(String word) {
		StringBuilder wordNum = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			wordNum.append(lettersDig.get(word.charAt(i)));
		}
		return Integer.parseInt(wordNum.toString());
	}
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
				while (scannerNumber.hasNext()) {
					String word = scannerNumber.next(); 
				    if (reallySize >= table[numberRows].length) {
					    table[numberRows] = Arrays.copyOf(table[numberRows], reallySize * 2);
				    }
				    table[numberRows][reallySize] = decode(word);
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
					System.out.print(code(Min) + " ");
				}
			 	System.out.println();
			}
		} catch (IOException errorReading) {
			System.err.println(errorReading.getMessage());
		}
    }
}