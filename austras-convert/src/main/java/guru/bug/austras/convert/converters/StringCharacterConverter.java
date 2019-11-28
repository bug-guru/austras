package guru.bug.austras.convert.converters;

public interface StringCharacterConverter {

    char fromString(String value);

    String toString(char value);

}
