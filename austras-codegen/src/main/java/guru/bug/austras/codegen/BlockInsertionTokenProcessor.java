package guru.bug.austras.codegen;

public class BlockInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    public BlockInsertionTokenProcessor() {
        super('#', TemplateToken.Type.BLOCK);
    }
}
