package guru.bug.austras.convert.engine.json.reader;

public interface JsonDeserializer<T> {
    T fromJson(JsonValueReader reader);
}
