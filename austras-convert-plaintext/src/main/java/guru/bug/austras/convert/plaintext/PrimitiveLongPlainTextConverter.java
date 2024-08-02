/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.LongContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveLongPlainTextConverter implements LongContentConverter {
    @Override
    public long fromString(String value) {
        return Long.parseLong(value);
    }

    @Override
    public String toString(long value) {
        return Long.toString(value);
    }

    @Override
    public long read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(long value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }

}
