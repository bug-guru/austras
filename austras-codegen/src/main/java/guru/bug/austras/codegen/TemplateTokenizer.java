package guru.bug.austras.codegen;

import java.util.List;

public class TemplateTokenizer extends Tokenizer<TemplateToken> {
    public TemplateTokenizer() {
        super(List.of(
                new NewLineTokenProcessor(),
                new ValueInsertionTokenProcessor(),
                new BlockInsertionTokenProcessor(),
                new TextTokenProcessor()));
    }
}