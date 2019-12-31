package guru.bug.austras.json.reader;

public interface JsonDeserializer<T> {
    T fromJson(JsonValueReader reader);
}
