package guru.bug.austras.convert.engine.json.reader;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@FunctionalInterface
public interface JsonObjectMemberVisitor<T> {
    void accept(T object, String key, JsonValueReader reader);
}
