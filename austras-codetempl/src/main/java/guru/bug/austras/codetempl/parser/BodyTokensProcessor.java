package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.parser.tokenizer.template.TemplateToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BodyTokensProcessor {
    private final Iterator<TemplateToken> tokenIterator;

    public BodyTokensProcessor(Iterator<TemplateToken> tokenIterator) {
        this.tokenIterator = tokenIterator;
    }

    public List<Block> processBody() {
        var result = new ArrayList<Block>();
        var expressionParser = new ExpressionParser();
        var commandParser = new CommandSwitch(this);
        loop:
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
                    block = expressionParser.parse(t.getValue().strip());
                    break;
                case COMMAND:
                    String value = t.getValue().strip();
                    if (value.equals("END")) {
                        break loop;
                    }
                    block = commandParser.parse(value);
                    break;
                default:
                    throw new IllegalStateException("Token is not supported [" + t.getType() + "]");
            }
            result.add(block);
        }
        return result;
    }
}
