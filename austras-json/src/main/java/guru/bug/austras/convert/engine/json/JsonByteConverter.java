package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonByteDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonByteSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonByteConverter extends JsonByteSerializer, JsonByteDeserializer {

    @Override
    void toJson(byte value, JsonValueWriter writer);

    @Override
    byte fromJson(JsonValueReader reader);

}
