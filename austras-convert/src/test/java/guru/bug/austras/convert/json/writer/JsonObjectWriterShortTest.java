package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterShortTest extends JsonObjectWriterAbstractTest {
    private static final JsonShortSerializer CUSTOM_PRIMITIVE_SHORT_SERIALIZER = (v, w) -> {
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

    private static final JsonSerializer<Short> CUSTOM_OBJECT_SHORT_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_SHORT_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveShortWithConverter() {
        ow.writeShort("key", (short) 1, CUSTOM_PRIMITIVE_SHORT_SERIALIZER);
        assertEquals(p("key", q("B")), out.toString());
    }

    @Test
    void writeObjectShortWithConverter() {
        ow.writeShort("key", (short) 2, CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectShortNullWithConverter() {
        ow.writeShort("key", null, CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writePrimitiveShortArrayWithConverter() {
        ow.writeShortArray("key", new short[]{0, 1, 2, 3}, CUSTOM_PRIMITIVE_SHORT_SERIALIZER);
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveShortArrayNullWithConverter() {
        ow.writeShortArray("key", null, CUSTOM_PRIMITIVE_SHORT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectShortArrayWithConverter() {
        ow.writeShortArray("key", new Short[]{2, 0, null}, CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectShortArrayNullWithConverter() {
        ow.writeShortArray("key", (Short[]) null, CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectShortCollectionWithConverter() {
        ow.writeShortArray("key", Arrays.asList((short) 1, (short) 2, null), CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectShortCollectionNullWithConverter() {
        ow.writeShortArray("key", (Collection<Short>) null, CUSTOM_OBJECT_SHORT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveShort() {
        ow.writeShort("key", (short) 123);
        assertEquals(p("key", "123"), out.toString());
    }

    @Test
    void writeObjectShort() {
        ow.writeShort("key", (short) -128);
        assertEquals(p("key", "-128"), out.toString());
    }

    @Test
    void writeObjectShortNull() {
        ow.writeShort("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveShortArray() {
        ow.writeShortArray("key", new short[]{(short) -120, (short) 127, (short) 0, (short) 15});
        assertEquals(p("key", "[-120,127,0,15]"), out.toString());
    }

    @Test
    void writePrimitiveShortArrayNull() {
        ow.writeShortArray("key", (short[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectShortArray() {
        ow.writeShortArray("key", new Short[]{(short) 1, (short) 2, null});
        assertEquals(p("key", "[1,2,null]"), out.toString());
    }

    @Test
    void writeObjectShortArrayNull() {
        ow.writeShortArray("key", (Short[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectShortCollection() {
        ow.writeShortArray("key", Arrays.asList(null, (short) 6, null));
        assertEquals(p("key", "[null,6,null]"), out.toString());
    }

    @Test
    void writeObjectShortCollectionNull() {
        ow.writeShortArray("key", (Collection<Short>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}