package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonLongDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonLongSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonLongConverter extends JsonLongSerializer, JsonLongDeserializer {

    @Override
    void toJson(long value, JsonValueWriter writer);

    @Override
    long fromJson(JsonValueReader reader);

}
