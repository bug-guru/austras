/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

@Default
public class StringToLongMapper implements Mapper<String, Long> {
    @Override
    public Long map(String source) {
        return source == null ? null : Long.valueOf(source);
    }
}
