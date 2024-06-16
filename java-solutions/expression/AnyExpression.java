package expression;

public abstract class AnyExpression implements Expression, DoubleExpression, TripleExpression {
    protected final int priority;

    public AnyExpression(int priority) {
        this.priority = priority;
    }
}
