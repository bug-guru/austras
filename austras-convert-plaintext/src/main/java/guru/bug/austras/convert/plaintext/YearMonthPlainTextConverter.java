/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.YearMonth;

@PlainText
public class YearMonthPlainTextConverter extends AbstractPlainTextConverter<YearMonth> {
    @Override
    public YearMonth fromString(String value) {
        if (value == null) return null;
        return YearMonth.parse(value);
    }

    @Override
    public String toString(YearMonth obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
