/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateTokenizerTest {
    private Iterator<TemplateToken> tokenIterator;

    @BeforeEach
    void init() throws Exception {
        try (var is = getClass().getResourceAsStream("simple_test_template.txt");
             var r = new InputStreamReader(is);
             var br = new BufferedReader(r)) {
            var str = new StringWriter(2048);
            br.transferTo(str);
            TemplateTokenizer tt = new TemplateTokenizer();
            var tokens = tt.process(str.toString());
            this.tokenIterator = tokens.iterator();
        }
    }

    void assertNextToken(TemplateToken.Type expectedType, String expectedValue) {
        var token = tokenIterator.next();
        assertEquals(expectedType, token.getType());
        assertEquals(expectedValue, token.getValue());
    }

    void assertNextTokenIsNewLine() {
        var token = tokenIterator.next();
        assertEquals(TemplateToken.Type.NEW_LINE, token.getType());
    }

    @Test
    void tokens() throws Exception {
        assertNextToken(TemplateToken.Type.TEXT, "package ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET.PACKAGE");
        assertNextToken(TemplateToken.Type.TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.BLOCK, "IMPORTS");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "guru.bug.austras.convert.converters.JsonConverter");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "guru.bug.austras.json.reader.JsonValueReader");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "guru.bug.austras.json.writer.JsonValueWriter");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "guru.bug.austras.core.qualifiers.Default");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "@Default");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "public class ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET.SIMPLE_NAME");
        assertNextToken(TemplateToken.Type.TEXT, "ToJsonConverter implements JsonConverter<");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TemplateToken.Type.TEXT, "> {");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    ");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    private ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.TYPE");
        assertNextToken(TemplateToken.Type.TEXT, " ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TemplateToken.Type.TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    public FakeDtoToJsonConverter(");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS PARAMS");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.TYPE");
        assertNextToken(TemplateToken.Type.TEXT, " ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextToken(TemplateToken.Type.TEXT, ") {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        ");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        this.");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TemplateToken.Type.TEXT, " = ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TemplateToken.Type.TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    @Override");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    public void toJson(");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TemplateToken.Type.TEXT, " value, JsonValueWriter writer) {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        writer.writeObject(value, (obj, out) -> {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "            ");
        assertNextToken(TemplateToken.Type.BLOCK, "PROPERTIES");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "            out.write(\"");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.NAME");
        assertNextToken(TemplateToken.Type.TEXT, "\", obj.");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.GETTER_NAME");
        assertNextToken(TemplateToken.Type.TEXT, "(), ");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.CONVERTER.NAME");
        assertNextToken(TemplateToken.Type.TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "            ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        });");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    @Override");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    public ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TemplateToken.Type.TEXT, " fromJson(JsonValueReader reader) {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        return reader.readObject(");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TemplateToken.Type.TEXT, "::new, (obj, key, in) -> {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "            switch (key) {");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "                ");
        assertNextToken(TemplateToken.Type.BLOCK, "PROPERTIES");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "                case \"");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.NAME");
        assertNextToken(TemplateToken.Type.TEXT, "\":");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "                    obj.");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.SETTER_NAME");
        assertNextToken(TemplateToken.Type.TEXT, "(r.read(");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.CONVERTER.NAME");
        assertNextToken(TemplateToken.Type.TEXT, ").orElse(null));");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "                    break;");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "                ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "            }");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "        }).orElse(null);");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.TEXT, "}");
    }

    private String escape(String value) {
        return value.codePoints()
                .mapToObj(cp -> {
                    switch (cp) {
                        case '\n':
                            return "\\n";
                        case '\t':
                            return "\\t";
                        case '\r':
                            return "\\r";
                        case '\b':
                            return "\\b";
                        case '\\':
                            return "\\\\";
                        case '"':
                            return "\\\"";
                        default:
                            return Character.toString(cp);
                    }
                })
                .collect(Collectors.joining());
    }

}