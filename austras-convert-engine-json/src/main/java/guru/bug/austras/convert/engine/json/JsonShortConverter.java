package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonShortDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonShortSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonShortConverter extends JsonShortSerializer, JsonShortDeserializer {

    @Override
    void toJson(short value, JsonValueWriter writer);

    @Override
    short fromJson(JsonValueReader reader);

}
