package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.YearMonth;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfYearMonthToJsonConverterProvider extends SetToJsonConverterProvider<YearMonth> {

    public SetOfYearMonthToJsonConverterProvider(Provider<? extends JsonConverter<YearMonth>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
