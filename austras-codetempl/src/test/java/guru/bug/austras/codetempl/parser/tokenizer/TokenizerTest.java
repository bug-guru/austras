package guru.bug.austras.codetempl.parser.tokenizer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenizerTest {


    @Test
    public void simpleCase() throws Exception {
        var tokenizer = new Tokenizer<String>(List.of(
                new SpaceIgnoreTokenProcessor(),
                new StringLiteralTokenProcessor<>(s -> s),
                new LetterTokenProcessor()));
        var result = tokenizer.process("abc def\"xyz\"");
        assertEquals(List.of("abc", "def", "xyz"), result);
    }


    private static class LetterTokenProcessor implements TokenProcessor<String> {
        private final StringBuilder result = new StringBuilder();

        @Override
        public ProcessResult process(int codePoint) {
            if (Character.isLetterOrDigit(codePoint)) {
                result.appendCodePoint(codePoint);
                return ProcessResult.ACCEPT;
            } else {
                return ProcessResult.REJECT;
            }
        }

        @Override
        public String complete() {
            var r = result.toString();
            result.setLength(0);
            return r;
        }
    }
}