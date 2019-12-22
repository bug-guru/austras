package guru.bug.austras.apt.core;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.HashSet;
import java.util.Set;

public class UniqueNameGenerator {
    private final Set<String> reservedVarNames = new HashSet<>();

    public String findFreeVarName(TypeElement type) {
        var nameBase = type.getSimpleName().toString();
        return findFreeVarName(ModelUtils.firstLower(nameBase));
    }


    public String findFreeVarName(DeclaredType type) {
        return findFreeVarName((TypeElement) type.asElement());
    }

    public String findFreeVarName(String nameBase) {
        var desiredVarName = nameBase;
        int index = 1;
        while (reservedVarNames.contains(desiredVarName)) {
            desiredVarName = nameBase + index++;
        }
        reservedVarNames.add(desiredVarName);
        return desiredVarName;
    }
}
