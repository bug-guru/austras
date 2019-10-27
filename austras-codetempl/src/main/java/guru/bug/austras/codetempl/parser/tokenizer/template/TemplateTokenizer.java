package guru.bug.austras.codetempl.parser.tokenizer.template;

import guru.bug.austras.codetempl.parser.tokenizer.Tokenizer;

import java.util.List;

public class TemplateTokenizer extends Tokenizer<TemplateToken> {
    public TemplateTokenizer() {
        super(List.of(
                new TemplateNewLineTokenProcessor(),
                new TemplateSpecTokenProcessor(),
                new TemplateTextTokenProcessor()));
    }
}
