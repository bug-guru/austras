/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfBooleanJsonConverter extends AbstractListJsonConverter<Boolean> {

    public ListOfBooleanJsonConverter(@ApplicationJson JsonConverter<Boolean> elementConverter) {
        super(elementConverter);
    }

}
