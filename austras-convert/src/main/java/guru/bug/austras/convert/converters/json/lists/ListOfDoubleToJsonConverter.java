package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class ListOfDoubleToJsonConverter extends ListToJsonConverter<Double> {

    public ListOfDoubleToJsonConverter(JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
