package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.FloatContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveFloatPlainTextConverter implements FloatContentConverter {
    @Override
    public float fromString(String value) {
        return Float.parseFloat(value);
    }

    @Override
    public String toString(float value) {
        return Float.toString(value);
    }

    @Override
    public float read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(float value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }

}
