package guru.bug.austras.convert.converters;

public interface StringByteConverter {

    byte fromString(String value);

    String toString(byte value);

}
