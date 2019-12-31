package guru.bug.austras.convert.engine.json.reader;

public interface JsonCharacterDeserializer {
    char fromJson(JsonValueReader reader);
}
