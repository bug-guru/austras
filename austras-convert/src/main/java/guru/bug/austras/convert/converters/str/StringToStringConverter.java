package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;

// TODO This class provided as temporary solution. PathParamModelGenerator will not request this converter in future.
public class StringToStringConverter implements StringConverter<String> {
    @Override
    public String fromString(String value) {
        return value;
    }

    @Override
    public String toString(String obj) {
        return obj;
    }
}
