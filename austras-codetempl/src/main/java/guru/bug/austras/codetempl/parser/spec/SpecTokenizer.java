package guru.bug.austras.codetempl.parser.spec;

import guru.bug.austras.codetempl.parser.Tokenizer;

import java.util.List;

public class SpecTokenizer extends Tokenizer<SpecToken> {
    public SpecTokenizer() {
        super(List.of(
                new SpaceIgnoreTokenProcessor<>(),
                new OneCharTokenProcessor<>(s -> new SpecToken(SpecToken.Type.COLON, ":"), ':'),
                new OneCharTokenProcessor<>(s -> new SpecToken(SpecToken.Type.QUESTION_MARK, "?"), '?'),
                new StringLiteralTokenProcessor<>(s -> new SpecToken(SpecToken.Type.STRING_LITERAL, s)),
                new NameTokenProcessor<>(s -> new SpecToken(SpecToken.Type.NAME, s))
        ));
    }
}
