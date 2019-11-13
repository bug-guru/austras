package guru.bug.austras.codetempl;

public class ValueInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    public ValueInsertionTokenProcessor() {
        super('$', TemplateToken.Type.VALUE);
    }
}
