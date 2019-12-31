package guru.bug.austras.convert.json.lists;

import guru.bug.austras.convert.json.ApplicationJson;
import guru.bug.austras.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class SetOfPeriodJsonConverter extends AbstractSetJsonConverter<Period> {

    public SetOfPeriodJsonConverter(@ApplicationJson JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
