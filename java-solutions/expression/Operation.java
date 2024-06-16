package expression;

public abstract class Operation extends AnyExpression {
    protected final String operation;

    public Operation(String operation, int priority) {
        super(priority);
        this.operation = operation;
    }
}
