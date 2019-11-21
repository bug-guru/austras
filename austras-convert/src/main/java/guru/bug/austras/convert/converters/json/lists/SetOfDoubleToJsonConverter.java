package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Component;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Component
public class SetOfDoubleToJsonConverter extends SetToJsonConverter<Double> {

    public SetOfDoubleToJsonConverter(JsonConverter<Double> elementConverter) {
        super(elementConverter);
    }

}
