package markup;

import java.util.List;

public class Emphasis extends MarkElements {
	public Emphasis(List<InList> inputSymbols) {
		super(inputSymbols, "*", "i");
	}
}