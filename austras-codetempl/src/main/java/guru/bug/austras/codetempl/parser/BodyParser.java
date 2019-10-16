package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BodyParser {
    private final Iterator<Token> tokenIterator;
    private final List<Block> result = new ArrayList<>();

    protected BodyParser(Iterator<Token> tokenIterator) {
        this.tokenIterator = tokenIterator;
    }

    public void parse() {
        while (tokenIterator.hasNext()) {
            var t = tokenIterator.next();
            Block block;
            switch (t.type) {
                case TXT:
                    block = PlainTextBlock.builder()
                            .append(t.value)
                            .build();
                    break;
                case EXP:
                    block = parseExp(t.value.strip());
                    break;
                case CMD:
                    block = parseCmd(t.value.strip());
                    break;
                default:
                    throw new IllegalStateException("Token is not supported [" + t.type + "]");
            }
            result.add(block);
        }
    }

    private Block parseExp(String value) {
        return new ExpressionParser(value).parse();
    }

    private Block parseCmd(String value) {

    }
}
