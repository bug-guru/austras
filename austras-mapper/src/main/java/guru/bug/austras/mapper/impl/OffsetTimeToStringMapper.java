/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.OffsetTime;

@Default
public class OffsetTimeToStringMapper implements Mapper<OffsetTime, String> {
    @Override
    public String map(OffsetTime source) {
        return source == null ? null : source.toString();
    }
}