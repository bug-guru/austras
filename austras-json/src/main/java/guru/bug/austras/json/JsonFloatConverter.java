/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonFloatDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonFloatSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonFloatConverter extends JsonFloatSerializer, JsonFloatDeserializer {

    @Override
    void toJson(float value, JsonValueWriter writer);

    @Override
    float fromJson(JsonValueReader reader);

}
