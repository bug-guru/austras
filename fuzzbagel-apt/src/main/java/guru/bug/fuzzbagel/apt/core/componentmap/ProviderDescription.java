package guru.bug.fuzzbagel.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.Set;

public class ProviderDescription {
    private final TypeElement providerType;
    private final Set<DeclaredType> ancestors;

    public ProviderDescription(TypeElement providerType, Set<DeclaredType> ancestors) {
        this.providerType = providerType;
        this.ancestors = ancestors;
    }

    public TypeElement getProviderType() {
        return providerType;
    }

    public Set<DeclaredType> getAncestors() {
        return ancestors;
    }
}
