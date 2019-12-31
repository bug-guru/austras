package guru.bug.austras.convert.engine.json.writer;

public interface JsonSerializer<T> {
    void toJson(T value, JsonValueWriter writer);
}
