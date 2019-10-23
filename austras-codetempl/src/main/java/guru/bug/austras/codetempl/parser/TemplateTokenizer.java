package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.parser.tokenizer.TemplateToken;
import guru.bug.austras.codetempl.parser.tokenizer.Tokenizer;

class TemplateTokenizer extends Tokenizer<TemplateToken> {

    public TemplateTokenizer() {
        super(variants);
    }
}
