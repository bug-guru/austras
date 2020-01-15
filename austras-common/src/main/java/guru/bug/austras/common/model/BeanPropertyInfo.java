/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.common.model;

public interface BeanPropertyInfo {

    String getName();

    String getType();

    boolean isReadable();

    boolean isWritable();
}
