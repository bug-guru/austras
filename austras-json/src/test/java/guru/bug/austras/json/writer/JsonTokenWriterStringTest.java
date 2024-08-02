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

public class JsonTokenWriterStringTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeStringEng() {
        writer.writeString("Hello!");
        assertEquals("\"Hello!\"", out.toString());
    }

    @Test
    void writeStringRus() {
        writer.writeString("По русски");
        assertEquals("\"По русски\"", out.toString());
    }

    @Test
    void testStringNull() {
        writer.writeString(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeStringSymbols() {
        writer.writeString("$¢ह€㒨");
        assertEquals("\"$¢ह€㒨\"", out.toString());
    }

    @Test
    void writeStringEscapeSurrogates() {
        writer.writeString("\uD800\uDF48\uD83D\uDE02\uD841\uDC57");
        assertEquals("\"\uD800\uDF48\uD83D\uDE02\uD841\uDC57\"", out.toString());
    }

    @Test
    void writeStringEscapeSpec() {
        writer.writeString("a\rb\n\t\\/\b");
        assertEquals("\"a\\rb\\n\\t\\\\/\\b\"", out.toString());
    }

    @Test
    void writeStringException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeString("abc\""));
    }

}
