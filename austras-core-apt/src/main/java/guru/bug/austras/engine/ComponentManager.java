package guru.bug.austras.engine;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.QualifierModel;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;

public interface ComponentManager {
    boolean useComponent(TypeMirror type, QualifierModel qualifier);

    Collection<ComponentModel> useAndGetComponents(TypeMirror element, QualifierModel qualifier);

    QualifierModel extractQualifier(AnnotatedConstruct annotated);
}
