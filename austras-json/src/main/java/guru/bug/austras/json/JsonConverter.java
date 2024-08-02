/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json;


import guru.bug.austras.json.reader.JsonDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonConverter<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    void toJson(T value, JsonValueWriter writer);

    @Override
    T fromJson(JsonValueReader reader);

}
