/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.FloatContentConverter;
import guru.bug.austras.json.JsonFloatConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveFloatJsonConverter implements FloatContentConverter, JsonFloatConverter {
    @Override
    public void toJson(float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public float fromJson(JsonValueReader reader) {
        return reader.readFloat();
    }

    @Override
    public float fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(float value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public float read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(float value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
