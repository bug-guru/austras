/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.common.model;

public interface DependencyInfo {

    String getName();

    String getType();

    QualifierSetInfo getQualifiers();

    WrappingType getWrapping();

}
