/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.BooleanContentConverter;
import guru.bug.austras.json.JsonBooleanConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveBooleanJsonConverter implements JsonBooleanConverter, BooleanContentConverter {
    @Override
    public void toJson(boolean value, JsonValueWriter writer) {
        writer.writeBoolean(value);
    }

    @Override
    public boolean fromJson(JsonValueReader reader) {
        return reader.readBoolean();
    }

    @Override
    public boolean fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(boolean value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public boolean read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(boolean value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
