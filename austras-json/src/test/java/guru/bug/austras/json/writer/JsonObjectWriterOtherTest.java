/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonObjectWriterOtherTest extends JsonObjectWriterAbstractTest {

    @Test
    void writeMultipleKeys() {
        ow.writeInteger("key1" , 1);
        ow.writeInteger("key2", 2);
        assertEquals(p("key1", "1") + "," + p("key2", "2"), out.toString());
    }

    @Test
    void writeDuplicateKeys() {
        ow.writeInteger("key1", 1);
        ow.writeInteger("key2", 2);
        assertThrows(JsonWritingException.class, () -> ow.writeInteger("key2", 3));
    }

    @Test
    void writeNull() {
        ow.writeNull("x");
        assertEquals(p("x", "null"), out.toString());
    }
}