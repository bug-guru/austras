/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.LocalTimePlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalTimeJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalTime> converter = new LocalTimeJsonConverter(new LocalTimePlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"10:15:30\"",
                toJson(writer -> converter.toJson(LocalTime.parse("10:15:30.00"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalTime.parse("10:15:30.00"),
                converter.fromJson(reader("\"10:15:30.00\"")));
    }

}