package guru.bug.austras.convert.json.writer;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public interface JsonObjectSerializer<T> {
    void toJson(T value, JsonObjectWriter writer);
}
