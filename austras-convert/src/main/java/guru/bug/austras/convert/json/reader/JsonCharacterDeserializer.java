package guru.bug.austras.convert.json.reader;

public interface JsonCharacterDeserializer {
    char fromJson(JsonValueReader reader);
}
