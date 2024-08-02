/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Integer> converter = new IntegerJsonConverter();

    @Test
    void toJson() {
        assertEquals("123",
                toJson(writer -> converter.toJson(123, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Integer.valueOf(123),
                converter.fromJson(reader("123")));
    }
}