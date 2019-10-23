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
        TemplateToken result = new TemplateToken(text.toString(), TemplateToken.Type.TXT);
        text.setLength(0);
        return result;
    }
}
