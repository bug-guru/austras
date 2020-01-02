/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalTime;

@Default
public class LocalTimeToStringMapper implements Mapper<LocalTime, String> {
    @Override
    public String map(LocalTime source) {
        return source == null ? null : source.toString();
    }
}
