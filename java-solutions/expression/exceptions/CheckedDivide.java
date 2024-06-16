package expression.exceptions;

import expression.AnyExpression;
import expression.Divide;
import expression.MyMath;

public class CheckedDivide extends Divide {
    public CheckedDivide(AnyExpression leftExpression, AnyExpression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public int evaluate(int x) throws ArithmeticException {
        return MyMath.divideExact(leftExpression.evaluate(x), rightExpression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) throws ArithmeticException {
        return MyMath.divideExact(leftExpression.evaluate(x, y, z), rightExpression.evaluate(x, y, z));
    }
}
