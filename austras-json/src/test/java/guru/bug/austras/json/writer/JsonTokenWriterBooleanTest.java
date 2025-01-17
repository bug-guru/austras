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

public class JsonTokenWriterBooleanTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedBooleanTrue() {
        writer.writeBoolean(true);
        assertEquals("true", out.toString());
    }

    @Test
    void writeUnboxedBooleanFalse() {
        writer.writeBoolean(false);
        assertEquals("false", out.toString());
    }

    @Test
    void writeUnboxedBooleanException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBoolean(true));
    }

    @Test
    void writeBoxedBooleanTrue() {
        writer.writeBoolean(Boolean.TRUE);
        assertEquals("true", out.toString());
    }

    @Test
    void writeBoxedBooleanFalse() {
        writer.writeBoolean(Boolean.FALSE);
        assertEquals("false", out.toString());
    }

    @Test
    void writeBoxedBooleanNull() {
        writer.writeBoolean(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedBooleanException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBoolean(null));
    }

}
