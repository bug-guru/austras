package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonShortDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonShortSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonShortConverter extends JsonShortSerializer, JsonShortDeserializer {

    @Override
    void toJson(short value, JsonValueWriter writer);

    @Override
    short fromJson(JsonValueReader reader);

}
