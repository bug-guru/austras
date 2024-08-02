/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BooleanJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Boolean> converter = new BooleanJsonConverter();

    @Test
    void toJson() {
        assertEquals("true", toJson(writer -> converter.toJson(Boolean.TRUE, writer)));
    }

    @Test
    void fromJson() {
        assertFalse(converter.fromJson(reader("false")));
    }

}