package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.converters.JsonIntegerConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.IntegerContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonIntegerContentConverter implements IntegerContentConverter {
    private final JsonIntegerConverter integerConverter;

    public JsonIntegerContentConverter(JsonIntegerConverter integerConverter) {
        this.integerConverter = integerConverter;
    }

    @Override
    public int fromString(String value) {
        return integerConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(int value) {
        var result = new StringWriter(10);
        integerConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public int read(Reader reader) {
        return integerConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(int value, Writer writer) {
        integerConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

