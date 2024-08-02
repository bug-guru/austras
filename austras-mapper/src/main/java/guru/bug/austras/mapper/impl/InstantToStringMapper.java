/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Default
public class InstantToStringMapper implements Mapper<Instant, String> {
    private final DateTimeFormatter format;

    public InstantToStringMapper() {
        format = DateTimeFormatter.ISO_INSTANT;
    }

    public InstantToStringMapper(String format) {
        this.format = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public String map(Instant source) {
        return source == null ? null : source.toString();
    }
}
