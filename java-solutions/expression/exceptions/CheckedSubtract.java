package expression.exceptions;

import expression.AnyExpression;
import expression.MyMath;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AnyExpression leftExpression, AnyExpression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public int evaluate(int x) throws ArithmeticException {
        return MyMath.subtractExact(leftExpression.evaluate(x), rightExpression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticException {
        return MyMath.subtractExact(leftExpression.evaluate(x, y, z), rightExpression.evaluate(x, y, z));
    }
}
