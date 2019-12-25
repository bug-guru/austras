package guru.bug.austras.json.reader;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@FunctionalInterface
public interface JsonObjectMemberVisitor<T> {
    void accept(T object, String key, JsonValueReader reader);
}
