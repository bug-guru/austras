package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
@ApplicationJson
public class ListOfLocalDateJsonConverter extends ListJsonConverter<LocalDate> {

    public ListOfLocalDateJsonConverter(JsonConverter<LocalDate> elementConverter) {
        super(elementConverter);
    }

}
