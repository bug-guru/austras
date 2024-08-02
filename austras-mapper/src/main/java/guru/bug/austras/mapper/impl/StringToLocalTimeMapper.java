/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalTime;

@Default
public class StringToLocalTimeMapper implements Mapper<String, LocalTime> {
    @Override
    public LocalTime map(String source) {
        return source == null ? null : LocalTime.parse(source);
    }
}
