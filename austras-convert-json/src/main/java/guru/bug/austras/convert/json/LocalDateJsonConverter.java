/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.LocalDate;

@ApplicationJson
public class LocalDateJsonConverter extends AbstractJsonConverter<LocalDate> {
    private final ContentConverter<LocalDate> converter;

    @SuppressWarnings("WeakerAccess")
    public LocalDateJsonConverter(@PlainText ContentConverter<LocalDate> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(LocalDate value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public LocalDate fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }
}
