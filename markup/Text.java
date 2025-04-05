package markup;

public class Text implements InList {
	final String text;
	
	public Text(String thatText) {
		text = thatText;
	}
	
	@Override
	public void toMarkdown(StringBuilder symbols) {
		symbols.append(text);
	}
	
	@Override
	public void toBBCode(StringBuilder symbols) {
		symbols.append(text);
	}
}