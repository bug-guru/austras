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
public class IntegerToDoubleMapper implements Mapper<Integer, Double> {
    @Override
    public Double map(Integer source) {
        return source == null ? null : source.doubleValue();
    }
}
