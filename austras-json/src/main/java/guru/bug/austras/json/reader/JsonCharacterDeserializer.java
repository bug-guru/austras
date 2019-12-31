package guru.bug.austras.json.reader;

public interface JsonCharacterDeserializer {
    char fromJson(JsonValueReader reader);
}
