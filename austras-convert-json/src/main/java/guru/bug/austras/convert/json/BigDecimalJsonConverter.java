/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;


import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.math.BigDecimal;

@ApplicationJson
public class BigDecimalJsonConverter extends AbstractJsonConverter<BigDecimal> {
    @Override
    public void toJson(BigDecimal value, JsonValueWriter writer) {
        writer.writeBigDecimal(value);
    }

    @Override
    public BigDecimal fromJson(JsonValueReader reader) {
        return reader.readNullableBigDecimal();
    }

}
