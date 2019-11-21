package guru.bug.austras.codegen;

class ValueInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    ValueInsertionTokenProcessor() {
        super('$', TemplateToken.Type.VALUE);
    }
}
