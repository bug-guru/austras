package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonDoubleDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonDoubleSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonDoubleConverter extends JsonDoubleSerializer, JsonDoubleDeserializer {

    @Override
    void toJson(double value, JsonValueWriter writer);

    @Override
    double fromJson(JsonValueReader reader);

}
