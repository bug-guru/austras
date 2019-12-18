package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.DayOfWeek;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfDayOfWeekToJsonConverterProvider extends ListToJsonConverterProvider<DayOfWeek> {

    public ListOfDayOfWeekToJsonConverterProvider(Provider<? extends JsonConverter<DayOfWeek>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
