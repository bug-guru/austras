/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.Duration;

@Default
public class StringToDurationMapper implements Mapper<String, Duration> {
    @Override
    public Duration map(String source) {
        return source == null ? null : Duration.parse(source);
    }
}
