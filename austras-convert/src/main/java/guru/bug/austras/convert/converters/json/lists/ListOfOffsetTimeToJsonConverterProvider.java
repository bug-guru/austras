package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.OffsetTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfOffsetTimeToJsonConverterProvider extends ListToJsonConverterProvider<OffsetTime> {

    public ListOfOffsetTimeToJsonConverterProvider(Provider<? extends JsonConverter<OffsetTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
