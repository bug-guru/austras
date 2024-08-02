/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterIntegerTest extends JsonObjectWriterAbstractTest {
    private static final JsonIntegerSerializer CUSTOM_PRIMITIVE_INTEGER_SERIALIZER = (v, w) -> {
        switch (v) {
            case 0:
                w.writeCharacter('A');
                break;
            case 1:
                w.writeCharacter('B');
                break;
            case 2:
                w.writeCharacter('C');
                break;
            default:
                w.writeCharacter('Z');
        }
    };

    private static final JsonSerializer<Integer> CUSTOM_OBJECT_INTEGER_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_INTEGER_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveIntegerWithConverter() {
        ow.writeInteger("key", 1, CUSTOM_PRIMITIVE_INTEGER_SERIALIZER);
        assertEquals(p("key", q("B")), out.toString());
    }

    @Test
    void writeObjectIntegerWithConverter() {
        ow.writeInteger("key", 2, CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectIntegerNullWithConverter() {
        ow.writeInteger("key", null, CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writePrimitiveIntegerArrayWithConverter() {
        ow.writeIntegerArray("key", new int[]{0, 1, 2, 3}, CUSTOM_PRIMITIVE_INTEGER_SERIALIZER);
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveIntegerArrayNullWithConverter() {
        ow.writeIntegerArray("key", null, CUSTOM_PRIMITIVE_INTEGER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectIntegerArrayWithConverter() {
        ow.writeIntegerArray("key", new Integer[]{2, 0, null}, CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectIntegerArrayNullWithConverter() {
        ow.writeIntegerArray("key", (Integer[]) null, CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectIntegerCollectionWithConverter() {
        ow.writeIntegerArray("key", Arrays.asList(1, 2, null), CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectIntegerCollectionNullWithConverter() {
        ow.writeIntegerArray("key", (Collection<Integer>) null, CUSTOM_OBJECT_INTEGER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveInteger() {
        ow.writeInteger("key", 123);
        assertEquals(p("key", "123"), out.toString());
    }

    @Test
    void writeObjectInteger() {
        ow.writeInteger("key", -128);
        assertEquals(p("key", "-128"), out.toString());
    }

    @Test
    void writeObjectIntegerNull() {
        ow.writeInteger("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveIntegerArray() {
        ow.writeIntegerArray("key", new int[]{-120, 127, 0, 15});
        assertEquals(p("key", "[-120,127,0,15]"), out.toString());
    }

    @Test
    void writePrimitiveIntegerArrayNull() {
        ow.writeIntegerArray("key", (int[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectIntegerArray() {
        ow.writeIntegerArray("key", new Integer[]{1, 2, null});
        assertEquals(p("key", "[1,2,null]"), out.toString());
    }

    @Test
    void writeObjectIntegerArrayNull() {
        ow.writeIntegerArray("key", (Integer[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectIntegerCollection() {
        ow.writeIntegerArray("key", Arrays.asList(null, 6, null));
        assertEquals(p("key", "[null,6,null]"), out.toString());
    }

    @Test
    void writeObjectIntegerCollectionNull() {
        ow.writeIntegerArray("key", (Collection<Integer>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}