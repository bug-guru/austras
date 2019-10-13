package guru.bug.austras.codetempl.expressions;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Value;

public interface Expression {
    Expression empty = ctx -> Value.EMPTY;

    Value evaluate(Context ctx);
}
