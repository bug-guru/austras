package guru.bug.austras.convert.json.reader;

import guru.bug.austras.convert.json.utils.JsonReadingException;

public class ParsingException extends JsonReadingException {
    private final JsonBufferedReader.Position position;

    public ParsingException(JsonBufferedReader.Position pos) {
        this("", pos, null);
    }

    public ParsingException(String message, JsonBufferedReader.Position pos) {
        this(message, pos, null);
    }

    public ParsingException(JsonBufferedReader.Position pos, Throwable cause) {
        this("", pos, cause);
    }

    public ParsingException(String message, JsonBufferedReader.Position pos, Throwable cause) {
        super(message + " at row: " + pos.getRow() + ", col: " + pos.getCol(), cause);
        this.position = pos;
    }

    public JsonBufferedReader.Position getPosition() {
        return position;
    }
}
