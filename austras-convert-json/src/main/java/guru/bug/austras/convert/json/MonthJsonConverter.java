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

import java.time.Month;

@ApplicationJson
public class MonthJsonConverter extends AbstractJsonConverter<Month> {
    private final ContentConverter<Month> converter;

    @SuppressWarnings("WeakerAccess")
    public MonthJsonConverter(@PlainText ContentConverter<Month> converter) {
        this.converter = converter;
    }

    @Override
    public void toJson(Month value, JsonValueWriter writer) {
        var str = converter.toString(value);
        writer.writeString(str);
    }

    @Override
    public Month fromJson(JsonValueReader reader) {
        var str = reader.readNullableString();
        return converter.fromString(str);
    }

}
