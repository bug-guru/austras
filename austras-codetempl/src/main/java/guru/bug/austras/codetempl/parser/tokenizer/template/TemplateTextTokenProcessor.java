package guru.bug.austras.codetempl.parser.tokenizer.template;

import guru.bug.austras.codetempl.parser.tokenizer.ProcessResult;
import guru.bug.austras.codetempl.parser.tokenizer.TokenProcessor;

public class TemplateTextTokenProcessor implements TokenProcessor<TemplateToken> {
    private final StringBuilder text = new StringBuilder();

    @Override
    public ProcessResult process(int codePoint) {
        text.appendCodePoint(codePoint);
        return ProcessResult.ACCEPT;
    }

    @Override
    public TemplateToken complete() {
        return new TemplateToken(TemplateToken.Type.TEXT, text.toString());
    }

    @Override
    public void reset() {
        text.setLength(0);
    }
}
