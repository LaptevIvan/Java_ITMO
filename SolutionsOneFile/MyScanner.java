import java.io.*;
import java.util.NoSuchElementException;

public class MyScanner implements Closeable {
	private Reader reader;
	private char[] buffer = new char[1000];
	private StringBuilder tocken = new StringBuilder();
	private StringBuilder del = new StringBuilder();
	private int place = 0;
	private int hasRead = 0;

	public MyScanner(String nameFile, String nameCharSet)
			throws IOException, FileNotFoundException, UnsupportedEncodingException {
		reader = new InputStreamReader(new FileInputStream(nameFile), nameCharSet);
	}

	public MyScanner(InputStream stream) throws IOException {
		reader = new InputStreamReader(stream);
	}

	public MyScanner(String line) throws IOException {
		reader = new StringReader(line);
	}

	private boolean trySkip(char symbol) {
		if (buffer[place] == symbol) {
			place++;
			end(place);
			return true;
		}
		return false;
	}

	private boolean end(int place) {
		if (place >= hasRead) {
			try {
				hasRead = reader.read(buffer);
				if (hasRead != -1) {
					this.place = 0;
					return false;
				}
				return true;
			} catch (IOException errorRead) {
				System.out.println(errorRead.getMessage());
			}
		}
		return false;
	}

	public boolean hasNextLine() {
		if (!end(place)) {
			return true;
		}
		return false;
	}

	public String nextLine() {
		tocken.delete(0, tocken.length());
		while (!end(place)) {
			if (buffer[place] != System.lineSeparator().charAt(0)) {
				tocken.append(buffer[place++]);
			} else {
				boolean flag = true;
				for (int i = 0; i < System.lineSeparator().length(); i++) {
					if (trySkip(System.lineSeparator().charAt(i))) {
						del.append(System.lineSeparator().charAt(i));
					} else {
						flag = false;
						tocken.append(del.toString());
						break;
					}
				}
				del.delete(0, del.length());
				if (flag) {
					String output = tocken.toString();
					tocken.delete(0, tocken.length());
					return output;
				}
			}
		}
		if (tocken.length() > 0) {
			String output = tocken.toString();
			tocken.delete(0, tocken.length());
			return output;
		}
		throw new NoSuchElementException();
	}

	private boolean isNumber() {
		try {
			Integer.parseInt(tocken.toString());
			return true;
		} catch (NumberFormatException notNumber) {
			return false;
		}
	}

	private boolean nextTocken() {
		if (!tocken.isEmpty()) {
			return true;
		}
		while (!end(place)) {
			if (!Character.isWhitespace(buffer[place])) {
				tocken.append(buffer[place]);
			} else if (!tocken.isEmpty()) {
				return true;
			}
			place++;
		}
		if (!tocken.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasNextInt() {
		if (nextTocken() && isNumber()) {
			return true;
		}
		return false;
	}

	public int nextInt() {
		if (hasNextInt()) {
			int num = Integer.parseInt(tocken.toString());
			tocken.delete(0, tocken.length());
			return num;
		}
		throw new NoSuchElementException();
	}

	public boolean hasNext() {
		if (!tocken.isEmpty()) {
			return true;
		} else {
			while (!end(place)) {
				if (!Character.isWhitespace(buffer[place])) {
					return true;
				}
				place++;
			}
		}
		return false;
	}

	public String next() {
		if (nextTocken()) {
			String ans = tocken.toString();
			tocken.delete(0, tocken.length());
			return ans;
		}
		throw new NoSuchElementException();
	}

	@Override
	public void close() throws IOException {
		reader.close();
		place = -1;
	}
}