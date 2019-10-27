package guru.bug.austras.codetempl.parser.tokenizer.spec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionTokenizerTest {

    @Test
    public void basicTest() throws Exception {
        var et = new ExpressionTokenizer();
        var result = et.process("CNV.IS_LAST?:\", \"");
        var i = 0;
        assertEquals(SpecToken.Type.NAME, result.get(i).getType());
        assertEquals("CNV.IS_LAST", result.get(i).getValue());
        i++;
        assertEquals(SpecToken.Type.QUESTION_MARK, result.get(i).getType());
        assertEquals("?", result.get(i).getValue());
        i++;
        assertEquals(SpecToken.Type.COLON, result.get(i).getType());
        assertEquals(":", result.get(i).getValue());
        i++;
        assertEquals(SpecToken.Type.STRING_LITERAL, result.get(i).getType());
        assertEquals(", ", result.get(i).getValue());

    }

}