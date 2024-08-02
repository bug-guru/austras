/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.Instant;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfInstantJsonConverter extends AbstractSetJsonConverter<Instant> {

    public SetOfInstantJsonConverter(@ApplicationJson JsonConverter<Instant> elementConverter) {
        super(elementConverter);
    }

}
