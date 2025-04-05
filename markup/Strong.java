package markup;

import java.util.List;

public class Strong extends MarkElements {
	public Strong(List<InList> inputSymbols) {
		super(inputSymbols, "__", "b");
	}
}