/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterByteTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedByteMax() {
        writer.writeByte(Byte.MAX_VALUE);
        assertEquals(String.valueOf(Byte.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedByteMin() {
        writer.writeByte(Byte.MIN_VALUE);
        assertEquals(String.valueOf(Byte.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedByteException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeByte((byte) 0));
    }

    @Test
    void writeBoxedByteMax() {
        writer.writeByte(Byte.valueOf(Byte.MAX_VALUE));
        assertEquals(String.valueOf(Byte.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedByteMin() {
        writer.writeByte(Byte.valueOf(Byte.MIN_VALUE));
        assertEquals(String.valueOf(Byte.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedByteNull() {
        writer.writeByte(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedByteException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeByte(null));
    }

}
