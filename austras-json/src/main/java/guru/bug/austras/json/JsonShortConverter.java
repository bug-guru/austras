/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonShortDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonShortSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonShortConverter extends JsonShortSerializer, JsonShortDeserializer {

    @Override
    void toJson(short value, JsonValueWriter writer);

    @Override
    short fromJson(JsonValueReader reader);

}
