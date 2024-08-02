/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.LocalDatePlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalDate> converter = new LocalDateJsonConverter(new LocalDatePlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03\"",
                toJson(writer -> converter.toJson(LocalDate.parse("2007-12-03"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalDate.parse("2007-12-03"),
                converter.fromJson(reader("\"2007-12-03\"")));
    }

}