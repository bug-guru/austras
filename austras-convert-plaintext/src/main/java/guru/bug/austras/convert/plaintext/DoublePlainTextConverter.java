/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.DoubleContentConverter;

@PlainText
public class DoublePlainTextConverter extends AbstractPlainTextConverter<Double> {
    private final DoubleContentConverter converter;

    public DoublePlainTextConverter(@PlainText DoubleContentConverter converter) {
        this.converter = converter;
    }

    @Override
    public Double fromString(String value) {
        if (value == null) {
            return null;
        }
        return converter.fromString(value);
    }

    @Override
    public String toString(Double value) {
        if (value == null) {
            return null;
        }
        return converter.toString(value);
    }
}
