/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Default
public class LocalDateToStringMapper implements Mapper<LocalDate, String> {
    private final DateTimeFormatter format;

    public LocalDateToStringMapper() {
        format = DateTimeFormatter.ISO_LOCAL_DATE;
    }

    public LocalDateToStringMapper(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String map(LocalDate source) {
        return source == null ? null : format.format(source);
    }
}
