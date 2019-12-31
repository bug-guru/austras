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

public class JsonTokenWriterOtherTest extends JsonTokenWriterAbstractTest {

    @Test
    void testCreateWithNullWriter() {
        assertThrows(NullPointerException.class, () -> new JsonTokenWriter(null));
    }

    @Test
    void writeRaw() {
        writer.writeRaw("abc\"");
        assertEquals("abc\"", out.toString());
    }

    @Test
    void writeRawException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeRaw("abc\""));
    }

    @Test
    void writeNull() {
        writer.writeNull();
        assertEquals("null", out.toString());
    }

    @Test
    void writeNullException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeNull());
    }

    @Test
    void writeBeginObject() {
        writer.writeBeginObject();
        assertEquals("{", out.toString());
    }

    @Test
    void writeBeginObjectException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBeginObject());
    }

    @Test
    void writeEndObject() {
        writer.writeEndObject();
        assertEquals("}", out.toString());
    }

    @Test
    void writeEndObjectException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeEndObject());
    }

    @Test
    void writeBeginArray() {
        writer.writeBeginArray();
        assertEquals("[", out.toString());
    }

    @Test
    void writeBeginArrayException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBeginArray());
    }

    @Test
    void writeEndArray() {
        writer.writeEndArray();
        assertEquals("]", out.toString());
    }

    @Test
    void writeEndArrayException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeEndArray());
    }

    @Test
    void writeColon() {
        writer.writeColon();
        assertEquals(":", out.toString());
    }

    @Test
    void writeColonException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeColon());
    }

    @Test
    void writeComma() {
        writer.writeComma();
        assertEquals(",", out.toString());
    }

    @Test
    void writeCommaException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeComma());
    }


}
