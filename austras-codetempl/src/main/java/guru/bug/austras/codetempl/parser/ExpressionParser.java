package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;

class ExpressionParser {
    private final String value;

    ExpressionParser(String value) {
        this.value = value;
    }

    Block parse() {
        tokenize();
    }

    private void tokenize() {

    }

    private enum Type {
        VAR,
        STR_LITERAL,
        QUESTION_MARK,
        COLON
    }

    private static class Token {
        private Type type;
        private String value;
    }
}
