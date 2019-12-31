/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLocalDateJsonConverter extends AbstractListJsonConverter<LocalDate> {

    public ListOfLocalDateJsonConverter(@ApplicationJson JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
