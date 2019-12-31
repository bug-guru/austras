package guru.bug.austras.convert.engine.json.reader;

public interface JsonByteDeserializer {
    byte fromJson(JsonValueReader reader);
}
