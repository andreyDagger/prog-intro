package expression;

public class Const extends AnyExpression {
    private final Number value;

    public Const(double value) {
        super(Priority.CONST);
        this.value = value;
    }

    public Const(int value) {
        super(Priority.CONST);
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object expression) {
        if (expression instanceof Const constant) {
            return constant.value.equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

    @Override
    public double evaluate(double x) {
        return value.doubleValue();
    }
}
