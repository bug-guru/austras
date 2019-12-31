/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.AbstractJsonConverter;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public abstract class AbstractSetJsonConverter<T> extends AbstractJsonConverter<Set<T>> {
    private final JsonConverter<T> elementConverter;

    public AbstractSetJsonConverter(JsonConverter<T> elementConverter) {
        this.elementConverter = elementConverter;
    }

    @Override
    public void toJson(Set<T> value, JsonValueWriter writer) {
        writer.writeValueArray(value, elementConverter);
    }

    @Override
    public Set<T> fromJson(JsonValueReader reader) {
        return reader.readOptionalArray(elementConverter)
                .map(s -> s.collect(Collectors.toSet()))
                .orElse(null);
    }
}
