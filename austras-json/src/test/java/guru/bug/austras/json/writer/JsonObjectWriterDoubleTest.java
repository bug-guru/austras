/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterDoubleTest extends JsonObjectWriterAbstractTest {
    private static final JsonDoubleSerializer CUSTOM_PRIMITIVE_DOUBLE_SERIALIZER = (v, w) -> {
        if (v < 0.9) {
            w.writeCharacter('A');
        } else if (v < 1.9) {
            w.writeCharacter('B');
        } else if (v < 2.9) {
            w.writeCharacter('C');
        } else {
            w.writeCharacter('Z');
        }
    };

    private static final JsonSerializer<Double> CUSTOM_OBJECT_DOUBLE_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_DOUBLE_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveDoubleWithConverter() {
        ow.writeDouble("key", 1.0, CUSTOM_PRIMITIVE_DOUBLE_SERIALIZER);
        assertEquals(p("key", q("B")), out.toString());
    }

    @Test
    void writeObjectDoubleWithConverter() {
        ow.writeDouble("key", 2.0, CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectDoubleNullWithConverter() {
        ow.writeDouble("key", null, CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writePrimitiveDoubleArrayWithConverter() {
        ow.writeDoubleArray("key", new double[]{0.0, 1.0, 2.0, 3.0}, CUSTOM_PRIMITIVE_DOUBLE_SERIALIZER);
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveDoubleArrayNullWithConverter() {
        ow.writeDoubleArray("key", null, CUSTOM_PRIMITIVE_DOUBLE_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectDoubleArrayWithConverter() {
        ow.writeDoubleArray("key", new Double[]{2.0, 0.0, null}, CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectDoubleArrayNullWithConverter() {
        ow.writeDoubleArray("key", (Double[]) null, CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectDoubleCollectionWithConverter() {
        ow.writeDoubleArray("key", Arrays.asList(1.0, 2.0, null), CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectDoubleCollectionNullWithConverter() {
        ow.writeDoubleArray("key", (Collection<Double>) null, CUSTOM_OBJECT_DOUBLE_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveDouble() {
        ow.writeDouble("key", 123.0);
        assertEquals(p("key", "123.0"), out.toString());
    }

    @Test
    void writeObjectDouble() {
        ow.writeDouble("key", -128.0);
        assertEquals(p("key", "-128.0"), out.toString());
    }

    @Test
    void writeObjectDoubleNull() {
        ow.writeDouble("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveDoubleArray() {
        ow.writeDoubleArray("key", new double[]{-120.0, 127.0, 0.0, 15.0});
        assertEquals(p("key", "[-120.0,127.0,0.0,15.0]"), out.toString());
    }

    @Test
    void writePrimitiveDoubleArrayNull() {
        ow.writeDoubleArray("key", (double[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectDoubleArray() {
        ow.writeDoubleArray("key", new Double[]{1.0, 2.0, null});
        assertEquals(p("key", "[1.0,2.0,null]"), out.toString());
    }

    @Test
    void writeObjectDoubleArrayNull() {
        ow.writeDoubleArray("key", (Double[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectDoubleCollection() {
        ow.writeDoubleArray("key", Arrays.asList(null, 6.0, null));
        assertEquals(p("key", "[null,6.0,null]"), out.toString());
    }

    @Test
    void writeObjectDoubleCollectionNull() {
        ow.writeDoubleArray("key", (Collection<Double>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}