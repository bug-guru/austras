/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.ByteContentConverter;

@PlainText
public class BytePlainTextConverter extends AbstractPlainTextConverter<Byte> {
    private final ByteContentConverter converter;

    public BytePlainTextConverter(@PlainText ByteContentConverter converter) {
        this.converter = converter;
    }

    @Override
    public Byte fromString(String value) {
        if (value == null) {
            return null;
        }
        return converter.fromString(value);
    }

    @Override
    public String toString(Byte value) {
        if (value == null) {
            return null;
        }
        return converter.toString(value);
    }
}
