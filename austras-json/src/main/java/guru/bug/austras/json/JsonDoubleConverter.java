/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonDoubleDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonDoubleSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonDoubleConverter extends JsonDoubleSerializer, JsonDoubleDeserializer {

    @Override
    void toJson(double value, JsonValueWriter writer);

    @Override
    double fromJson(JsonValueReader reader);

}
