/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.plaintext.DurationPlainTextConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Duration> converter = new DurationJsonConverter(new DurationPlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"PT1M10S\"",
                toJson(writer -> converter.toJson(Duration.ofSeconds(70), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Duration.ofHours(1),
                converter.fromJson(reader("\"PT60M\"")));
    }

}