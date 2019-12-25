package guru.bug.austras.json.reader;

public interface JsonByteDeserializer {
    byte fromJson(JsonValueReader reader);
}
