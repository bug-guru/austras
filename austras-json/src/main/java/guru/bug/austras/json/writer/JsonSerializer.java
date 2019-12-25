package guru.bug.austras.json.writer;

public interface JsonSerializer<T> {
    void toJson(T value, JsonValueWriter writer);
}
