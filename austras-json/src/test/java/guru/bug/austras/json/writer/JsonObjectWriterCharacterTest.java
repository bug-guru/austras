/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterCharacterTest extends JsonObjectWriterAbstractTest {
    private static final JsonCharacterSerializer CUSTOM_PRIMITIVE_CHARACTER_SERIALIZER = (v, w) -> {
        switch (v) {
            case 'A':
                w.writeCharacter('Z');
                break;
            case 'B':
                w.writeCharacter('Y');
                break;
            case 'C':
                w.writeCharacter('X');
                break;
            default:
                w.writeCharacter(v);
        }
    };

    private static final JsonSerializer<Character> CUSTOM_OBJECT_CHARACTER_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeString("nothing");
        } else {
            CUSTOM_PRIMITIVE_CHARACTER_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveCharacterWithConverter() {
        ow.writeCharacter("key", 'A', CUSTOM_PRIMITIVE_CHARACTER_SERIALIZER);
        assertEquals(p("key", q("Z")), out.toString());
    }

    @Test
    void writeObjectCharacterWithConverter() {
        ow.writeCharacter("key", 'B', CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", q("Y")), out.toString());
    }

    @Test
    void writeObjectCharacterNullWithConverter() {
        ow.writeCharacter("key", null, CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", q("nothing")), out.toString());
    }

    @Test
    void writePrimitiveCharacterArrayWithConverter() {
        ow.writeCharacterArray("key", new char[]{'A', 'B', 'C', 'D'}, CUSTOM_PRIMITIVE_CHARACTER_SERIALIZER);
        assertEquals(p("key", "[\"Z\",\"Y\",\"X\",\"D\"]"), out.toString());
    }

    @Test
    void writePrimitiveCharacterArrayNullWithConverter() {
        ow.writeCharacterArray("key", null, CUSTOM_PRIMITIVE_CHARACTER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectCharacterArrayWithConverter() {
        ow.writeCharacterArray("key", new Character[]{'B', 'A', null}, CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", "[\"Y\",\"Z\",\"nothing\"]"), out.toString());
    }

    @Test
    void writeObjectCharacterArrayNullWithConverter() {
        ow.writeCharacterArray("key", (Character[]) null, CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectCharacterCollectionWithConverter() {
        ow.writeCharacterArray("key", Arrays.asList('A', 'B', null), CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", "[\"Z\",\"Y\",\"nothing\"]"), out.toString());
    }

    @Test
    void writeObjectCharacterCollectionNullWithConverter() {
        ow.writeCharacterArray("key", (Collection<Character>) null, CUSTOM_OBJECT_CHARACTER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveCharacter() {
        ow.writeCharacter("key", 'A');
        assertEquals(p("key", "\"A\""), out.toString());
    }

    @Test
    void writeObjectCharacter() {
        ow.writeCharacter("key", Character.valueOf('B'));
        assertEquals(p("key", "\"B\""), out.toString());
    }

    @Test
    void writeObjectCharacterNull() {
        ow.writeCharacter("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveCharacterArray() {
        ow.writeCharacterArray("key", new char[]{'A', 'B', 'C', 'D'});
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"D\"]"), out.toString());
    }

    @Test
    void writePrimitiveCharacterArrayNull() {
        ow.writeCharacterArray("key", (char[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectCharacterArray() {
        ow.writeCharacterArray("key", new Character[]{'Z', 'Y', null});
        assertEquals(p("key", "[\"Z\",\"Y\",null]"), out.toString());
    }

    @Test
    void writeObjectCharacterArrayNull() {
        ow.writeCharacterArray("key", (Character[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectCharacterCollection() {
        ow.writeCharacterArray("key", Arrays.asList(null, '6', null));
        assertEquals(p("key", "[null,\"6\",null]"), out.toString());
    }

    @Test
    void writeObjectCharacterCollectionNull() {
        ow.writeCharacterArray("key", (Collection<Character>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}