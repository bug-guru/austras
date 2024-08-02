/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class ByteJsonConverter extends AbstractJsonConverter<Byte> {
    @Override
    public void toJson(Byte value, JsonValueWriter writer) {
        writer.writeByte(value);
    }

    @Override
    public Byte fromJson(JsonValueReader reader) {
        return reader.readNullableByte();
    }

}
