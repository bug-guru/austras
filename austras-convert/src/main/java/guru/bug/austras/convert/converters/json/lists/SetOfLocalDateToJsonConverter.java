package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfLocalDateToJsonConverter extends SetToJsonConverter<LocalDate> {

    public SetOfLocalDateToJsonConverter(JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
