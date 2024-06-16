package expression.exceptions;

import expression.AnyExpression;
import expression.Multiply;
import expression.MyMath;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AnyExpression leftExpression, AnyExpression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public int evaluate(int x) throws ArithmeticException {
        return MyMath.exactMultiply(leftExpression.evaluate(x), rightExpression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticException {
        return MyMath.exactMultiply(leftExpression.evaluate(x, y, z), rightExpression.evaluate(x, y, z));
    }
}
