/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.math.BigInteger;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfBigIntegerJsonConverter extends AbstractSetJsonConverter<BigInteger> {

    public SetOfBigIntegerJsonConverter(@ApplicationJson JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
