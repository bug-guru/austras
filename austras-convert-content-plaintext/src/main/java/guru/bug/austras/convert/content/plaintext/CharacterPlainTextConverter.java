package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.CharacterContentConverter;

@PlainText
public class CharacterPlainTextConverter extends AbstractPlainTextConverter<Character> {
    private final CharacterContentConverter converter;

    public CharacterPlainTextConverter(@PlainText CharacterContentConverter converter) {
        this.converter = converter;
    }

    @Override
    public Character fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return converter.fromString(value);
    }

    @Override
    public String toString(Character value) {
        if (value == null) {
            return null;
        }
        return converter.toString(value);
    }
}
