package guru.bug.austras.codetempl;

public class BlockInsertionTokenProcessor extends AbstractInsertionTokenProcessor {
    public BlockInsertionTokenProcessor() {
        super('#', TemplateToken.Type.BLOCK);
    }
}
