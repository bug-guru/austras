package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfPeriodJsonConverter extends SetJsonConverter<Period> {

    public SetOfPeriodJsonConverter(JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
