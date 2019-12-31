package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.ApplicationJson;
import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfMonthJsonConverter extends AbstractListJsonConverter<Month> {

    public ListOfMonthJsonConverter(@ApplicationJson JsonConverter<Month> elementConverter) {
        super(elementConverter);
    }

}
