/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.time.LocalDateTime;

@PlainText
public class LocalDateTimePlainTextConverter extends AbstractPlainTextConverter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String value) {
        if (value == null) return null;
        return LocalDateTime.parse(value);
    }

    @Override
    public String toString(LocalDateTime obj) {
        if (obj == null) return null;
        return obj.toString();
    }
}
