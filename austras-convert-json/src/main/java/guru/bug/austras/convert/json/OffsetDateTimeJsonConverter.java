/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.plaintext.PlainText;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.time.OffsetDateTime;

@ApplicationJson
public class OffsetDateTimeJsonConverter extends AbstractJsonConverter<OffsetDateTime> {
    private final ContentConverter<OffsetDateTime> converter;

    @SuppressWarnings("WeakerAccess")
    public OffsetDateTimeJsonConverter(@PlainText ContentConverter<OffsetDateTime> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(OffsetDateTime value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public OffsetDateTime fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
