package guru.bug.austras.convert.engine.json.writer;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterBigDecimalTest extends JsonObjectWriterAbstractTest {
    private static final JsonSerializer<BigDecimal> CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            switch (v.intValue()) {
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
        }
    };

    @Test
    void writeObjectBigDecimalWithConverter() {
        ow.writeBigDecimal("key", BigDecimal.valueOf(2.9), CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectBigDecimalNullWithConverter() {
        ow.writeBigDecimal("key", null, CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writeObjectBigDecimalArrayWithConverter() {
        ow.writeBigDecimalArray("key", new BigDecimal[]{BigDecimal.valueOf(2.5), BigDecimal.ZERO, null}, CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectBigDecimalArrayNullWithConverter() {
        ow.writeBigDecimalArray("key", (BigDecimal[]) null, CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigDecimalCollectionWithConverter() {
        ow.writeBigDecimalArray("key", Arrays.asList(BigDecimal.ONE, BigDecimal.valueOf(2.5), null), CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectBigDecimalCollectionNullWithConverter() {
        ow.writeBigDecimalArray("key", (Collection<BigDecimal>) null, CUSTOM_OBJECT_BIG_DECIMAL_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigDecimal() {
        ow.writeBigDecimal("key", BigDecimal.valueOf(-128.33));
        assertEquals(p("key", "-128.33"), out.toString());
    }

    @Test
    void writeObjectBigDecimalNull() {
        ow.writeBigDecimal("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigDecimalArray() {
        ow.writeBigDecimalArray("key", new BigDecimal[]{BigDecimal.ONE, BigDecimal.valueOf(2.5), null});
        assertEquals(p("key", "[1,2.5,null]"), out.toString());
    }

    @Test
    void writeObjectBigDecimalArrayNull() {
        ow.writeBigDecimalArray("key", (BigDecimal[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigDecimalCollection() {
        ow.writeBigDecimalArray("key", Arrays.asList(null, BigDecimal.valueOf(6.1), null));
        assertEquals(p("key", "[null,6.1,null]"), out.toString());
    }

    @Test
    void writeObjectBigDecimalCollectionNull() {
        ow.writeBigDecimalArray("key", (Collection<BigDecimal>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}