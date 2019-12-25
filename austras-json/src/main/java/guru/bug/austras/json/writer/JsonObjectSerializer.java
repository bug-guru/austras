package guru.bug.austras.json.writer;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public interface JsonObjectSerializer<T> {
    void toJson(T value, JsonObjectWriter writer);
}
