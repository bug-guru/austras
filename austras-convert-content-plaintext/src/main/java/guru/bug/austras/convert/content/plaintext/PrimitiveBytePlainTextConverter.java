package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.ByteContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveBytePlainTextConverter implements ByteContentConverter {
    @Override
    public byte fromString(String value) {
        return Byte.parseByte(value);
    }

    @Override
    public String toString(byte obj) {
        return String.valueOf(obj);
    }

    @Override
    public byte read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(byte value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }
}
