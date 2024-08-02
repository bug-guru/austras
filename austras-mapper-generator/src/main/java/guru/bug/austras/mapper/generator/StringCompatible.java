/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.UUID;

public enum StringCompatible {
    BYTE("Byte", "source.toString()", "Byte.valueOf(source)"),
    CHAR("Character", "source.toString()", "source.charAt(0)"),
    SHORT("Short", "source.toString()", "Short.valueOf(source)"),
    INT("Integer", "source.toString()", "Integer.valueOf(source)"),
    LONG("Long", "source.toString()", "Long.valueOf(source)"),
    FLOAT("Float", "source.toString()", "Float.valueOf(source)"),
    DOUBLE("Double", "source.toString()", "Double.valueOf(source)"),
    BOOLEAN("Boolean", "source.toString()", "Boolean.valueOf(source)"),
    BIG_DECIMAL(BigDecimal.class.getName(), "source.toString()", "new BigDecimal(source)"),
    BIG_INTEGER(BigInteger.class.getName(), "source.toString()", "new BigInteger(source)"),
    DAY_OF_WEEK(DayOfWeek.class.getName(), "source.name()", "DayOfWeek.valueOf(source)"),
    DURATION(Duration.class.getName(), "source.toString()", "Duration.parse(source)"),
    INSTANT(Instant.class.getName(), "source.toString()", "Instant.parse(source)"),
    LOCAL_DATE(LocalDate.class.getName(), "source.toString()", "LocalDate.parse(source)"),
    LOCAL_DATE_TIME(LocalDateTime.class.getName(), "source.toString()", "LocalDateTime.parse(source)"),
    LOCAL_TIME(LocalTime.class.getName(), "source.toString()", "LocalTime.parse(source)"),
    MONTH_DAY(MonthDay.class.getName(), "source.toString()", "MonthDay.parse(source)"),
    OFFSET_DATE_TIME(OffsetDateTime.class.getName(), "source.toString()", "OffsetDateTime.parse(source)"),
    OFFSET_TIME(OffsetTime.class.getName(), "source.toString()", "OffsetTime.parse(source)"),
    PERIOD(Period.class.getName(), "source.toString()", "Period.parse(source)"),
    UUID(UUID.class.getName(), "source.toString()", "UUID.fromString(source)"),
    ZONED_DATE_TIME(ZonedDateTime.class.getName(), "source.toString()", "ZonedDateTime.parse(source)"),
    ZONED_ID(ZoneId.class.getName(), "source.toString()", "ZoneId.of(source)"),
    ZONE_OFFSET(ZoneOffset.class.getName(), "source.toString()", "ZoneOffset.of(source)");


    private final String type;
    private final String toString;
    private final String fromString;

    StringCompatible(String type, String toString, String fromString) {

        this.type = type;
        this.toString = toString;
        this.fromString = fromString;
    }

    public String getType() {
        return type;
    }

    public String getToString() {
        return toString;
    }

    public String getFromString() {
        return fromString;
    }
}
