/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
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
public class ListOfBigIntegerJsonConverter extends AbstractListJsonConverter<BigInteger> {

    public ListOfBigIntegerJsonConverter(@ApplicationJson JsonConverter<BigInteger> elementConverter) {
        super(elementConverter);
    }

}
