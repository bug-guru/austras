package guru.bug.austras.codetempl.expressions;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Value;

import java.util.Objects;

public class VarExpression implements Expression {
    private final String varName;

    private VarExpression(String varName) {
        this.varName = varName;
    }

    public static VarExpression of(String varName) {
        Objects.requireNonNull(varName, "varName must not be null");
        return new VarExpression(varName);
    }

    @Override
    public Value evaluate(Context ctx) {
        return ctx.get(varName);
    }
}
