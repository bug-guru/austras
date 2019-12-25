package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringCharacterConverter;
import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class CharacterToStringConverter implements StringConverter<Character> {
    private final StringCharacterConverter stringConverter;

    public CharacterToStringConverter(StringCharacterConverter stringConverter) {
        this.stringConverter = stringConverter;
    }

    @Override
    public Character fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return stringConverter.fromString(value);
    }

    @Override
    public String toString(Character value) {
        if (value == null) {
            return null;
        }
        return stringConverter.toString(value);
    }
}
