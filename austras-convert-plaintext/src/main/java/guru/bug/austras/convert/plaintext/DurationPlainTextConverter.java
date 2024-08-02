/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.time.Duration;

@PlainText
public class DurationPlainTextConverter extends AbstractPlainTextConverter<Duration> {
    @Override
    public Duration fromString(String value) {
        if (value == null) {
            return null;
        }
        return Duration.parse(value);
    }

    @Override
    public String toString(Duration obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
