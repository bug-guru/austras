package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.parser.template.TemplateToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BodyTokensProcessor {
    private final Iterator<TemplateToken> tokenIterator;
    private final LoopCommandParser loopCommandParser;

    public BodyTokensProcessor(Iterator<TemplateToken> tokenIterator) {
        this.tokenIterator = tokenIterator;
        this.loopCommandParser = new LoopCommandParser(this);
    }

    public List<Block> processBody() {
        var result = new ArrayList<Block>();
        var expressionParser = new ExpressionParser();
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
                    block = parseCommand(value);
                    break;
                default:
                    throw new IllegalStateException("Token is not supported [" + t.getType() + "]");
            }
            result.add(block);
        }
        return result;
    }

    public Block parseCommand(String value) {
        String cmd = value.split("\\s", 2)[0];
        //noinspection SwitchStatementWithTooFewBranches
        switch (cmd) {
            case "LOOP":
                return loopCommandParser.parse(value);
            default:
                throw new IllegalArgumentException("Unknown command " + cmd);
        }
    }
}
