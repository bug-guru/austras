package guru.bug.austras.json.writer;

public interface JsonBooleanSerializer {
    void toJson(boolean value, JsonValueWriter writer);
}
