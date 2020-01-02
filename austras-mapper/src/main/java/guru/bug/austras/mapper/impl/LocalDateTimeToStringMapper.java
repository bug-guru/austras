/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Default
public class LocalDateTimeToStringMapper implements Mapper<LocalDateTime, String> {
    private final DateTimeFormatter format;

    public LocalDateTimeToStringMapper() {
        format = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public LocalDateTimeToStringMapper(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String map(LocalDateTime source) {
        return source == null ? null : format.format(source);
    }

}
