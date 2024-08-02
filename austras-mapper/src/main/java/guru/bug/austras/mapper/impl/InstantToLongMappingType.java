/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.impl;

import java.time.Instant;

public enum InstantToLongMappingType {
    MILLI {
        @Override
        public long toLong(Instant value) {
            return value.toEpochMilli();
        }

        @Override
        public Instant fromLong(long value) {
            return Instant.ofEpochMilli(value);
        }
    },
    SECOND {
        @Override
        public long toLong(Instant value) {
            return value.getEpochSecond();
        }

        @Override
        public Instant fromLong(long value) {
            return Instant.ofEpochSecond(value);
        }
    };

    public abstract long toLong(Instant value);

    public abstract Instant fromLong(long value);
}
