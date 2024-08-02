/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;


import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.math.BigInteger;

@ApplicationJson
public class BigIntegerJsonConverter extends AbstractJsonConverter<BigInteger> {
    @Override
    public void toJson(BigInteger value, JsonValueWriter writer) {
        writer.writeBigInteger(value);
    }

    @Override
    public BigInteger fromJson(JsonValueReader reader) {
        return reader.readNullableBigInteger();
    }

}
