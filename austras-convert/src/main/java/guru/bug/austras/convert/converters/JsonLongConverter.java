package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonLongDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonLongSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonLongConverter extends JsonLongSerializer, JsonLongDeserializer {

    @Override
    void toJson(long value, JsonValueWriter writer);

    @Override
    long fromJson(JsonValueReader reader);

}
