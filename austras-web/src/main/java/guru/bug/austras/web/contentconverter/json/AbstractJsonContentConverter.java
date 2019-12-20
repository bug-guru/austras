package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.web.contentconverter.ContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractJsonContentConverter<T> implements ContentConverter<T> {

    @Override
    public T fromString(String value) {
        var reader = new StringReader(value);
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public String toString(T value) {
        var result = new StringWriter(10);
        var jsonWriter = JsonValueWriter.newInstance(result);
        toJson(value, jsonWriter);
        return result.toString();
    }

    @Override
    public T read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(T value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }

    protected abstract T fromJson(JsonValueReader jsonReader);

    protected abstract void toJson(T value, JsonValueWriter jsonWriter);
}
