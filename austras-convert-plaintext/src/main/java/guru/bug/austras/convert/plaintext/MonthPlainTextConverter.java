/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.Month;

@PlainText
public class MonthPlainTextConverter extends AbstractPlainTextConverter<Month> {
    @Override
    public Month fromString(String value) {
        if (value == null) return null;
        return Month.valueOf(value);
    }

    @Override
    public String toString(Month obj) {
        if (obj == null) return null;
        return obj.name();
    }
}
