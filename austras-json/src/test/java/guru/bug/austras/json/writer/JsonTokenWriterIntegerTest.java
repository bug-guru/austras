/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterIntegerTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedIntegerMax() {
        writer.writeInteger(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedIntegerMin() {
        writer.writeInteger(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedIntegerException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeInteger(0));
    }

    @Test
    void writeBoxedIntegerMax() {
        writer.writeInteger(Integer.valueOf(Integer.MAX_VALUE));
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedIntegerMin() {
        writer.writeInteger(Integer.valueOf(Integer.MIN_VALUE));
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedIntegerNull() {
        writer.writeInteger(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedIntegerException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeInteger(null));
    }

}
