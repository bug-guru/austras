package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.converters.JsonBooleanConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.BooleanContentConverter;
import guru.bug.austras.web.contentconverter.Converts;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonBooleanContentConverter implements BooleanContentConverter {
    private final JsonBooleanConverter booleanConverter;

    public JsonBooleanContentConverter(JsonBooleanConverter booleanConverter) {
        this.booleanConverter = booleanConverter;
    }

    @Override
    public boolean fromString(String value) {
        return booleanConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(boolean value) {
        var result = new StringWriter(10);
        booleanConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public boolean read(Reader reader) {
        return booleanConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(boolean value, Writer writer) {
        booleanConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

