package guru.bug.austras.codegen;

class BlockInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    BlockInsertionTokenProcessor() {
        super('#', TemplateToken.Type.BLOCK);
    }
}
