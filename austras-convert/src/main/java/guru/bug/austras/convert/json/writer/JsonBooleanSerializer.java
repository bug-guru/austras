package guru.bug.austras.convert.json.writer;

public interface JsonBooleanSerializer {
    void toJson(boolean value, JsonValueWriter writer);
}
