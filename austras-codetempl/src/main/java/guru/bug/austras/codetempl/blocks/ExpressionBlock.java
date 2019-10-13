package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;
import guru.bug.austras.codetempl.expressions.Expression;

import java.util.List;

public class ExpressionBlock implements Block {
    private final Expression expression;

    private ExpressionBlock(Expression expression) {
        this.expression = expression;
    }

    public static ExpressionBlock of(Expression expression) {
        return new ExpressionBlock(expression);
    }

    @Override
    public List<Printable> evaluate(Context ctx) {
        return List.of(expression.evaluate(ctx));
    }
}
