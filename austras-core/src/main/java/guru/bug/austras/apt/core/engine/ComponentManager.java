/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.engine;

import guru.bug.austras.apt.core.common.model.ComponentKey;
import guru.bug.austras.apt.core.common.model.ComponentModel;
import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.common.model.QualifierSetModel;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.Optional;

public interface ComponentManager {
    boolean tryUseComponents(TypeMirror type, QualifierSetModel qualifier);

    Collection<ComponentModel> findComponents(TypeMirror type, QualifierSetModel qualifier);

    Optional<ComponentModel> findSingleComponent(TypeMirror type, QualifierSetModel qualifier);

    boolean tryUseComponents(Class<?> type, QualifierSetModel qualifier);

    Collection<ComponentModel> findComponents(Class<?> type, QualifierSetModel qualifier);

    Optional<ComponentModel> findSingleComponent(Class<?> type, QualifierSetModel qualifier);

    boolean tryUseComponents(String type, QualifierSetModel qualifier);

    Collection<ComponentModel> findComponents(String type, QualifierSetModel qualifier);

    Optional<ComponentModel> findSingleComponent(String type, QualifierSetModel qualifier);

    boolean tryUseComponents(DependencyModel dependencyModel);

    Collection<ComponentModel> findComponents(DependencyModel dependencyModel);

    Optional<ComponentModel> findSingleComponent(DependencyModel dependencyModel);

    boolean tryUseComponents(ComponentKey key);

    Collection<ComponentModel> findComponents(ComponentKey key);

    Optional<ComponentModel> findSingleComponent(ComponentKey key);

    QualifierSetModel extractQualifier(AnnotatedConstruct annotated);
}
