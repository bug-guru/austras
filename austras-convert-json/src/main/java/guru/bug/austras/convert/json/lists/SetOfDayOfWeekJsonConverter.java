/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfDayOfWeekJsonConverter extends AbstractSetJsonConverter<DayOfWeek> {

    public SetOfDayOfWeekJsonConverter(@ApplicationJson JsonConverter<DayOfWeek> elementConverter) {
        super(elementConverter);
    }

}
