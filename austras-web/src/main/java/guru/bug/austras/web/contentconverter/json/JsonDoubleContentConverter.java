package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.convert.converters.JsonDoubleConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.DoubleContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonDoubleContentConverter implements DoubleContentConverter {
    private final JsonDoubleConverter doubleConverter;

    public JsonDoubleContentConverter(JsonDoubleConverter doubleConverter) {
        this.doubleConverter = doubleConverter;
    }

    @Override
    public double fromString(String value) {
        return doubleConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(double value) {
        var result = new StringWriter(10);
        doubleConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public double read(Reader reader) {
        return doubleConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(double value, Writer writer) {
        doubleConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

