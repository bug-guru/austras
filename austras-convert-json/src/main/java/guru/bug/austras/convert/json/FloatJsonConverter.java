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
public class FloatJsonConverter extends AbstractJsonConverter<Float> {
    @Override
    public void toJson(Float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public Float fromJson(JsonValueReader reader) {
        return reader.readNullableFloat();
    }

}
