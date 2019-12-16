package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.MonthDay;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfMonthDayToJsonConverterProvider extends ListToJsonConverterProvider<MonthDay> {

    public ListOfMonthDayToJsonConverterProvider(Provider<? extends JsonConverter<MonthDay>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
