package guru.bug.austras.apt.core.componentmap;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeVariable;
import java.util.HashSet;
import java.util.Set;

public class UniqueNameGenerator {
    private final Set<String> reservedVarNames = new HashSet<>();

    public String findFreeVarName(TypeElement type) {
        var nameBase = type.getSimpleName().toString();
        return findFreeVarName(firstLower(nameBase));
    }

    private String firstLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public String findFreeVarName(DeclaredType type) {
        var nameBase = new StringBuilder(64);
        nameBase.append(firstLower(type.asElement().getSimpleName().toString()));
        type.getTypeArguments().forEach(tm -> {
            switch (tm.getKind()) {
                case TYPEVAR:
                    nameBase.append(((TypeVariable) tm).asElement().getSimpleName());
                    break;
                case DECLARED:
                    nameBase.append(((DeclaredType) tm).asElement().getSimpleName());
                    break;
                default:
                    nameBase.append("X" );
            }
        });
        return findFreeVarName(nameBase.toString());
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
