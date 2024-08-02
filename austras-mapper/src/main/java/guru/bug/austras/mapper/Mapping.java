/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper;

import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

import java.lang.annotation.*;

@Qualifier(name = Mapping.QUALIFIER_NAME,
        properties = {
                @QualifierProperty(name = Mapping.FIELD_PROPERTY),
                @QualifierProperty(name = Mapping.TARGET_FIELD_PROPERTY),
                @QualifierProperty(name = Mapping.MAPPER_PROPERTY),
                @QualifierProperty(name = Mapping.FORMAT_PROPERTY)
        })
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Repeatable(value = Mappings.class)
public @interface Mapping {
    String QUALIFIER_NAME = "austras.mapping";
    String FIELD_PROPERTY = "field";
    String TARGET_FIELD_PROPERTY = "targetField";
    String MAPPER_PROPERTY = "mapper";
    String FORMAT_PROPERTY = "format";

    String field() default "";

    String targetField() default "";

    Class<? extends Mapper> mapper() default Mapper.class;

    String format() default "";
}
