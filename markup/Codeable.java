package markup;

public interface Codeable {
	void toBBCode(StringBuilder symbols);
	void toMarkdown(StringBuilder symbols);
}