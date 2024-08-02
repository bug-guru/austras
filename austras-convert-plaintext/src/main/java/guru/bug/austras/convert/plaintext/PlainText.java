/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.Converts;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.QualifierProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier(name = Converts.QUALIFIER_NAME, properties = @QualifierProperty(name = Converts.PROPERTY_TYPE, value = PlainText.CONTENT_TYPE))
public @interface PlainText {
    String CONTENT_TYPE = "text/plain";
}
