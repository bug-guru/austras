package guru.bug.austras.convert.converters.str;

import guru.bug.austras.convert.converters.StringConverter;
import guru.bug.austras.core.Component;

import java.util.UUID;

@Component
public class UUIDToStringConverter implements StringConverter<UUID> {
    @Override
    public UUID fromString(String value) {
        if (value == null) {
            return null;
        }
        return UUID.fromString(value);
    }

    @Override
    public String toString(UUID obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}