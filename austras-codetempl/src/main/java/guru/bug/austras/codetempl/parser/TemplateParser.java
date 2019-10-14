package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.Template;
import guru.bug.austras.codetempl.blocks.PlainTextBlock;

import java.util.List;

class TemplateParser {
    private final Template.Builder templateBuilder = Template.builder();
    private final List<Token> tokens;

    TemplateParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Template parse() {

        for (var t : tokens) {
            switch (t.type) {
                case TXT:
                    templateBuilder.add(PlainTextBlock.builder()
                            .append(t.value)
                            .build());
                    break;
                case EXP:
                    parseExp(t.value);
                    break;
                case CMD:
                    parseCmd(t.value);
                    break;
            }
        }

    }

    private void parseCmd(String value) {

    }

    private void parseExp(String value) {

    }
}
