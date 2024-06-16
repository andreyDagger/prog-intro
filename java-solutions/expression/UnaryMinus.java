package expression;

import expression.exceptions.CheckedNegate;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(AnyExpression expression) {
        super("-", Priority.UNARY_MINUS, expression);
    }

    @Override
    public int evaluate(int x) {
        return -expression.evaluate(x);
    }

    @Override
    public double evaluate(double x) {
        return -expression.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expression.evaluate(x, y, z);
    }
}
