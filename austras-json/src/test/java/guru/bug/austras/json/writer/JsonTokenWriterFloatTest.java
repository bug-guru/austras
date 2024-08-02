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

public class JsonTokenWriterFloatTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedFloatMax() {
        writer.writeFloat(Float.MAX_VALUE);
        assertEquals(String.valueOf(Float.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedFloatMin() {
        writer.writeFloat(Float.MIN_VALUE);
        assertEquals(String.valueOf(Float.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedFloatNaN() {
        writer.writeFloat(Float.NaN);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedFloatPositiveInfinity() {
        writer.writeFloat(Float.POSITIVE_INFINITY);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedFloatNegativeInfinity() {
        writer.writeFloat(Float.NEGATIVE_INFINITY);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedFloatFloatException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeFloat(0F));
    }

    @Test
    void writeBoxedFloatMax() {
        writer.writeFloat(Float.valueOf(Float.MAX_VALUE));
        assertEquals(String.valueOf(Float.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedFloatMin() {
        writer.writeFloat(Float.valueOf(Float.MIN_VALUE));
        assertEquals(String.valueOf(Float.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedFloatNaN() {
        writer.writeFloat(Float.valueOf(Float.NaN));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedFloatPositiveInfinity() {
        writer.writeFloat(Float.valueOf(Float.POSITIVE_INFINITY));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedFloatNegativeInfinity() {
        writer.writeFloat(Float.valueOf(Float.NEGATIVE_INFINITY));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedFloatException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeFloat(null));
    }

}
