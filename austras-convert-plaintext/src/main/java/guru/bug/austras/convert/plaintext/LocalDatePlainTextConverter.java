/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.time.LocalDate;

@PlainText
public class LocalDatePlainTextConverter extends AbstractPlainTextConverter<LocalDate> {
    @Override
    public LocalDate fromString(String value) {
        if (value == null) return null;
        return LocalDate.parse(value);
    }

    @Override
    public String toString(LocalDate obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
