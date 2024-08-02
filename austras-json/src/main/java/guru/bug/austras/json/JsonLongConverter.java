/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonLongDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonLongSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonLongConverter extends JsonLongSerializer, JsonLongDeserializer {

    @Override
    void toJson(long value, JsonValueWriter writer);

    @Override
    long fromJson(JsonValueReader reader);

}
