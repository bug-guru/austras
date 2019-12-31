/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Float> converter = new FloatJsonConverter();

    @Test
    void toJson() {
        assertEquals("123.0",
                toJson(writer -> converter.toJson(123F, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Float.valueOf(123F),
                converter.fromJson(reader("123")));
    }
}