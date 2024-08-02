/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.ZonedDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfZonedDateTimeJsonConverter extends AbstractSetJsonConverter<ZonedDateTime> {

    public SetOfZonedDateTimeJsonConverter(@ApplicationJson JsonConverter<ZonedDateTime> elementConverter) {
        super(elementConverter);
    }

}
