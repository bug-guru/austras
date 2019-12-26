package guru.bug.austras.convert.engine.json.writer;

public interface JsonIntegerSerializer {
    void toJson(int value, JsonValueWriter writer);
}
