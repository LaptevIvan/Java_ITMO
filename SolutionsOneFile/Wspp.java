import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class Wspp {
	private static final char APOSTROPHE = 39;
    public static void main(String[] args) {
        try {
			MyScanner in = new MyScanner(args[0] ,"UTF-8");
			Map<String, List<Integer>> data = new LinkedHashMap<>();
			String scannersWord;
			StringBuilder word = new StringBuilder();
            boolean beLetter = false;
			int numberWord = 0;
			while (in.hasNext()) {
				scannersWord = in.next();
				for (int i = 0; i < scannersWord.length(); i++) {
					char symbol = scannersWord.charAt(i);
					if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION
							|| symbol == APOSTROPHE) {
						word.append(Character.toLowerCase((char) symbol));
                        beLetter = true;
					}  else {
						beLetter = false;
					}
                    if ((!beLetter && !word.isEmpty()) || (beLetter && i == scannersWord.length() - 1)) {
						numberWord++;
						String key = word.toString();
						if (data.containsKey(key)) {
							data.get(key).add(numberWord);
						} else {
							List<Integer> wordStat = new ArrayList<Integer>();
							wordStat.add(numberWord);
							data.put(key, wordStat);
						}
						word.delete(0, word.length());
					}
				}
			}
            in.close();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
			try {
				for (Map.Entry<String, List<Integer>> statOneWord : data.entrySet()) {
					List<Integer> stat = statOneWord.getValue();
					out.write(statOneWord.getKey() + " " + stat.size());
					for (Integer i : stat) {
						out.write(" " +  i);
					}
					out.write('\n');
				}
			} catch (IOException errorWriting) {
				System.err.println("Error writing to file" + errorWriting.getMessage());
			} finally {
				try {
					out.close();
				} catch (IOException badClosing) {
					System.err.println("Error in closing" + badClosing.getMessage());
				}
			}
		} catch (IOException noFile) {
			System.err.println("File wasn't found" + noFile.getMessage());
		}
    }
}