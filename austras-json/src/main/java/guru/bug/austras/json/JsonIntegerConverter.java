/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonIntegerDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonIntegerSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonIntegerConverter extends JsonIntegerSerializer, JsonIntegerDeserializer {

    @Override
    void toJson(int value, JsonValueWriter writer);

    @Override
    int fromJson(JsonValueReader reader);

}
