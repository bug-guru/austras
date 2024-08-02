/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.InstantPlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstantJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Instant> converter = new InstantJsonConverter(new InstantPlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03T10:15:30Z\"",
                toJson(writer -> converter.toJson(Instant.parse("2007-12-03T10:15:30.00Z"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Instant.parse("2007-12-03T10:15:30.00Z"),
                converter.fromJson(reader("\"2007-12-03T10:15:30.00Z\"")));
    }

}