package guru.bug.austras.convert.converters.json.lists;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.core.Provider;
import guru.bug.austras.meta.QualifierSetMetaInfo;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListOfCharacterToJsonConverterProvider extends ListToJsonConverterProvider<Character> {

    public ListOfCharacterToJsonConverterProvider(Provider<? extends JsonConverter<Character>> elementConverterProvider) {
        super(elementConverterProvider);
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
