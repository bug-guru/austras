package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonDoubleDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonDoubleSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonDoubleConverter extends JsonDoubleSerializer, JsonDoubleDeserializer {

    @Override
    void toJson(double value, JsonValueWriter writer);

    @Override
    double fromJson(JsonValueReader reader);

}
