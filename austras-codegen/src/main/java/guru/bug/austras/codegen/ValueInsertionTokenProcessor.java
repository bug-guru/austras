package guru.bug.austras.codegen;

public class ValueInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    public ValueInsertionTokenProcessor() {
        super('$', TemplateToken.Type.VALUE);
    }
}
