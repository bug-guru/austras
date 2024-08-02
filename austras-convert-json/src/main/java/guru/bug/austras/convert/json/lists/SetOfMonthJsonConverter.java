/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfMonthJsonConverter extends AbstractSetJsonConverter<Month> {

    public SetOfMonthJsonConverter(@ApplicationJson JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
