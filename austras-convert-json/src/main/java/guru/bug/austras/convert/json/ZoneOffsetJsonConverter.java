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

import java.time.ZoneOffset;

@ApplicationJson
public class ZoneOffsetJsonConverter extends AbstractJsonConverter<ZoneOffset> {
    private final ContentConverter<ZoneOffset> converter;

    @SuppressWarnings("WeakerAccess")
    public ZoneOffsetJsonConverter(@PlainText ContentConverter<ZoneOffset> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(ZoneOffset value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public ZoneOffset fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
