package guru.bug.austras.codetempl.blocks;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Printable;
import guru.bug.austras.codetempl.expressions.Expression;

import java.util.List;

public class OutputBlock implements Block {
    private final Filter filter;
    private final Expression expression;

    @Override

    public List<Printable> evaluate(Context ctx) {
        return null;
    }
}
