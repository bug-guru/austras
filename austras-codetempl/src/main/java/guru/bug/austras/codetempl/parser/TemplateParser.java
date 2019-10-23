package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.Template;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;
import guru.bug.austras.codetempl.parser.tokenizer.TemplateToken;

import java.util.Iterator;
import java.util.List;

class TemplateParser {
    private final Template.Builder templateBuilder = Template.builder();
    private final List<TemplateToken> tokens;
    private Iterator<TemplateToken> tokenIterator;

    TemplateParser(List<TemplateToken> tokens) {
        this.tokens = tokens;
    }

    Template parse() {
        tokenIterator = tokens.iterator();
        while (tokenIterator.hasNext()) {
            var t = tokenIterator.next();
            switch (t.getType()) {
                case TEXT:
                    templateBuilder.add(PlainTextBlock.builder()
                            .append(t.getValue())
                            .build());
                    break;
                case EXPRESSION:
                    parseExp(t.getValue());
                    break;
                case COMMAND:
                    parseCmd(t.getValue());
                    break;
            }
        }
        return null;
    }

    private void parseCmd(String value) {

    }

    private void parseExp(String value) {

    }
}
