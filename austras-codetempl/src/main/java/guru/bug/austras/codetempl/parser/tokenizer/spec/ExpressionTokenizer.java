package guru.bug.austras.codetempl.parser.tokenizer.spec;

import guru.bug.austras.codetempl.parser.tokenizer.Tokenizer;

import java.util.List;

public class ExpressionTokenizer extends Tokenizer<SpecToken> {
    public ExpressionTokenizer() {
        super(List.of(
                new SpaceIgnoreTokenProcessor<>(),
                new OneCharTokenProcessor<>(s -> new SpecToken(SpecToken.Type.COLON, ":"), ':'),
                new OneCharTokenProcessor<>(s -> new SpecToken(SpecToken.Type.QUESTION_MARK, "?"), '?'),
                new StringLiteralTokenProcessor<>(s -> new SpecToken(SpecToken.Type.STRING_LITERAL, s)),
                new NameTokenProcessor<>(s -> new SpecToken(SpecToken.Type.NAME, s))
        ));
    }
}
