/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.DayOfWeekPlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayOfWeekJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<DayOfWeek> converter = new DayOfWeekJsonConverter(new DayOfWeekPlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"FRIDAY\"",
                toJson(writer -> converter.toJson(DayOfWeek.FRIDAY, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(DayOfWeek.MONDAY,
                converter.fromJson(reader("\"MONDAY\"")));
    }

}