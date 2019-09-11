package guru.bug.austras.convert.json.reader;

@FunctionalInterface
public interface JsonObjectMemberVisitor<T> {
    void accept(T object, String key, JsonValueReader reader);
}
