import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class WordStatInput {
    public static void main(String[] args) {
        try {
			MyScanner in = new MyScanner(args[0] ,"UTF-8");
			final char APOSTROPHE = 39;
			String[] wordsList = new String[16];
			int[] counts = new int[16];
			String scannersWord;
			StringBuilder word = new StringBuilder();
            boolean beLetter = false;
			int pointerWord = -1;
			while (in.hasNext()) {
				scannersWord = in.next();
				for (int i = 0; i < scannersWord.length(); i++) {
					char symbol = scannersWord.charAt(i);
					if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION
							|| symbol == APOSTROPHE) {
						word.append(Character.toLowerCase(symbol));
                        beLetter = true;
					}  else {
						beLetter = false;
					}
                    if ((!beLetter && !word.isEmpty()) || (beLetter && i == scannersWord.length() - 1)) {
						boolean find = false;
						for (int j = 0; j <= pointerWord; j++) {
							if (wordsList[j].contentEquals(word)) {
								find = true;
								counts[j] += 1;
								break;
							}
						}
						if (!find) {
							if (pointerWord == wordsList.length - 1) {
								wordsList = Arrays.copyOf(wordsList, wordsList.length * 2);
								counts = Arrays.copyOf(counts, wordsList.length);
							}
							wordsList[++pointerWord] = word.toString();
							counts[pointerWord] = 1;
						}
                        beLetter = false;
						word.delete(0, word.length());
					}
				}
			}
            in.close();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
			try {
				for (int i = 0; i <= pointerWord; i++) {
					out.write(wordsList[i] + " " + counts[i] + "\n");
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
