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

class JsonObjectWriterByteTest extends JsonObjectWriterAbstractTest {
    private static final JsonByteSerializer CUSTOM_PRIMITIVE_BYTE_SERIALIZER = (v, w) -> {
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

    private static final JsonSerializer<Byte> CUSTOM_OBJECT_BYTE_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_BYTE_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveByteWithConverter() {
        ow.writeByte("key" , (byte) 1, CUSTOM_PRIMITIVE_BYTE_SERIALIZER);
        assertEquals(p("key" , q("B")), out.toString());
    }

    @Test
    void writeObjectByteWithConverter() {
        ow.writeByte("key" , (byte) 2, CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , q("C")), out.toString());
    }

    @Test
    void writeObjectByteNullWithConverter() {
        ow.writeByte("key" , null, CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , q("x")), out.toString());
    }

    @Test
    void writePrimitiveByteArrayWithConverter() {
        ow.writeByteArray("key" , new byte[]{0, 1, 2, 3}, CUSTOM_PRIMITIVE_BYTE_SERIALIZER);
        assertEquals(p("key" , "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveByteArrayNullWithConverter() {
        ow.writeByteArray("key" , null, CUSTOM_PRIMITIVE_BYTE_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectByteArrayWithConverter() {
        ow.writeByteArray("key" , new Byte[]{2, 0, null}, CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectByteArrayNullWithConverter() {
        ow.writeByteArray("key" , (Byte[]) null, CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectByteCollectionWithConverter() {
        ow.writeByteArray("key" , Arrays.asList((byte) 1, (byte) 2, null), CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectByteCollectionNullWithConverter() {
        ow.writeByteArray("key" , (Collection<Byte>) null, CUSTOM_OBJECT_BYTE_SERIALIZER);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writePrimitiveByte() {
        ow.writeByte("key" , (byte) 123);
        assertEquals(p("key" , "123"), out.toString());
    }

    @Test
    void writeObjectByte() {
        ow.writeByte("key" , (byte) -128);
        assertEquals(p("key" , "-128"), out.toString());
    }

    @Test
    void writeObjectByteNull() {
        ow.writeByte("key" , null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writePrimitiveByteArray() {
        ow.writeByteArray("key" , new byte[]{(byte) -120, (byte) 127, (byte) 0, (byte) 15});
        assertEquals(p("key" , "[-120,127,0,15]"), out.toString());
    }

    @Test
    void writePrimitiveByteArrayNull() {
        ow.writeByteArray("key" , (byte[]) null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectByteArray() {
        ow.writeByteArray("key" , new Byte[]{(byte) 1, (byte) 2, null});
        assertEquals(p("key" , "[1,2,null]"), out.toString());
    }

    @Test
    void writeObjectByteArrayNull() {
        ow.writeByteArray("key" , (Byte[]) null);
        assertEquals(p("key" , "null"), out.toString());
    }

    @Test
    void writeObjectByteCollection() {
        ow.writeByteArray("key" , Arrays.asList(null, (byte) 6, null));
        assertEquals(p("key" , "[null,6,null]"), out.toString());
    }

    @Test
    void writeObjectByteCollectionNull() {
        ow.writeByteArray("key" , (Collection<Byte>) null);
        assertEquals(p("key" , "null"), out.toString());
    }

}