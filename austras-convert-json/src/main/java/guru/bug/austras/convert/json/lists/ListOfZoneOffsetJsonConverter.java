/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.ZoneOffset;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfZoneOffsetJsonConverter extends AbstractListJsonConverter<ZoneOffset> {

    public ListOfZoneOffsetJsonConverter(@ApplicationJson JsonConverter<ZoneOffset> elementConverter) {
        super(elementConverter);
    }

}
