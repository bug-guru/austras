/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterBigDecimalTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeBigDecimal() {
        writer.writeBigDecimal(new BigDecimal("999999999999999999999999999999999999999999999999999999999999999999999999999E-999"));
        assertEquals("9.99999999999999999999999999999999999999999999999999999999999999999999999999E-925", out.toString());
    }

    @Test
    void writeBigDecimalNull() {
        writer.writeBigDecimal(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBigDecimalException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBigDecimal(BigDecimal.ZERO));
    }

}
