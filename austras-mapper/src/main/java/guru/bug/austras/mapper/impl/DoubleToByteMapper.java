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
public class DoubleToByteMapper implements Mapper<Double, Byte> {
    @Override
    public Byte map(Double source) {
        return source == null ? null : source.byteValue();
    }
}
