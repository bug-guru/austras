package guru.bug.austras.codetempl.parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateTokenizerTest {

    @Test
    void tokens() throws Exception {
        try (var is = getClass().getResourceAsStream("/guru/bug/austras/codetempl/test/simple_test_template.txt");
             var r = new InputStreamReader(is);
             var br = new BufferedReader(r)) {
            TemplateTokenizer tt = new TemplateTokenizer(br);
            var tokens = tt.tokenize();
            assertEquals("package ", tokens.get(0).getValue());
            assertEquals(Token.Type.TXT, tokens.get(0).getType());
            assertEquals("TARGET.PACKAGE", tokens.get(1).getValue());
            assertEquals(Token.Type.EXP, tokens.get(1).getType());
            assertEquals(";\n", tokens.get(2).getValue());
            assertEquals(Token.Type.TXT, tokens.get(2).getType());
            assertEquals("\n", tokens.get(3).getValue());
            assertEquals(Token.Type.TXT, tokens.get(3).getType());
            assertEquals("import guru.bug.austras.convert.converters.JsonConverter;\n", tokens.get(4).getValue());
            assertEquals(Token.Type.TXT, tokens.get(4).getType());
            assertEquals("import guru.bug.austras.convert.json.reader.JsonValueReader;\n", tokens.get(5).getValue());
            assertEquals(Token.Type.TXT, tokens.get(5).getType());
            assertEquals("import guru.bug.austras.convert.json.writer.JsonValueWriter;\n", tokens.get(6).getValue());
            assertEquals(Token.Type.TXT, tokens.get(6).getType());
            assertEquals("import guru.bug.austras.core.Component;\n", tokens.get(7).getValue());
            assertEquals(Token.Type.TXT, tokens.get(7).getType());
            assertEquals("\n", tokens.get(8).getValue());
            assertEquals(Token.Type.TXT, tokens.get(8).getType());
            assertEquals("@Component\n", tokens.get(9).getValue());
            assertEquals(Token.Type.TXT, tokens.get(9).getType());
            assertEquals("public class ", tokens.get(10).getValue());
            assertEquals(Token.Type.TXT, tokens.get(10).getType());
            assertEquals("TARGET.SIMPLE_NAME", tokens.get(11).getValue());
            assertEquals(Token.Type.EXP, tokens.get(11).getType());
            assertEquals("ToJsonConverter implements JsonConverter<", tokens.get(12).getValue());
            assertEquals(Token.Type.TXT, tokens.get(12).getType());
            assertEquals("TARGET", tokens.get(13).getValue());
            assertEquals(Token.Type.EXP, tokens.get(13).getType());
            assertEquals("> {\n", tokens.get(14).getValue());
            assertEquals(Token.Type.TXT, tokens.get(14).getType());
            assertEquals("\n", tokens.get(15).getValue());
            assertEquals(Token.Type.TXT, tokens.get(15).getType());
            assertEquals("    ", tokens.get(16).getValue());
            assertEquals(Token.Type.TXT, tokens.get(16).getType());
            assertEquals("LOOP CNV : CONVERTERS", tokens.get(17).getValue());
            assertEquals(Token.Type.CMD, tokens.get(17).getType());
            assertEquals("\n", tokens.get(18).getValue());
            assertEquals(Token.Type.TXT, tokens.get(18).getType());
            assertEquals("    private ", tokens.get(19).getValue());
            assertEquals(Token.Type.TXT, tokens.get(19).getType());
            assertEquals("CNV.TYPE", tokens.get(20).getValue());
            assertEquals(Token.Type.EXP, tokens.get(20).getType());
            assertEquals(" ", tokens.get(21).getValue());
            assertEquals(Token.Type.TXT, tokens.get(21).getType());
            assertEquals("CNV.NAME", tokens.get(22).getValue());
            assertEquals(Token.Type.EXP, tokens.get(22).getType());
            assertEquals(";\n", tokens.get(23).getValue());
            assertEquals(Token.Type.TXT, tokens.get(23).getType());
            assertEquals("    ", tokens.get(24).getValue());
            assertEquals(Token.Type.TXT, tokens.get(24).getType());
            assertEquals("END", tokens.get(25).getValue());
            assertEquals(Token.Type.CMD, tokens.get(25).getType());
            assertEquals("\n", tokens.get(26).getValue());
            assertEquals(Token.Type.TXT, tokens.get(26).getType());
            assertEquals("\n", tokens.get(27).getValue());
            assertEquals(Token.Type.TXT, tokens.get(27).getType());
            assertEquals("    public FakeDtoToJsonConverter(", tokens.get(28).getValue());
            assertEquals(Token.Type.TXT, tokens.get(28).getType());
            assertEquals("LOOP CNV : CONVERTERS", tokens.get(29).getValue());
            assertEquals(Token.Type.CMD, tokens.get(29).getType());
            assertEquals("CNV.VALUE.TYPE", tokens.get(30).getValue());
            assertEquals(Token.Type.EXP, tokens.get(30).getType());
            assertEquals(" ", tokens.get(31).getValue());
            assertEquals(Token.Type.TXT, tokens.get(31).getType());
            assertEquals("CNV.NAME", tokens.get(32).getValue());
            assertEquals(Token.Type.EXP, tokens.get(32).getType());
            assertEquals("CNV.IS_LAST?:\", \"", tokens.get(33).getValue());
            assertEquals(Token.Type.EXP, tokens.get(33).getType());
            assertEquals("END", tokens.get(34).getValue());
            assertEquals(Token.Type.CMD, tokens.get(34).getType());
            assertEquals(") {\n", tokens.get(35).getValue());
            assertEquals(Token.Type.TXT, tokens.get(35).getType());
            assertEquals("        ", tokens.get(36).getValue());
            assertEquals(Token.Type.TXT, tokens.get(36).getType());
            assertEquals("LOOP CNV : CONVERTERS", tokens.get(37).getValue());
            assertEquals(Token.Type.CMD, tokens.get(37).getType());
            assertEquals("\n", tokens.get(38).getValue());
            assertEquals(Token.Type.TXT, tokens.get(38).getType());
            assertEquals("        this.", tokens.get(39).getValue());
            assertEquals(Token.Type.TXT, tokens.get(39).getType());
            assertEquals("CNV.VALUE.NAME", tokens.get(40).getValue());
            assertEquals(Token.Type.EXP, tokens.get(40).getType());
            assertEquals(" = ", tokens.get(41).getValue());
            assertEquals(Token.Type.TXT, tokens.get(41).getType());
            assertEquals("CNV.VALUE.NAME", tokens.get(42).getValue());
            assertEquals(Token.Type.EXP, tokens.get(42).getType());
            assertEquals(";\n", tokens.get(43).getValue());
            assertEquals(Token.Type.TXT, tokens.get(43).getType());
            assertEquals("        ", tokens.get(44).getValue());
            assertEquals(Token.Type.TXT, tokens.get(44).getType());
            assertEquals("END", tokens.get(45).getValue());
            assertEquals(Token.Type.CMD, tokens.get(45).getType());
            assertEquals("\n", tokens.get(46).getValue());
            assertEquals(Token.Type.TXT, tokens.get(46).getType());
            assertEquals("    }\n", tokens.get(47).getValue());
            assertEquals(Token.Type.TXT, tokens.get(47).getType());
            assertEquals("\n", tokens.get(48).getValue());
            assertEquals(Token.Type.TXT, tokens.get(48).getType());
            assertEquals("    @Override\n", tokens.get(49).getValue());
            assertEquals(Token.Type.TXT, tokens.get(49).getType());
            assertEquals("    public void toJson(", tokens.get(50).getValue());
            assertEquals(Token.Type.TXT, tokens.get(50).getType());
            assertEquals("TARGET", tokens.get(51).getValue());
            assertEquals(Token.Type.EXP, tokens.get(51).getType());
            assertEquals(" value, JsonValueWriter writer) {\n", tokens.get(52).getValue());
            assertEquals(Token.Type.TXT, tokens.get(52).getType());
            assertEquals("        writer.writeObject(value, (obj, out) -> {\n", tokens.get(53).getValue());
            assertEquals(Token.Type.TXT, tokens.get(53).getType());
            assertEquals("            ", tokens.get(54).getValue());
            assertEquals(Token.Type.TXT, tokens.get(54).getType());
            assertEquals("LOOP PROP : PROPERTIES", tokens.get(55).getValue());
            assertEquals(Token.Type.CMD, tokens.get(55).getType());
            assertEquals("\n", tokens.get(56).getValue());
            assertEquals(Token.Type.TXT, tokens.get(56).getType());
            assertEquals("            out.write(\"", tokens.get(57).getValue());
            assertEquals(Token.Type.TXT, tokens.get(57).getType());
            assertEquals("PROP.VALUE.NAME", tokens.get(58).getValue());
            assertEquals(Token.Type.EXP, tokens.get(58).getType());
            assertEquals("\", obj.", tokens.get(59).getValue());
            assertEquals(Token.Type.TXT, tokens.get(59).getType());
            assertEquals("PROP.VALUE.GETTER_NAME", tokens.get(60).getValue());
            assertEquals(Token.Type.EXP, tokens.get(60).getType());
            assertEquals("(), ", tokens.get(61).getValue());
            assertEquals(Token.Type.TXT, tokens.get(61).getType());
            assertEquals("PROP.VALUE.CONVERTER.NAME", tokens.get(62).getValue());
            assertEquals(Token.Type.EXP, tokens.get(62).getType());
            assertEquals(";\n", tokens.get(63).getValue());
            assertEquals(Token.Type.TXT, tokens.get(63).getType());
            assertEquals("            ", tokens.get(64).getValue());
            assertEquals(Token.Type.TXT, tokens.get(64).getType());
            assertEquals("END", tokens.get(65).getValue());
            assertEquals(Token.Type.CMD, tokens.get(65).getType());
            assertEquals("\n", tokens.get(66).getValue());
            assertEquals(Token.Type.TXT, tokens.get(66).getType());
            assertEquals("        });\n", tokens.get(67).getValue());
            assertEquals(Token.Type.TXT, tokens.get(67).getType());
            assertEquals("    }\n", tokens.get(68).getValue());
            assertEquals(Token.Type.TXT, tokens.get(68).getType());
            assertEquals("\n", tokens.get(69).getValue());
            assertEquals(Token.Type.TXT, tokens.get(69).getType());
            assertEquals("    @Override\n", tokens.get(70).getValue());
            assertEquals(Token.Type.TXT, tokens.get(70).getType());
            assertEquals("    public ", tokens.get(71).getValue());
            assertEquals(Token.Type.TXT, tokens.get(71).getType());
            assertEquals("TARGET", tokens.get(72).getValue());
            assertEquals(Token.Type.EXP, tokens.get(72).getType());
            assertEquals(" fromJson(JsonValueReader reader) {\n", tokens.get(73).getValue());
            assertEquals(Token.Type.TXT, tokens.get(73).getType());
            assertEquals("        return reader.readObject(", tokens.get(74).getValue());
            assertEquals(Token.Type.TXT, tokens.get(74).getType());
            assertEquals("TARGET", tokens.get(75).getValue());
            assertEquals(Token.Type.EXP, tokens.get(75).getType());
            assertEquals("::new, (obj, key, in) -> {\n", tokens.get(76).getValue());
            assertEquals(Token.Type.TXT, tokens.get(76).getType());
            assertEquals("            switch (key) {\n", tokens.get(77).getValue());
            assertEquals(Token.Type.TXT, tokens.get(77).getType());
            assertEquals("                ", tokens.get(78).getValue());
            assertEquals(Token.Type.TXT, tokens.get(78).getType());
            assertEquals("LOOP PROP : PROPERTIES", tokens.get(79).getValue());
            assertEquals(Token.Type.CMD, tokens.get(79).getType());
            assertEquals("\n", tokens.get(80).getValue());
            assertEquals(Token.Type.TXT, tokens.get(80).getType());
            assertEquals("                case \"", tokens.get(81).getValue());
            assertEquals(Token.Type.TXT, tokens.get(81).getType());
            assertEquals("PROP.VALUE.NAME", tokens.get(82).getValue());
            assertEquals(Token.Type.EXP, tokens.get(82).getType());
            assertEquals("\":\n", tokens.get(83).getValue());
            assertEquals(Token.Type.TXT, tokens.get(83).getType());
            assertEquals("                    obj.", tokens.get(84).getValue());
            assertEquals(Token.Type.TXT, tokens.get(84).getType());
            assertEquals("PROP.VALUE.SETTER_NAME", tokens.get(85).getValue());
            assertEquals(Token.Type.EXP, tokens.get(85).getType());
            assertEquals("(r.read(", tokens.get(86).getValue());
            assertEquals(Token.Type.TXT, tokens.get(86).getType());
            assertEquals("PROP.VALUE.CONVERTER.NAME", tokens.get(87).getValue());
            assertEquals(Token.Type.EXP, tokens.get(87).getType());
            assertEquals(").orElse(null));\n", tokens.get(88).getValue());
            assertEquals(Token.Type.TXT, tokens.get(88).getType());
            assertEquals("                    break;\n", tokens.get(89).getValue());
            assertEquals(Token.Type.TXT, tokens.get(89).getType());
            assertEquals("                ", tokens.get(90).getValue());
            assertEquals(Token.Type.TXT, tokens.get(90).getType());
            assertEquals("END", tokens.get(91).getValue());
            assertEquals(Token.Type.CMD, tokens.get(91).getType());
            assertEquals("\n", tokens.get(92).getValue());
            assertEquals(Token.Type.TXT, tokens.get(92).getType());
            assertEquals("            }\n", tokens.get(93).getValue());
            assertEquals(Token.Type.TXT, tokens.get(93).getType());
            assertEquals("        }).orElse(null);\n", tokens.get(94).getValue());
            assertEquals(Token.Type.TXT, tokens.get(94).getType());
            assertEquals("    }\n", tokens.get(95).getValue());
            assertEquals(Token.Type.TXT, tokens.get(95).getType());
            assertEquals("}", tokens.get(96).getValue());
            assertEquals(Token.Type.TXT, tokens.get(96).getType());
        }
    }

    @Test
    @Disabled
    void printTokens() throws Exception {
        try (var is = getClass().getResourceAsStream("/guru/bug/austras/codetempl/test/simple_test_template.txt");
             var r = new InputStreamReader(is);
             var br = new BufferedReader(r)) {
            TemplateTokenizer tt = new TemplateTokenizer(br);
            var idx = -1;
            for (var t : tt.tokenize()) {
                idx++;
                System.out.printf("assertEquals(\"%s\", tokens.get(%d).getValue());\n", escape(t.getValue()), idx);
                System.out.printf("assertEquals(Token.Type.%s, tokens.get(%d).getType());\n", t.getType(), idx);
            }
        }
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