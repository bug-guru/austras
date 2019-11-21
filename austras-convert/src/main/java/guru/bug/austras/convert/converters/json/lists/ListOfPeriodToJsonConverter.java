package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfPeriodToJsonConverter extends ListToJsonConverter<Period> {

    public ListOfPeriodToJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
