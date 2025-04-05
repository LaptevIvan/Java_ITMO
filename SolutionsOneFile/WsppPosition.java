import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class WsppPosition {
    private static final char APOSTROPHE = 39;
    public static void main(String[] args) {
        try {
            MyScanner in = new MyScanner(args[0], "UTF-8");
            Map<String, List<String>> data = new LinkedHashMap<>();
            String scannersWord;
            StringBuilder word = new StringBuilder();
            boolean beLetter = false;
            int row = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                row++;
                MyScanner scWord = new MyScanner(line);
                List<String> dataRow = new ArrayList<>();
                while (scWord.hasNext()) {
                    scannersWord = scWord.next();
                    for (int i = 0; i < scannersWord.length(); i++) {
                        char symbol = scannersWord.charAt(i);
                        if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION
                                || symbol == APOSTROPHE) {
                            word.append(Character.toLowerCase((char) symbol));
                            beLetter = true;
                        } else {
                            beLetter = false;
                        }
                        if ((!beLetter && !word.isEmpty()) || (beLetter && i == scannersWord.length() - 1)) {
                            dataRow.add(word.toString());
                            word.delete(0, word.length());
                        }
                    }
                }
                scWord.close();
                for (int i = 0; i < dataRow.size(); i++) {
                    String tocken = dataRow.get(i);
                    String elemStat = row +":"+ (dataRow.size() - i);
                    if (data.containsKey(tocken)) {
                        data.get(tocken).add(elemStat);
                    } else {
                        List<String> wordStat = new ArrayList<>();
                        wordStat.add(elemStat);
                        data.put(tocken, wordStat);
                    }
                }
            }
            in.close();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
                for (Map.Entry<String, List<String>> statOneWord : data.entrySet()) {
                    List<String> stat = statOneWord.getValue();
                    out.write(statOneWord.getKey() + " " + stat.size());
                    for (String elemStat : stat) {
                        out.write(" " + elemStat);
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
