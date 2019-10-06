package guru.bug.austras.codetempl;

public interface ValueConverter<T> {

    Value convert(T obj);

}
