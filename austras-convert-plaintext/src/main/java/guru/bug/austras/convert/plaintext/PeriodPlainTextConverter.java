/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.Period;

@PlainText
public class PeriodPlainTextConverter extends AbstractPlainTextConverter<Period> {
    @Override
    public Period fromString(String value) {
        if (value == null) return null;
        return Period.parse(value);
    }

    @Override
    public String toString(Period obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}