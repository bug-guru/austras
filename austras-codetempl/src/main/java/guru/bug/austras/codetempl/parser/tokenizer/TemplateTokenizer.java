package guru.bug.austras.codetempl.parser.tokenizer;

import java.util.List;

public class TemplateTokenizer extends Tokenizer<TemplateToken> {
    public TemplateTokenizer() {
        super(List.of(
                new TemplateNewLineTokenProcessor(),
                new TemplateSpecTokenProcessor(),
                new TemplateTextTokenProcessor()));
    }
}
