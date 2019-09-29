package guru.bug.austras.engine;

import guru.bug.austras.apt.model.ComponentModel;
import guru.bug.austras.apt.model.QualifierModel;
import guru.bug.austras.codegen.spec.TypeSpec;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.Optional;

public interface ComponentManager {
    Collection<ComponentModel> findComponents(String name, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(Class<?> clazz, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(TypeMirror type, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(TypeElement element, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(TypeSpec spec, QualifierModel qualifier);

    Collection<ComponentModel> findComponents(String name);

    Collection<ComponentModel> findComponents(Class<?> clazz);

    Collection<ComponentModel> findComponents(TypeMirror type);

    Collection<ComponentModel> findComponents(TypeSpec spec);

    Optional<ComponentModel> findSingleComponent(String name, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(Class<?> clazz, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(TypeMirror type, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(TypeElement element, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(TypeSpec spec, QualifierModel qualifier);

    Optional<ComponentModel> findSingleComponent(String name);

    Optional<ComponentModel> findSingleComponent(Class<?> clazz);

    Optional<ComponentModel> findSingleComponent(TypeMirror type);

    Optional<ComponentModel> findSingleComponent(TypeSpec spec);
}
