package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.parser.tokenizer.TemplateToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BodyParser {
    private final Iterator<TemplateToken> tokenIterator;
    private final List<Block> result = new ArrayList<>();

    protected BodyParser(Iterator<TemplateToken> tokenIterator) {
        this.tokenIterator = tokenIterator;
    }

    public void parse() {
        while (tokenIterator.hasNext()) {
            var t = tokenIterator.next();
            Block block;
            switch (t.getType()) {
                case TXT:
                    block = PlainTextBlock.builder()
                            .append(t.getValue())
                            .build();
                    break;
                case EXP:
                    block = parseExp(t.getValue().strip());
                    break;
                case CMD:
                    block = parseCmd(t.getValue().strip());
                    break;
                default:
                    throw new IllegalStateException("Token is not supported [" + t.getType() + "]");
            }
            result.add(block);
        }
    }

    private Block parseExp(String value) {
        return new ExpressionParser(value).parse();
    }

    private Block parseCmd(String value) {
        return null;
    }
}
