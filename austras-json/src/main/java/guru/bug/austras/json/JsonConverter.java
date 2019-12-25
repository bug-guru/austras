package guru.bug.austras.json;


import guru.bug.austras.json.reader.JsonDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    void toJson(T value, JsonValueWriter writer);

    @Override
    T fromJson(JsonValueReader reader);

}
