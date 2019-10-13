package guru.bug.austras.codetempl.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TemplateTokenizer {
    private static final char DEFAULT_EXPR_CHAR = '$';
    private static final char DEFAULT_CMD_CHAR = '#';
    private final List<Token> tokenLine = new ArrayList<>();
    private final List<Token> tokens = new ArrayList<>();
    private final StringBuilder preToken = new StringBuilder(4);
    private final BufferedReader reader;
    private final char expChar;
    private final char cmdChar;
    private final char quotChar;
    private final char escChar;
    private int lineNr;
    private int colNr;
    private TokenBuilder currentTokenBuilder;

    TemplateTokenizer(BufferedReader reader, char expChar, char cmdChar) {
        this.reader = reader;
        this.expChar = expChar;
        this.cmdChar = cmdChar;
        this.quotChar = '"';
        this.escChar = '\\';
    }

    TemplateTokenizer(BufferedReader reader) {
        this(reader, DEFAULT_EXPR_CHAR, DEFAULT_CMD_CHAR);
    }


    List<Token> tokenize() throws IOException {
        var nextLine = reader.readLine();
        while (nextLine != null) {
            lineNr++;
            var line = nextLine;
            nextLine = reader.readLine();
            parseLine(line, nextLine != null);
        }

        return tokens;
    }

    private void parseLine(String line, boolean hasEOL) {
        preToken.setLength(0);
        currentTokenBuilder = new TextTokenBuilder();
        colNr = 0;
        line.codePoints().forEach(cp -> {
            colNr++;
            currentTokenBuilder.append(cp);
        });
        if (hasEOL) {
            currentTokenBuilder.append('\n');
        }
        currentTokenBuilder.forcePush();
    }

    private abstract class TokenBuilder {
        abstract void append(int cp);

        abstract Token.Type type();

        abstract void forcePush();

        final void push() {
            if (preToken.length() == 0) {
                return;
            }
            tokenLine.add(new Token(preToken.toString(), type()));
            preToken.setLength(0);
        }
    }

    private class TextTokenBuilder extends TokenBuilder {

        @Override
        void append(int cp) {
            if (cp == expChar) {
                push();
                currentTokenBuilder = new ExpTokenBuilder();
            } else if (cp == cmdChar) {
                push();
                currentTokenBuilder = new CmdTokenBuilder();
            } else {
                preToken.appendCodePoint(cp);
            }
        }

        @Override
        Token.Type type() {
            return Token.Type.TXT;
        }

        @Override
        void forcePush() {
            push();
            tokens.addAll(tokenLine);
            tokenLine.clear();
        }
    }

    private abstract class AuxTokenBuilder extends TokenBuilder {
        boolean str;
        boolean esc;

        @Override
        final void append(int cp) {
            if (!str && cp == exitChar()) {
                push();
                currentTokenBuilder = new TextTokenBuilder();
                return;
            }
            preToken.appendCodePoint(cp);
            if (esc) {
                esc = false;
            } else if (str && cp == escChar) {
                esc = true;
            } else if (str && cp == quotChar) {
                str = false;
            } else if (!str && cp == quotChar) {
                str = true;
            }
        }

        abstract char exitChar();

        @Override
        void forcePush() {
            throw new IllegalStateException("Unfinished token at line " + lineNr);
        }
    }

    private class ExpTokenBuilder extends AuxTokenBuilder {

        @Override
        Token.Type type() {
            return Token.Type.EXP;
        }

        @Override
        char exitChar() {
            return expChar;
        }
    }

    private class CmdTokenBuilder extends AuxTokenBuilder {


        @Override
        Token.Type type() {
            return Token.Type.CMD;
        }

        @Override
        char exitChar() {
            return cmdChar;
        }
    }
}
