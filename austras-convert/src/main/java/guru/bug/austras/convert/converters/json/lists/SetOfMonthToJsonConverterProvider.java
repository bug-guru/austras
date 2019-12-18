package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.Month;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class SetOfMonthToJsonConverterProvider extends SetToJsonConverterProvider<Month> {

    public SetOfMonthToJsonConverterProvider(Provider<? extends JsonConverter<Month>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
