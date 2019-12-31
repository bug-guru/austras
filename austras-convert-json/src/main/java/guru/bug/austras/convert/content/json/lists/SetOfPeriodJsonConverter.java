package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfPeriodJsonConverter extends AbstractSetJsonConverter<Period> {

    public SetOfPeriodJsonConverter(@ApplicationJson JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
