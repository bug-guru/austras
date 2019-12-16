package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.time.ZoneId;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfZoneIdToJsonConverterProvider extends ListToJsonConverterProvider<ZoneId> {

    public ListOfZoneIdToJsonConverterProvider(Provider<? extends JsonConverter<ZoneId>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
