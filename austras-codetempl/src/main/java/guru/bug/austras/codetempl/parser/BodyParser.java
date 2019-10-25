package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.parser.tokenizer.TemplateToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BodyParser {
    private final Iterator<TemplateToken> tokenIterator;
    private final ExpressionParser expressionParser;
    private final CommandParser commandParser;
    private final List<Block> result = new ArrayList<>();

    protected BodyParser(Iterator<TemplateToken> tokenIterator) {
        this.tokenIterator = tokenIterator;
        this.expressionParser = new ExpressionParser();
        this.commandParser = new CommandParser(tokenIterator);
    }

    public void parse() {
        while (tokenIterator.hasNext()) {
            var t = tokenIterator.next();
            Block block;
            switch (t.getType()) {
                case TEXT:
                    block = PlainTextBlock.builder()
                            .append(t.getValue())
                            .build();
                    break;
                case NEW_LINE:
                    block = PlainTextBlock.builder()
                            .append(System.lineSeparator())
                            .build();
                    break;
                case EXPRESSION:
                    block = parseExp(t.getValue().strip());
                    break;
                case COMMAND:
                    block = parseCmd(t.getValue().strip());
                    break;
                default:
                    throw new IllegalStateException("Token is not supported [" + t.getType() + "]");
            }
            result.add(block);
        }
    }

    private Block parseExp(String value) {
        return expressionParser.parse(value);
    }

    private Block parseCmd(String value) {
        return commandParser.parse(value);
    }
}
