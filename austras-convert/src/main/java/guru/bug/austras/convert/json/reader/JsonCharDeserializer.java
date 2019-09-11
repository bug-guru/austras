package guru.bug.austras.convert.json.reader;

public interface JsonCharDeserializer {
    char fromJson(JsonValueReader reader);
}
