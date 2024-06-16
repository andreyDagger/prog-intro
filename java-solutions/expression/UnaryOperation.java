package expression;

public abstract class UnaryOperation extends Operation {
    protected AnyExpression expression;

    public UnaryOperation(String operation, int priority, AnyExpression expression) {
        super(operation, priority);
        this.expression = expression;
    }

    @Override
    public String toMiniString() {
        if (expression.priority >= priority) {
            return operation + " " + expression.toMiniString();
        }
        return operation + "(" + expression.toMiniString() + ")";
    }

    @Override
    public String toString() {
        return operation + "(" + expression + ")";
    }
}
