package guru.bug.austras.engine;

import guru.bug.austras.apt.model.QualifierModel;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.type.TypeMirror;

public interface ComponentManager {
    boolean useComponent(TypeMirror type, QualifierModel qualifier);

    QualifierModel extractQualifier(AnnotatedConstruct annotated);
}
