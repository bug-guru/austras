package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;

class ExpressionParser {

    Block parse(String expression) {
        tokenize();
        return null;
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
