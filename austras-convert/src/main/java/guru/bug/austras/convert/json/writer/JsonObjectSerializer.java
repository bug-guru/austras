package guru.bug.austras.convert.json.writer;

public interface JsonObjectSerializer<T> {
    void toJson(T value, JsonObjectWriter writer);
}
