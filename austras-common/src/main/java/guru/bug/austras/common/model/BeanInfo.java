/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.common.model;

import java.util.Collection;

public interface BeanInfo {

    String getType();

    Collection<BeanPropertyInfo> getProperties();

}
