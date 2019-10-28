package guru.bug.austras.codetempl.parser.template;

import guru.bug.austras.codetempl.parser.Tokenizer;

import java.util.List;

public class TemplateTokenizer extends Tokenizer<TemplateToken> {
    public TemplateTokenizer() {
        super(List.of(
                new TemplateNewLineTokenProcessor(),
                new TemplateSpecTokenProcessor(),
                new TemplateTextTokenProcessor()));
    }
}
