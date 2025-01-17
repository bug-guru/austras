/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.time.ZoneOffset;

@PlainText
public class ZoneOffsetPlainTextConverter extends AbstractPlainTextConverter<ZoneOffset> {
    @Override
    public ZoneOffset fromString(String value) {
        if (value == null) return null;
        return ZoneOffset.of(value);
    }

    @Override
    public String toString(ZoneOffset obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
