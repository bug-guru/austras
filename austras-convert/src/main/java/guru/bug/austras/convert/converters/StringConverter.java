package guru.bug.austras.convert.converters;

public interface StringConverter<T> {

    T fromString(String value);

    String toString(T obj);

}
