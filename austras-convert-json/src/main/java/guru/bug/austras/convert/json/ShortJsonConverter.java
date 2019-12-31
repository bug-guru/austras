/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class ShortJsonConverter extends AbstractJsonConverter<Short> {
    @Override
    public void toJson(Short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public Short fromJson(JsonValueReader reader) {
        return reader.readNullableShort();
    }

}
