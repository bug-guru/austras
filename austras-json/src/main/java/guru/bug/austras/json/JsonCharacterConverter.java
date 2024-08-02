/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonCharacterDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonCharacterSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonCharacterConverter extends JsonCharacterSerializer, JsonCharacterDeserializer {

    @Override
    void toJson(char value, JsonValueWriter writer);

    @Override
    char fromJson(JsonValueReader reader);

}
