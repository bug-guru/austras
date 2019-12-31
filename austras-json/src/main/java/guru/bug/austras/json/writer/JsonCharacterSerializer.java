package guru.bug.austras.json.writer;

public interface JsonCharacterSerializer {
    void toJson(char value, JsonValueWriter writer);
}
