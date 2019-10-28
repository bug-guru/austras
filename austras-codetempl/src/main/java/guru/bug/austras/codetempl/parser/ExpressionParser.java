package guru.bug.austras.codetempl.parser;

import guru.bug.austras.codetempl.blocks.Block;
import guru.bug.austras.codetempl.blocks.ExpressionBlock;
import guru.bug.austras.codetempl.expressions.LiteralExpression;
import guru.bug.austras.codetempl.expressions.VarExpression;
import guru.bug.austras.codetempl.parser.spec.SpecToken;
import guru.bug.austras.codetempl.parser.spec.SpecTokenizer;

public class ExpressionParser {

    Block parse(String expression) {
        var st = new SpecTokenizer();
        var tokens = st.process(expression);
        if (tokens.size() != 1) {
            throw new IllegalArgumentException("Unsupported expression: " + expression);
        }
        var specToken = tokens.get(0);
        if (specToken.getType() == SpecToken.Type.STRING_LITERAL) {
            return ExpressionBlock.of(LiteralExpression.of(specToken.getValue()));
        } else if (specToken.getType() == SpecToken.Type.NAME) {
            return ExpressionBlock.of(VarExpression.of(specToken.getValue()));
        } else {
            throw new IllegalArgumentException("Token not supported: " + specToken);
        }
    }

}
