/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalDateTime;

/**
 * Maps LocalDateTime to Long as epoch milli or second.
 */
@Default
public class LocalDateTimeToLongMapper implements Mapper<LocalDateTime, Long> {
    private final LocalDateTimeToLongMappingType mappingType;

    public LocalDateTimeToLongMapper() {
        this(LocalDateTimeToLongMappingType.MILLI);
    }

    public LocalDateTimeToLongMapper(LocalDateTimeToLongMappingType mappingType) {
        this.mappingType = mappingType;
    }

    public LocalDateTimeToLongMapper(String mappingType) {
        this.mappingType = LocalDateTimeToLongMappingType.valueOf(mappingType);
    }

    @Override
    public Long map(LocalDateTime source) {
        return source == null ? null : mappingType.toLong(source);
    }
}
