package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.LocalDate;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfLocalDateToJsonConverterProvider extends ListToJsonConverterProvider<LocalDate> {

    public ListOfLocalDateToJsonConverterProvider(Provider<? extends JsonConverter<LocalDate>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
