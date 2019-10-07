package guru.bug.austras.codetempl.expressions;

import guru.bug.austras.codetempl.Context;
import guru.bug.austras.codetempl.Value;

import java.util.Objects;

public class TernaryExpression implements Expression {
    private final Expression booleanExpression;
    private final Expression trueExpression;
    private final Expression falseExpression;

    private TernaryExpression(Expression booleanExpression, Expression trueExpression, Expression falseExpression) {
        this.booleanExpression = booleanExpression;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Value evaluate(Context ctx) {
        return booleanExpression.evaluate(ctx).getBoolean()
                ? trueExpression.evaluate(ctx)
                : falseExpression.evaluate(ctx);
    }

    public static class Builder {
        private Expression booleanExpression;
        private Expression trueExpression;
        private Expression falseExpression;

        public Builder booleanExpression(Expression booleanExpression) {
            this.booleanExpression = booleanExpression;
            return this;
        }

        public Builder trueExpression(Expression trueExpression) {
            this.trueExpression = trueExpression;
            return this;
        }

        public Builder falseExpression(Expression falseExpression) {
            this.falseExpression = falseExpression;
            return this;
        }

        public TernaryExpression build() {
            Objects.requireNonNull(booleanExpression, "boolean expression must be not null");
            if (trueExpression == null && falseExpression == null) {
                throw new NullPointerException("At least one of trueExpression or falseExpression must not be null");
            }
            return new TernaryExpression(booleanExpression,
                    trueExpression == null ? Expression.empty : trueExpression,
                    falseExpression == null ? Expression.empty : falseExpression);
        }
    }
}
