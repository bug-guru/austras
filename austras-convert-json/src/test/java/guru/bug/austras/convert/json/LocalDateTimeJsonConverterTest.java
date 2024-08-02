/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.LocalDateTimePlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTimeJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalDateTime> converter = new LocalDateTimeJsonConverter(new LocalDateTimePlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03T10:15:30\"",
                toJson(writer -> converter.toJson(LocalDateTime.parse("2007-12-03T10:15:30"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalDateTime.parse("2007-12-03T10:15:30"),
                converter.fromJson(reader("\"2007-12-03T10:15:30\"")));
    }

}