/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterBigIntegerTest extends JsonObjectWriterAbstractTest {
    private static final JsonSerializer<BigInteger> CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER = (v, w) -> {
        if (v == null) {
            w.writeCharacter('x');
        } else {
            switch (v.intValueExact()) {
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
    void writeObjectBigIntegerWithConverter() {
        ow.writeBigInteger("key", BigInteger.valueOf(2), CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", q("C")), out.toString());
    }

    @Test
    void writeObjectBigIntegerNullWithConverter() {
        ow.writeBigInteger("key", null, CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", q("x")), out.toString());
    }

    @Test
    void writeObjectBigIntegerArrayWithConverter() {
        ow.writeBigIntegerArray("key", new BigInteger[]{BigInteger.valueOf(2), BigInteger.ZERO, null}, CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", "[\"C\",\"A\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectBigIntegerArrayNullWithConverter() {
        ow.writeBigIntegerArray("key", (BigInteger[]) null, CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigIntegerCollectionWithConverter() {
        ow.writeBigIntegerArray("key", Arrays.asList(BigInteger.ONE, BigInteger.valueOf(2), null), CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", "[\"B\",\"C\",\"x\"]"), out.toString());
    }

    @Test
    void writeObjectBigIntegerCollectionNullWithConverter() {
        ow.writeBigIntegerArray("key", (Collection<BigInteger>) null, CUSTOM_OBJECT_BIG_INTEGER_SERIALIZER);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigInteger() {
        ow.writeBigInteger("key", BigInteger.valueOf(-128));
        assertEquals(p("key", "-128"), out.toString());
    }

    @Test
    void writeObjectBigIntegerNull() {
        ow.writeBigInteger("key", null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigIntegerArray() {
        ow.writeBigIntegerArray("key", new BigInteger[]{BigInteger.ONE, BigInteger.valueOf(2), null});
        assertEquals(p("key", "[1,2,null]"), out.toString());
    }

    @Test
    void writeObjectBigIntegerArrayNull() {
        ow.writeBigIntegerArray("key", (BigInteger[]) null);
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeObjectBigIntegerCollection() {
        ow.writeBigIntegerArray("key", Arrays.asList(null, BigInteger.valueOf(6), null));
        assertEquals(p("key", "[null,6,null]"), out.toString());
    }

    @Test
    void writeObjectBigIntegerCollectionNull() {
        ow.writeBigIntegerArray("key", (Collection<BigInteger>) null);
        assertEquals(p("key", "null"), out.toString());
    }

}