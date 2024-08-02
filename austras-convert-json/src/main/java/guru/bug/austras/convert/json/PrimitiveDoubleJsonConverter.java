/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;

import guru.bug.austras.convert.DoubleContentConverter;
import guru.bug.austras.json.JsonDoubleConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveDoubleJsonConverter implements DoubleContentConverter, JsonDoubleConverter {
    @Override
    public void toJson(double value, JsonValueWriter writer) {
        writer.writeDouble(value);
    }

    @Override
    public double fromJson(JsonValueReader reader) {
        return reader.readDouble();
    }

    @Override
    public double fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(double value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public double read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(double value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
