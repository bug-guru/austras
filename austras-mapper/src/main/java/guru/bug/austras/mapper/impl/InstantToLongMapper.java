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

/**
 * Maps Instant to Long as epoch milli or second.
 */
@Default
public class InstantToLongMapper implements Mapper<Instant, Long> {
    private final InstantToLongMappingType mappingType;

    public InstantToLongMapper() {
        this(InstantToLongMappingType.MILLI);
    }

    public InstantToLongMapper(InstantToLongMappingType mappingType) {
        this.mappingType = mappingType;
    }

    public InstantToLongMapper(String mappingType) {
        this.mappingType = InstantToLongMappingType.valueOf(mappingType);
    }

    @Override
    public Long map(Instant source) {
        return source == null ? null : mappingType.toLong(source);
    }
}
