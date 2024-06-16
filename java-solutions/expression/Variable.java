package expression;

public class Variable extends AnyExpression {
    private final String name;

    public Variable(String name) {
        super(Priority.VARIABLE);
        if (!name.equals("x") && !name.equals("y") && !name.equals("z")) {
            throw new IllegalArgumentException("Variable must be x, y or z");
        }
        this.name = name;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object expression) {
        if (expression instanceof Variable variable) {
            return name.equals(variable.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x" -> {
                return x;
            }
            case "y" -> {
                return y;
            }
            case "z" -> {
                return z;
            }
            default -> {
                throw new IllegalArgumentException("Only 'x', 'y' and 'z' supported as names of variables");
            }
        }
    }

    @Override
    public double evaluate(double x) {
        return x;
    }
}
