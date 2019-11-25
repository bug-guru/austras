package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterFloatTest extends JsonObjectWriterAbstractTest {
    private static final JsonFloatSerializer CUSTOM_PRIMITIVE_FLOAT_SERIALIZER = (v, w) -> {
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

    private static final JsonSerializer<Float> CUSTOM_OBJECT_FLOAT_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            CUSTOM_PRIMITIVE_FLOAT_SERIALIZER.toJson(v, w);
        }
    };

    @Test
    void writePrimitiveFloat1WithConverter() {
        ow.writeFloat("key", 1F, CUSTOM_PRIMITIVE_FLOAT_SERIALIZER);
        assertEquals(p("key", q("B")), out.toString());
    }

    @Test
    void writePrimitiveFloat2WithConverter() {
        ow.writeFloat("key", 0F, CUSTOM_PRIMITIVE_FLOAT_SERIALIZER);
        assertEquals(p("key", q("A")), out.toString());
    }

    @Test
    void writeObjectFloat1WithConverter() {
        ow.writeFloat("key", 2F, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectFloat2WithConverter() {
        ow.writeFloat("key", 100F, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", q("Z")), out.toString());
    }

    @Test
    void writeObjectFloatNullWithConverter() {
        ow.writeFloat("key", null, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writePrimitiveFloatArrayWithConverter() {
        ow.writeFloatArray("key", new float[]{0F, 1F, 2F, 3F}, CUSTOM_PRIMITIVE_FLOAT_SERIALIZER);
        assertEquals(p("key", "[\"A\",\"B\",\"C\",\"Z\"]"), out.toString());
    }

    @Test
    void writePrimitiveFloatArrayNullWithConverter() {
        ow.writeFloatArray("key", null, CUSTOM_PRIMITIVE_FLOAT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectFloatArrayWithConverter() {
        ow.writeFloatArray("key", new Float[]{2F, 0F, null}, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectFloatArrayNullWithConverter() {
        ow.writeFloatArray("key", (Float[]) null, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectFloatCollectionWithConverter() {
        ow.writeFloatArray("key", Arrays.asList(1F, 2F, null), CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectFloatCollectionNullWithConverter() {
        ow.writeFloatArray("key", (Collection<Float>) null, CUSTOM_OBJECT_FLOAT_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveFloat() {
        ow.writeFloat("key", 123F);
        assertEquals(p("key", "123.0"), out.toString());
    }

    @Test
    void writeObjectFloat() {
        ow.writeFloat("key", -128F);
        assertEquals(p("key", "-128.0"), out.toString());
    }

    @Test
    void writeObjectFloatNull() {
        ow.writeFloat("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writePrimitiveFloatArray() {
        ow.writeFloatArray("key", new float[]{-120F, 127F, 0F, 15F});
        assertEquals(p("key", "[-120.0,127.0,0.0,15.0]"), out.toString());
    }

    @Test
    void writePrimitiveFloatArrayNull() {
        ow.writeFloatArray("key", (float[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectFloatArray() {
        ow.writeFloatArray("key", new Float[]{1F, 2F, null});
        assertEquals(p("key", "[1.0,2.0,null]"), out.toString());
    }

    @Test
    void writeObjectFloatArrayNull() {
        ow.writeFloatArray("key", (Float[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectFloatCollection() {
        ow.writeFloatArray("key", Arrays.asList(null, 6F, null));
        assertEquals(p("key", "[null,6.0,null]"), out.toString());
    }

    @Test
    void writeObjectFloatCollectionNull() {
        ow.writeFloatArray("key", (Collection<Float>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}