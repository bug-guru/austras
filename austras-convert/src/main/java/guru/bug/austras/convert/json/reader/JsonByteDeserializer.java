package guru.bug.austras.convert.json.reader;

public interface JsonByteDeserializer {
    byte fromJson(JsonValueReader reader);
}
