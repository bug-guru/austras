/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.CharacterContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveCharacterPlainTextConverter implements CharacterContentConverter {
    @Override
    public char fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String toString(char value) {
        return Character.toString(value);
    }

    @Override
    public char read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(char value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }
}
