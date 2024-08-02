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

import java.util.UUID;

@ApplicationJson
public class UUIDJsonConverter extends AbstractJsonConverter<UUID> {
    private final ContentConverter<UUID> converter;

    @SuppressWarnings("WeakerAccess")
    public UUIDJsonConverter(@PlainText ContentConverter<UUID> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(UUID value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public UUID fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
