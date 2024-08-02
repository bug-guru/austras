/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class IntegerJsonConverter extends AbstractJsonConverter<Integer> {
    @Override
    public void toJson(Integer value, JsonValueWriter writer) {
        writer.writeInteger(value);
    }

    @Override
    public Integer fromJson(JsonValueReader reader) {
        return reader.readNullableInt();
    }

}
