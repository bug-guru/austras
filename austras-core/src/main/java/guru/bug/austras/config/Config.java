/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.config;

import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier(name = "ConfigurationProperty", properties = @QualifierProperty(name = "name"))
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.PARAMETER})
public @interface Config {
    String name();

    String defaultValue() default "";
}
