package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

public class ProviderDescription extends BaseDescription {
    private final List<ComponentKey> params = new ArrayList<>();
    public ProviderDescription(String varName, TypeElement componentType) {
        super(varName, componentType);
    }
}
