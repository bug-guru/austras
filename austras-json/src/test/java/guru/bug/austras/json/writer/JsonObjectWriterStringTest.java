/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterStringTest extends JsonObjectWriterAbstractTest {
    private static final JsonSerializer<String> CUSTOM_OBJECT_STRING_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeString("nothing");
        } else if (v.isBlank()) {
            w.writeString("empty");
        } else {
            w.writeString(new StringBuilder(v).reverse().toString());
        }
    };

    @Test
    void writeObjectStringWithConverter() {
        ow.writeString("key", "two", CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", q("owt")), out.toString());
    }

    @Test
    void writeObjectStringNullWithConverter() {
        ow.writeString("key", null, CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", q("nothing")), out.toString());
    }

    @Test
    void writeObjectStringArrayWithConverter() {
        ow.writeStringArray("key", new String[]{"Hello, World!", "", null}, CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", "[\"!dlroW ,olleH\",\"empty\",\"nothing\"]"), out.toString());
    }

    @Test
    void writeObjectStringArrayNullWithConverter() {
        ow.writeStringArray("key", (String[]) null, CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectStringCollectionWithConverter() {
        ow.writeStringArray("key", Arrays.asList("one", "two", null), CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", "[\"eno\",\"owt\",\"nothing\"]"), out.toString());
    }

    @Test
    void writeObjectStringCollectionNullWithConverter() {
        ow.writeStringArray("key", (Collection<String>) null, CUSTOM_OBJECT_STRING_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectString() {
        ow.writeString("key", "Check");
        assertEquals(p("key", "\"Check\""), out.toString());
    }

    @Test
    void writeObjectStringNull() {
        ow.writeString("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectStringArray() {
        ow.writeStringArray("key", new String[]{"one", "two", null});
        assertEquals(p("key", "[\"one\",\"two\",null]"), out.toString());
    }

    @Test
    void writeObjectStringArrayNull() {
        ow.writeStringArray("key", (String[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectStringCollection() {
        ow.writeStringArray("key", Arrays.asList(null, "six", null));
        assertEquals(p("key", "[null,\"six\",null]"), out.toString());
    }

    @Test
    void writeObjectStringCollectionNull() {
        ow.writeStringArray("key", (Collection<String>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}