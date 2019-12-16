package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.LocalTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfLocalTimeToJsonConverterProvider extends ListToJsonConverterProvider<LocalTime> {

    public ListOfLocalTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
