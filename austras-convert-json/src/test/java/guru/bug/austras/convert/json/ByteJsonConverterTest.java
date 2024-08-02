/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Byte> converter = new ByteJsonConverter();

    @Test
    void toJson() {
        assertEquals("123",
                toJson(writer -> converter.toJson((byte) 123, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Byte.valueOf((byte) 123),
                converter.fromJson(reader("123")));
    }

}