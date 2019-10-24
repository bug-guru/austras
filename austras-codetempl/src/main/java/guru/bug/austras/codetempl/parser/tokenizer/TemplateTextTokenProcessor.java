package guru.bug.austras.codetempl.parser.tokenizer;

public class TemplateTextTokenProcessor implements TokenProcessor<TemplateToken> {
    private final StringBuilder text = new StringBuilder();

    @Override
    public ProcessResult process(int codePoint) {
        text.appendCodePoint(codePoint);
        return ProcessResult.ACCEPT;
    }

    @Override
    public TemplateToken complete() {
        return new TemplateToken(text.toString(), TemplateToken.Type.TEXT);
    }

    @Override
    public void reset() {
        text.setLength(0);
    }
}
