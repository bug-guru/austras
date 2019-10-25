package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.parser.tokenizer.TemplateToken;

import java.util.Iterator;

public class CommandParser {

    private final Iterator<TemplateToken> tokenIterator;

    public CommandParser(Iterator<TemplateToken> tokenIterator) {
        this.tokenIterator = tokenIterator;
    }

    public Block parse(String value) {
        return null;
    }
}
