package guru.bug.austras.json.reader;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.nio.CharBuffer;

class JsonBufferedReader {
    private static final char NULL_CHAR = '\0';
    private static final int BUFFER_CAPACITY = 8192;
    private final CharBuffer buffer;
    private final Reader reader;
    private final Position pos = new Position();
    private boolean freshStart = true;
    private char lastNewlineChar = NULL_CHAR;
    private boolean newline = false;
    private char lastChar = NULL_CHAR;
    private boolean repeatLastChar = false;

    JsonBufferedReader(Reader reader) {
        this.reader = reader;
        this.buffer = CharBuffer.allocate(BUFFER_CAPACITY);
        this.buffer.flip();
    }

    void back() {
        if (freshStart) {
            throw new IllegalStateException("Reading not yet started");
        }
        if (repeatLastChar) {
            throw new IllegalStateException("Cannot rewind more than 1 char back");
        }
        repeatLastChar = true;
    }

    char next() {
        if (repeatLastChar) {
            repeatLastChar = false;
            return lastChar;
        }
        char ch = next0();
        freshStart = false;

        if (ch != '\n' && ch != '\r') {
            if (newline) {
                newline = false;
                pos.nextLine();
            }
            lastNewlineChar = NULL_CHAR;
            if (ch != NULL_CHAR || lastChar != NULL_CHAR) {
                pos.nextCol();
            }
            lastChar = ch;
            return ch;
        }

        if (lastNewlineChar == NULL_CHAR) {
            if (newline) {
                pos.nextLine();
            }
            newline = true;
            lastNewlineChar = ch;
            pos.nextCol();
            lastChar = '\n';
            return lastChar;
        }

        char tmp = lastNewlineChar;
        lastNewlineChar = NULL_CHAR;
        if (tmp == ch) {
            pos.nextLine();
            pos.nextCol();
            lastChar = '\n';
            newline = true;
            return lastChar;
        } else {
            return next();
        }
    }

    char next0() {
        try {
            if (!buffer.hasRemaining()) {
                buffer.clear();
                if (reader.read(buffer) == -1) {
                    buffer.flip();
                    return NULL_CHAR;
                }
                buffer.flip();
            }
            return buffer.get();
        } catch (IOException e) {
            throw createParsingException(e);
        }
    }

    Position getPosition() {
        return pos;
    }

    ParsingException createParsingException(String message) {
        return new ParsingException(message, pos);
    }

    private ParsingException createParsingException(Throwable cause) {
        return new ParsingException(pos, cause);
    }

    ParsingException unicodeExpected(Throwable cause) {
        return new ParsingException("unicode expected", pos, cause);
    }

    ParsingException createParsingException() {
        return new ParsingException(pos);
    }

    static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        private int row = 1;
        private int col = 0;

        private void nextLine() {
            row++;
            col = 0;
        }

        private void nextCol() {
            col++;
        }

        int getRow() {
            return row;
        }

        int getCol() {
            return col;
        }
    }
}
