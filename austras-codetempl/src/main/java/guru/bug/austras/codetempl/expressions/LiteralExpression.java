package guru.bug.austras.codetempl.expressions;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Value;

public class LiteralExpression implements Expression {
    private final Value literal;

    private LiteralExpression(Value literal) {
        this.literal = literal;
    }

    public static LiteralExpression of(int value) {
        return new LiteralExpression(Value.of(value));
    }

    public static LiteralExpression of(boolean value) {
        return new LiteralExpression(Value.of(value));
    }

    public static LiteralExpression of(String value) {
        return new LiteralExpression(Value.of(value));
    }

    @Override
    public Value evaluate(Context ctx) {
        return literal;
    }
}
