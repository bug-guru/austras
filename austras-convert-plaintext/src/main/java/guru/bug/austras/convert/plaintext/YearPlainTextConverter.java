/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.Year;

@PlainText
public class YearPlainTextConverter extends AbstractPlainTextConverter<Year> {
    @Override
    public Year fromString(String value) {
        if (value == null) return null;
        return Year.parse(value);
    }

    @Override
    public String toString(Year obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
