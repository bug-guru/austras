package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.core.qualifiers.Default;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@Default
public class SetOfPeriodToJsonConverter extends SetToJsonConverter<Period> {

    public SetOfPeriodToJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
