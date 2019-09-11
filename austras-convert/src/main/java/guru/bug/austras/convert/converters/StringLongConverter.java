package guru.bug.austras.convert.converters;

public interface StringLongConverter {

    long fromString(String value);

    String toString(long value);

}
