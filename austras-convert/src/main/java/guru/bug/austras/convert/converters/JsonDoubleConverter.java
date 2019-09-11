package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonDoubleDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonDoubleSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonDoubleConverter extends JsonDoubleSerializer, JsonDoubleDeserializer {

    @Override
    void toJson(double value, JsonValueWriter writer);

    @Override
    double fromJson(JsonValueReader reader);

}
