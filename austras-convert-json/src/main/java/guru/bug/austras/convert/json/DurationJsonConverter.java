/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.Duration;

@ApplicationJson
public class DurationJsonConverter extends AbstractJsonConverter<Duration> {
    private final ContentConverter<Duration> converter;

    @SuppressWarnings("WeakerAccess")
    public DurationJsonConverter(@PlainText ContentConverter<Duration> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Duration value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Duration fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
