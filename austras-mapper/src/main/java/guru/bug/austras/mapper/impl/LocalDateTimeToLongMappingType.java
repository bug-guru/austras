/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public enum LocalDateTimeToLongMappingType {
    MILLI {
        @Override
        public long toLong(LocalDateTime value) {
            return InstantToLongMappingType.MILLI.toLong(value.toInstant(ZoneOffset.UTC));
        }

        @Override
        public LocalDateTime fromLong(long value) {
            return LocalDateTime.ofInstant(InstantToLongMappingType.MILLI.fromLong(value), UTC);
        }
    },
    SECOND {
        @Override
        public long toLong(LocalDateTime value) {
            return value.toEpochSecond(ZoneOffset.UTC);
        }

        @Override
        public LocalDateTime fromLong(long value) {
            return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC);
        }
    };

    private static final ZoneId UTC = ZoneId.of(ZoneOffset.UTC.getId());

    public abstract long toLong(LocalDateTime value);

    public abstract LocalDateTime fromLong(long value);
}
