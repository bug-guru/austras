/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigIntegerJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<BigInteger> converter = new BigIntegerJsonConverter();

    @Test
    void toJson() {
        assertEquals("123456",
                toJson(writer -> converter.toJson(BigInteger.valueOf(123456), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(BigInteger.valueOf(1234567890),
                converter.fromJson(reader("1234567890")));
    }
}