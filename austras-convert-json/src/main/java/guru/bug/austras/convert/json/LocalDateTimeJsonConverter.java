/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.LocalDateTime;

@ApplicationJson
public class LocalDateTimeJsonConverter extends AbstractJsonConverter<LocalDateTime> {
    private final ContentConverter<LocalDateTime> converter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateTimeJsonConverter(@PlainText ContentConverter<LocalDateTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(LocalDateTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
