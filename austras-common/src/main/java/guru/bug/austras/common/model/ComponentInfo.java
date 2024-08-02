/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.common.model;

import java.util.List;
import java.util.Set;

public interface ComponentInfo {

    String getName();

    String getInstantiable();

    QualifierSetInfo getQualifiers();

    Set<String> getTypes();

    List<DependencyInfo> getDependencies();

}
