/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Default
public class LocalTimeToStringMapper implements Mapper<LocalTime, String> {
    private final DateTimeFormatter format;

    public LocalTimeToStringMapper() {
        format = DateTimeFormatter.ISO_LOCAL_TIME;
    }

    public LocalTimeToStringMapper(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String map(LocalTime source) {
        return source == null ? null : format.format(source);
    }
}
