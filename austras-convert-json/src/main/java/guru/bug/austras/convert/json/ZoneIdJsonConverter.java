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

import java.time.ZoneId;

@ApplicationJson
public class ZoneIdJsonConverter extends AbstractJsonConverter<ZoneId> {
    private final ContentConverter<ZoneId> converter;

    @SuppressWarnings("WeakerAccess")
    public ZoneIdJsonConverter(@PlainText ContentConverter<ZoneId> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(ZoneId value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneId fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
