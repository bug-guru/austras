/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper;

import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

@Qualifier(name = Mapping.QUALIFIER_NAME,
        properties = {
                @QualifierProperty(name = "field"),
                @QualifierProperty(name = "sourceField"),
                @QualifierProperty(name = "targetField"),
                @QualifierProperty(name = "mapper"),
                @QualifierProperty(name = "pattern")

        })
public @interface Mapping {
    String QUALIFIER_NAME = "austras.mapping";

    String field() default "";

    String sourceField() default "";

    String targetField() default "";

    Class<? extends Mapper> mapper() default Mapper.class;

    String pattern() default "";
}
