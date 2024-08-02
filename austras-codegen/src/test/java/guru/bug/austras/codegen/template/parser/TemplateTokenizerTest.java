/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template.parser;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.stream.Collectors;

import static guru.bug.austras.codegen.template.parser.TemplateToken.Type.TEXT;
import static guru.bug.austras.codegen.template.parser.TemplateToken.Type.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateTokenizerTest {
    private Iterator<TemplateToken> tokenIterator;

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
    void tokenFromText() throws Exception {
        var template = "        var result = new $TARGET_TYPE$();\n" +
                       "        #FIELD_MAPPINGS#\n" +
                       "        result.$FIELD_MAPPING_TARGET_SETTER$(#CONVERTER#source.$FIELD_MAPPING_SOURCE_GETTER$()#END#);\n" +
                       "        #END#\n" +
                       "        return result;\n";

        var tokenizer = new TemplateTokenizer();
        var tokens = tokenizer.process(template);

        tokenIterator = tokens.iterator();

        assertNextToken(TEXT, "        var result = new ");
        assertNextToken(VALUE, "TARGET_TYPE");
        assertNextToken(TEXT, "();");
        assertNextTokenIsNewLine();

    }

    @Test
    void tokensFromFile() throws Exception {
        try (var is = getClass().getResourceAsStream("simple_test_template.txt");
             var r = new InputStreamReader(is);
             var br = new BufferedReader(r)) {
            var str = new StringWriter(2048);
            br.transferTo(str);
            TemplateTokenizer tt = new TemplateTokenizer();
            var tokens = tt.process(str.toString());
            this.tokenIterator = tokens.iterator();
        }
        assertNextToken(TEXT, "package ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET.PACKAGE");
        assertNextToken(TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.BLOCK, "IMPORTS");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "guru.bug.austras.convert.converters.JsonConverter");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "guru.bug.austras.json.reader.JsonValueReader");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "guru.bug.austras.json.writer.JsonValueWriter");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "guru.bug.austras.core.qualifiers.Default");
        assertNextTokenIsNewLine();
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "@Default");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "public class ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET.SIMPLE_NAME");
        assertNextToken(TEXT, "ToJsonConverter implements JsonConverter<");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TEXT, "> {");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    ");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    private ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.TYPE");
        assertNextToken(TEXT, " ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    public FakeDtoToJsonConverter(");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS PARAMS");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.TYPE");
        assertNextToken(TEXT, " ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextToken(TEXT, ") {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        ");
        assertNextToken(TemplateToken.Type.BLOCK, "CONVERTERS");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        this.");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TEXT, " = ");
        assertNextToken(TemplateToken.Type.VALUE, "CNV.NAME");
        assertNextToken(TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    @Override");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    public void toJson(");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TEXT, " value, JsonValueWriter writer) {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        writer.writeObject(value, (obj, out) -> {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "            ");
        assertNextToken(TemplateToken.Type.BLOCK, "PROPERTIES");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "            out.write(\"");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.NAME");
        assertNextToken(TEXT, "\", obj.");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.GETTER_NAME");
        assertNextToken(TEXT, "(), ");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.CONVERTER.NAME");
        assertNextToken(TEXT, ";");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "            ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        });");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    @Override");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    public ");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TEXT, " fromJson(JsonValueReader reader) {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        return reader.readObject(");
        assertNextToken(TemplateToken.Type.VALUE, "TARGET");
        assertNextToken(TEXT, "::new, (obj, key, in) -> {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "            switch (key) {");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "                ");
        assertNextToken(TemplateToken.Type.BLOCK, "PROPERTIES");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "                case \"");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.NAME");
        assertNextToken(TEXT, "\":");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "                    obj.");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.SETTER_NAME");
        assertNextToken(TEXT, "(r.read(");
        assertNextToken(TemplateToken.Type.VALUE, "PROP.CONVERTER.NAME");
        assertNextToken(TEXT, ").orElse(null));");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "                    break;");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "                ");
        assertNextToken(TemplateToken.Type.BLOCK, "END");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "            }");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "        }).orElse(null);");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "    }");
        assertNextTokenIsNewLine();
        assertNextToken(TEXT, "}");
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