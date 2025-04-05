package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Md2Html {
    private static int brokeLine(String symbol) throws IOException {
        int i = 0;
        int count = 0;
        int symb = symbol.charAt(0);
        while (symb == System.lineSeparator().charAt(i)) {
            afterLnBreak.append((char) symb);
            if (symbol.equals("#")) {
                countGrid++;
            }
            i++;
            if (System.lineSeparator().contentEquals(afterLnBreak)) {
                i = 0;
                count++;
                afterLnBreak.delete(0, afterLnBreak.length());
            }
            symb = in.read();
        }
        nextSymb = symb != -1? String.valueOf((char) symb) : "-1";
        return count;
    }

    private static void endBlock() throws IOException {
        if (heapSymbols.charAt(3) == '>') {
            heapSymbols.append(createEndTag("h" + heapSymbols.charAt(2)));
        } else {
            heapSymbols.append(createEndTag("p"));
        }
        for (Map.Entry<String, Integer> symb : symbolsEnabled.entrySet()) {
            if (symb.getValue() > -1) {
                symbolsEnabled.replace(symb.getKey(), symb.getValue(), -1);
            }
        }
        out.write(heapSymbols.toString());
        if (!nextSymb.equals("-1")) {
            out.write(System.lineSeparator());
        } else {
            nextSymb = "";
        }
        heapSymbols.delete(0, heapSymbols.length());
    }

    private static String createStartTag(String symbols) {
        return "<" + symbols + ">";
    }

    private static String createEndTag(String symbols) {
        return "</" + symbols + ">";
    }

    private static StringBuilder heapSymbols;
    private static StringBuilder afterLnBreak = new StringBuilder();
    private static int countGrid;
    private static BufferedReader in;
    private static BufferedWriter out;

    private static String nextSymb = "";
    private static String symbol = "";

    static Map<String, String> items = Map.ofEntries(
            Map.entry("*", "em"),
            Map.entry("**", "strong"),
            Map.entry("_", "em"),
            Map.entry("__", "strong"),
            Map.entry("-", ""),
            Map.entry("--", "s"),
            Map.entry("`", "code"),
            Map.entry("'", ""),
            Map.entry("''", "q"),
            Map.entry("<", "&lt;"),
            Map.entry(">", "&gt;"),
            Map.entry("&", "&amp;"),
            Map.entry("\\", "\\"),
            Map.entry(String.valueOf(System.lineSeparator().charAt(0)), "null")
    );
    private final static Map<String, String> SPECIAL_SYMBOLS = new HashMap<>(items);
    private static final Map<String, Integer> symbolsEnabled = new HashMap<>(Map.of(
            "*", -1,
            "_", -1,
            "**", -1,
            "-", -2,
            "__", -1,
            "--", -1,
            "`", -1,
            "'", -2,
            "''", -1
    ));

    public static void main(String[] args) throws Exception {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            heapSymbols = new StringBuilder();
            int s;
            try {
                while (!nextSymb.equals("-1")) {
                    if (nextSymb.isEmpty()) {
                        s = in.read();
                        if (s == -1) {
                            if (!heapSymbols.isEmpty()) {
                                nextSymb = "-1";
                                endBlock();
                            }
                            break;
                        }
                        symbol = String.valueOf((char) s);
                    } else {
                        symbol = nextSymb;
                        nextSymb = "";
                    }
                    if (heapSymbols.isEmpty()) {
                        brokeLine(symbol);
                        symbol = nextSymb;
                        nextSymb = "";
                        while ((afterLnBreak.isEmpty() || countGrid == afterLnBreak.length()) && symbol.equals("#")) {
                            afterLnBreak.append(symbol);
                            countGrid++;
                            symbol = String.valueOf((char) in.read());
                        }
                        if (!afterLnBreak.isEmpty() && symbol.equals(" ")) {
                            heapSymbols.append("<h").append(countGrid).append(">");
                            symbol = String.valueOf((char) in.read());
                        } else {
                            heapSymbols.append("<p>").append(afterLnBreak);
                        }
                        countGrid = 0;
                        afterLnBreak.delete(0, afterLnBreak.length());
                    }
                    if (!SPECIAL_SYMBOLS.containsKey(symbol)) {
                        heapSymbols.append(symbol);
                    } else {
                        if (symbolsEnabled.containsKey(symbol)) {
                            do {
                                symbol += nextSymb;
                                nextSymb = String.valueOf((char)in.read());
                            } while (symbolsEnabled.containsKey(symbol+nextSymb));
                            if (symbolsEnabled.get(symbol) < 0) {
                                if (symbolsEnabled.get(symbol) == -1) {
                                    symbolsEnabled.put(symbol, heapSymbols.length());
                                }
                                heapSymbols.append(symbol);
                            } else {
                                int ind = symbolsEnabled.get(symbol);
                                String startTag = createStartTag(SPECIAL_SYMBOLS.get(symbol));
                                heapSymbols.replace(ind, ind + symbol.length(), startTag);
                                heapSymbols.append(createEndTag(SPECIAL_SYMBOLS.get(symbol)));
                                symbolsEnabled.put(symbol, -1);
                                for (Map.Entry<String, Integer> symb : symbolsEnabled.entrySet()) {
                                    if (symb.getValue() > ind) {
                                        symbolsEnabled.replace(symb.getKey(), symb.getValue(), symb.getValue() + startTag.length() - 1);
                                    }
                                }
                            }
                        } else {
                            if (symbol.equals(String.valueOf(System.lineSeparator().charAt(0)))) {
                                int count = brokeLine(symbol);
                                heapSymbols.append(afterLnBreak);
                                if (count == 0) {
                                    heapSymbols.append(afterLnBreak);
                                    afterLnBreak.delete(0, afterLnBreak.length());
                                } else if (count == 1 && !nextSymb.equals("-1")) {
                                    heapSymbols.append(System.lineSeparator());
                                }
                                else {
                                    endBlock();
                                }
                            } else if (symbol.equals("\\")) {
                                nextSymb = String.valueOf((char) in.read());
                                if (SPECIAL_SYMBOLS.containsKey(nextSymb) && !nextSymb.equals("-")) {
                                    heapSymbols.append(nextSymb);
                                    nextSymb = "";
                                } else {
                                    heapSymbols.append("\\");
                                }
                            } else {
                                heapSymbols.append(SPECIAL_SYMBOLS.get(symbol));
                            }
                        }
                    }
                }
            } catch (IOException errorReadWrite) {
                System.err.println("Error read or write file" + errorReadWrite.getMessage());
            } finally {
                try {
                    out.close();
                    in.close();
                } catch (IOException badClosing) {
                    System.err.println("Error in closing" + badClosing.getMessage());
                }
            }
        } catch (FileNotFoundException noFile) {
            System.err.println("File wasn't found" + noFile.getMessage());
        }
    }
}