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

@Default
public class LongToInstantMapper implements Mapper<Long, Instant> {
    private final InstantToLongMappingType mappingType;

    public LongToInstantMapper() {
        this(InstantToLongMappingType.MILLI);
    }

    public LongToInstantMapper(InstantToLongMappingType mappingType) {
        this.mappingType = mappingType;
    }

    public LongToInstantMapper(String mappingType) {
        this.mappingType = InstantToLongMappingType.valueOf(mappingType);
    }

    @Override
    public Instant map(Long source) {
        return source == null ? null : mappingType.fromLong(source);
    }
}
