package guru.bug.austras.convert.json.writer;

public interface JsonSerializer<T> {
    void toJson(T value, JsonValueWriter writer);
}
