/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.time.ZonedDateTime;

@PlainText
public class ZonedDateTimePlainTextConverter extends AbstractPlainTextConverter<ZonedDateTime> {
    @Override
    public ZonedDateTime fromString(String value) {
        if (value == null) return null;
        return ZonedDateTime.parse(value);
    }

    @Override
    public String toString(ZonedDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
