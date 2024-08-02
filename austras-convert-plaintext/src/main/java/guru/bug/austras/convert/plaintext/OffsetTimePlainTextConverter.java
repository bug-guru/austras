/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.OffsetTime;

@PlainText
public class OffsetTimePlainTextConverter extends AbstractPlainTextConverter<OffsetTime> {
    @Override
    public OffsetTime fromString(String value) {
        if (value == null) return null;
        return OffsetTime.parse(value);
    }

    @Override
    public String toString(OffsetTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
