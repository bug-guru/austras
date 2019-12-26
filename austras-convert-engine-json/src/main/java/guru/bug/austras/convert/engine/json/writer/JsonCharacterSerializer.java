package guru.bug.austras.convert.engine.json.writer;

public interface JsonCharacterSerializer {
    void toJson(char value, JsonValueWriter writer);
}
