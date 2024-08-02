/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.DoubleContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveDoublePlainTextConverter implements DoubleContentConverter {
    @Override
    public double fromString(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public String toString(double value) {
        return Double.toString(value);
    }

    @Override
    public double read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(double value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }
}
