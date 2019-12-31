/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import java.time.Instant;

@PlainText
public class InstantPlainTextConverter extends AbstractPlainTextConverter<Instant> {
    @Override
    public Instant fromString(String value) {
        if (value == null) return null;
        return Instant.parse(value);
    }

    @Override
    public String toString(Instant obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
