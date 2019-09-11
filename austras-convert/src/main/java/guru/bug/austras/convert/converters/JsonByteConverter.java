package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonByteDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonByteSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonByteConverter extends JsonByteSerializer, JsonByteDeserializer {

    @Override
    void toJson(byte value, JsonValueWriter writer);

    @Override
    byte fromJson(JsonValueReader reader);

}
