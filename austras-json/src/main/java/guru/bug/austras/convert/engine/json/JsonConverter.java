package guru.bug.austras.convert.engine.json;


import guru.bug.austras.convert.engine.json.reader.JsonDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    void toJson(T value, JsonValueWriter writer);

    @Override
    T fromJson(JsonValueReader reader);

}
