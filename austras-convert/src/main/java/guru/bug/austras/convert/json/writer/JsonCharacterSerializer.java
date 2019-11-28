package guru.bug.austras.convert.json.writer;

public interface JsonCharacterSerializer {
    void toJson(char value, JsonValueWriter writer);
}
