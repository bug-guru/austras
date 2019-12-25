package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.qualifiers.Default;

// TODO This class provided as temporary solution. PathParamModelGenerator will not request this converter in future.

@Default
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
