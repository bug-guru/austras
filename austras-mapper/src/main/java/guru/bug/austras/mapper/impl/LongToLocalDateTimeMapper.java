/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.LocalDateTime;

@Default
public class LongToLocalDateTimeMapper implements Mapper<Long, LocalDateTime> {
    private final LocalDateTimeToLongMappingType mappingType;

    public LongToLocalDateTimeMapper() {
        this(LocalDateTimeToLongMappingType.MILLI);
    }

    public LongToLocalDateTimeMapper(LocalDateTimeToLongMappingType mappingType) {
        this.mappingType = mappingType;
    }

    public LongToLocalDateTimeMapper(String mappingType) {
        this.mappingType = LocalDateTimeToLongMappingType.valueOf(mappingType);
    }

    @Override
    public LocalDateTime map(Long source) {
        return source == null ? null : mappingType.fromLong(source);
    }
}
