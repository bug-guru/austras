package guru.bug.austras.convert.engine.json.writer;

public interface JsonBooleanSerializer {
    void toJson(boolean value, JsonValueWriter writer);
}
