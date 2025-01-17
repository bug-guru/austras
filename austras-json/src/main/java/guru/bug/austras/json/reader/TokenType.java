/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.json.reader;

public enum TokenType {
    BEGIN_OBJECT,
    END_OBJECT,
    BEGIN_ARRAY,
    END_ARRAY,
    STRING,
    COLON,
    COMMA,
    NUMBER,
    TRUE,
    FALSE,
    NULL,
    EOF
}
