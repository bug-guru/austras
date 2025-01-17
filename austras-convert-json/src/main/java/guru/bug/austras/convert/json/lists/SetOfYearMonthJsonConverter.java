/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfYearMonthJsonConverter extends AbstractSetJsonConverter<YearMonth> {

    public SetOfYearMonthJsonConverter(@ApplicationJson JsonConverter<YearMonth> elementConverter) {
        super(elementConverter);
    }

}
