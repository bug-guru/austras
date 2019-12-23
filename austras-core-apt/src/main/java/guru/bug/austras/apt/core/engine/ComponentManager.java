package guru.bug.austras.apt.core.engine;

import guru.bug.austras.apt.core.model.ComponentKey;
import guru.bug.austras.apt.core.model.ComponentModel;
import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.apt.core.model.QualifierModel;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.Optional;

public interface ComponentManager {
    boolean tryUseComponents(TypeMirror type, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(TypeMirror type, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(TypeMirror type, QualifierModel qualifier);

    boolean tryUseComponents(Class<?> type, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(Class<?> type, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(Class<?> type, QualifierModel qualifier);

    boolean tryUseComponents(String type, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(String type, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(String type, QualifierModel qualifier);

    boolean tryUseComponents(DependencyModel dependencyModel);

    Collection<ComponentModel> findComponents(DependencyModel dependencyModel);

    Optional<ComponentModel> findSingleComponent(DependencyModel dependencyModel);

    boolean tryUseComponents(ComponentKey key);

    Collection<ComponentModel> findComponents(ComponentKey key);

    Optional<ComponentModel> findSingleComponent(ComponentKey key);

    QualifierModel extractQualifier(AnnotatedConstruct annotated);
}
