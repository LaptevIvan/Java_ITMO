package markup;

import java.util.List;

public class Paragraph implements Codeable {
    private List<InList> data;
    public Paragraph(List<InList> inputSymbols) {
        this.data = inputSymbols;
    }

    public void byElements(StringBuilder symbols, int variant) {
        for (InList el : data) {
            switch (variant) {
                case 0:
                    el.toMarkdown(symbols);
                    break;

                case 1:
                    el.toBBCode(symbols);
                    break;
            }
        }
    }

    @Override
    public void toMarkdown(StringBuilder symbols) {
        byElements(symbols, 0);
    }

    @Override
    public void toBBCode(StringBuilder symbols) {
        byElements(symbols, 1);
    }
}
