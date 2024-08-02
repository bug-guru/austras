/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.FloatContentConverter;

@PlainText
public class FloatPlainTextConverter extends AbstractPlainTextConverter<Float> {
    private final FloatContentConverter stringConverter;

    public FloatPlainTextConverter(@PlainText FloatContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Float fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Float value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
