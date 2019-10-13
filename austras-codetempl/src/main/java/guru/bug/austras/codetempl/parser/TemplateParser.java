package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.Template;

import java.util.List;

class TemplateParser {
    private final Template.Builder templateBuilder = Template.builder();
    private final List<Token> tokens;

    TemplateParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Template parse() {
        return null;
    }
}
