/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfZoneIdJsonConverter extends AbstractListJsonConverter<ZoneId> {

    public ListOfZoneIdJsonConverter(@ApplicationJson JsonConverter<ZoneId> elementConverter) {
        super(elementConverter);
    }

}
