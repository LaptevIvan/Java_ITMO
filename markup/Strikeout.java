package markup;

import java.util.List;

public class Strikeout extends MarkElements {
	public Strikeout(List<InList> inputSymbols) {
		super(inputSymbols, "~", "s");
	}
}