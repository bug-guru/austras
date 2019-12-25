package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.json.JsonFloatConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.FloatContentConverter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonFloatContentConverter implements FloatContentConverter {
    private final JsonFloatConverter floatConverter;

    public JsonFloatContentConverter(JsonFloatConverter floatConverter) {
        this.floatConverter = floatConverter;
    }

    @Override
    public float fromString(String value) {
        return floatConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(float value) {
        var result = new StringWriter(10);
        floatConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public float read(Reader reader) {
        return floatConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(float value, Writer writer) {
        floatConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

