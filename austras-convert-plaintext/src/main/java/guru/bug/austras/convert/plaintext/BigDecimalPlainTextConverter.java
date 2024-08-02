/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.plaintext;

import java.math.BigDecimal;

@PlainText
public class BigDecimalPlainTextConverter extends AbstractPlainTextConverter<BigDecimal> {
    @Override
    public BigDecimal fromString(String value) {
        if (value == null) {
            return null;
        }
        return new BigDecimal(value);
    }

    @Override
    public String toString(BigDecimal obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

}
