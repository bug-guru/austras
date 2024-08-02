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

class JsonObjectWriterLongTest extends JsonObjectWriterAbstractTest {
    private static final JsonLongSerializer CUSTOM_PRIMITIVE_LONG_SERIALIZER = (v, w) -> {
        switch (Math.toIntExact(v)) {
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

    private static final JsonSerializer<Long> CUSTOM_OBJECT_LONG_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_LONG_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveLongWithConverter() {
        ow.writeLong("key", 1, CUSTOM_PRIMITIVE_LONG_SERIALIZER);
        assertEquals(p("key", q("B")), out.toString());
    }

    @Test
    void writeObjectLongWithConverter() {
        ow.writeLong("key", 2L, CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectLongNullWithConverter() {
        ow.writeLong("key", null, CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writePrimitiveLongArrayWithConverter() {
        ow.writeLongArray("key", new long[]{0, 1, 2, 3}, CUSTOM_PRIMITIVE_LONG_SERIALIZER);
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveLongArrayNullWithConverter() {
        ow.writeLongArray("key", null, CUSTOM_PRIMITIVE_LONG_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectLongArrayWithConverter() {
        ow.writeLongArray("key", new Long[]{2L, 0L, null}, CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectLongArrayNullWithConverter() {
        ow.writeLongArray("key", (Long[]) null, CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectLongCollectionWithConverter() {
        ow.writeLongArray("key", Arrays.asList(1L, 2L, null), CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectLongCollectionNullWithConverter() {
        ow.writeLongArray("key", (Collection<Long>) null, CUSTOM_OBJECT_LONG_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveLong() {
        ow.writeLong("key", 123);
        assertEquals(p("key", "123"), out.toString());
    }

    @Test
    void writeObjectLong() {
        ow.writeLong("key", -128);
        assertEquals(p("key", "-128"), out.toString());
    }

    @Test
    void writeObjectLongNull() {
        ow.writeLong("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveLongArray() {
        ow.writeLongArray("key", new long[]{(long) -120, (long) 127, (long) 0, (long) 15});
        assertEquals(p("key", "[-120,127,0,15]"), out.toString());
    }

    @Test
    void writePrimitiveLongArrayNull() {
        ow.writeLongArray("key", (long[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectLongArray() {
        ow.writeLongArray("key", new Long[]{(long) 1, (long) 2, null});
        assertEquals(p("key", "[1,2,null]"), out.toString());
    }

    @Test
    void writeObjectLongArrayNull() {
        ow.writeLongArray("key", (Long[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectLongCollection() {
        ow.writeLongArray("key", Arrays.asList(null, (long) 6, null));
        assertEquals(p("key", "[null,6,null]"), out.toString());
    }

    @Test
    void writeObjectLongCollectionNull() {
        ow.writeLongArray("key", (Collection<Long>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}