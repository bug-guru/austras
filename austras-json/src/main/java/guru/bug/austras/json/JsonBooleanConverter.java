/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.json;


import guru.bug.austras.json.reader.JsonBooleanDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonBooleanSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonBooleanConverter extends JsonBooleanSerializer, JsonBooleanDeserializer {

    @Override
    void toJson(boolean value, JsonValueWriter writer);

    @Override
    boolean fromJson(JsonValueReader reader);

}
