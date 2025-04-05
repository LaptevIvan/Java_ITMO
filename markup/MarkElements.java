package markup;
import java.util.List;

public class MarkElements extends Paragraph implements InList {
	private String tagMark; 
	private String tagBB;
	
	protected MarkElements(List<InList> inputSymbols, String tagMark, String tagBB) {
		super(inputSymbols);
		this.tagMark = tagMark;
		this.tagBB = tagBB;
	}
	
	@Override 
	public void toMarkdown(StringBuilder symbols) {
		symbols.append(tagMark);
		byElements(symbols, 0);
		symbols.append(tagMark);
	}
	
	@Override 
	public void toBBCode(StringBuilder symbols) {
		symbols.append("[").append(tagBB).append("]");
		byElements(symbols, 1);
		symbols.append("[/").append(tagBB).append("]");
	}
}