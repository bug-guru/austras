package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonByteDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonByteSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonByteConverter extends JsonByteSerializer, JsonByteDeserializer {

    @Override
    void toJson(byte value, JsonValueWriter writer);

    @Override
    byte fromJson(JsonValueReader reader);

}
