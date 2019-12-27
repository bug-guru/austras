package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Period;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfPeriodJsonConverter extends AbstractListJsonConverter<Period> {

    public ListOfPeriodJsonConverter(@ApplicationJson JsonConverter<Period> elementConverter) {
        super(elementConverter);
    }

}
