/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.mapper.Mapper;

import java.time.ZonedDateTime;

@Default
public class ZonedDateTimeToStringMapper implements Mapper<ZonedDateTime, String> {
    @Override
    public String map(ZonedDateTime source) {
        return source == null ? null : source.toString();
    }
}
