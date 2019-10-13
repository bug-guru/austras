package guru.bug.austras.codetempl.expressions;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Value;

import java.util.List;
import java.util.stream.Collectors;

public class ConcatenateExpression implements Expression {
    private final List<Expression> expressions;

    private ConcatenateExpression(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public Value evaluate(Context ctx) {
        String body = expressions.stream()
                .map(e -> e.evaluate(ctx))
                .map(Value::getString)
                .collect(Collectors.joining());
        return Value.of(body);
    }

    public static ConcatenateExpression of(Expression... expressions) {
        return new ConcatenateExpression(List.of(expressions));
    }

}
