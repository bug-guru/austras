/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.ShortContentConverter;

@PlainText
public class ShortPlainTextConverter extends AbstractPlainTextConverter<Short> {
    private final ShortContentConverter stringConverter;

    public ShortPlainTextConverter(@PlainText ShortContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Short fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Short value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
