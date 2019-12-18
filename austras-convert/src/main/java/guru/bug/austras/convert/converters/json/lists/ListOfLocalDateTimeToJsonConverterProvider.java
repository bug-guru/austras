package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.LocalDateTime;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfLocalDateTimeToJsonConverterProvider extends ListToJsonConverterProvider<LocalDateTime> {

    public ListOfLocalDateTimeToJsonConverterProvider(Provider<? extends JsonConverter<LocalDateTime>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
