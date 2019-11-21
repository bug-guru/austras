package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfLocalDateToJsonConverter extends SetToJsonConverter<LocalDate> {

    public SetOfLocalDateToJsonConverter(JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
