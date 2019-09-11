package guru.bug.austras.convert.json.reader;

public interface JsonDeserializer<T> {
    T fromJson(JsonValueReader reader);
}
