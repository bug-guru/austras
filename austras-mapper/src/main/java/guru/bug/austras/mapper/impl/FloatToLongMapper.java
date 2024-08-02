/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

@Default
public class FloatToLongMapper implements Mapper<Float, Long> {
    @Override
    public Long map(Float source) {
        return source == null ? null : source.longValue();
    }
}
