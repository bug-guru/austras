package guru.bug.austras.convert.converters;

public interface StringBooleanConverter {

    boolean fromString(String value);

    String toString(boolean value);

}
