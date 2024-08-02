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

public class JsonTokenWriterLongTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedLongMax() {
        writer.writeLong(Long.MAX_VALUE);
        assertEquals(String.valueOf(Long.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedLongMin() {
        writer.writeLong(Long.MIN_VALUE);
        assertEquals(String.valueOf(Long.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedLongException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeLong(0L));
    }

    @Test
    void writeBoxedLongMax() {
        writer.writeLong(Long.valueOf(Long.MAX_VALUE));
        assertEquals(String.valueOf(Long.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedLongMin() {
        writer.writeLong(Long.valueOf(Long.MIN_VALUE));
        assertEquals(String.valueOf(Long.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedLongException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeLong(null));
    }

}
