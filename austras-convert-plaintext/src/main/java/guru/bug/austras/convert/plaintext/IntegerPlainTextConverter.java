/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.IntegerContentConverter;

@PlainText
public class IntegerPlainTextConverter extends AbstractPlainTextConverter<Integer> {
    private final IntegerContentConverter stringConverter;

    @SuppressWarnings("WeakerAccess")
    public IntegerPlainTextConverter(@PlainText IntegerContentConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Integer fromString(String value) {
        if (value == null) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Integer value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
