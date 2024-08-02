/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json;


import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class CharacterJsonConverter extends AbstractJsonConverter<Character> {
    @Override
    public void toJson(Character value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public Character fromJson(JsonValueReader reader) {
        return reader.readNullableChar();
    }

}
