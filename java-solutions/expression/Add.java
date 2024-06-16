package expression;

import expression.exceptions.CheckedAdd;

public class Add extends BinaryOperation {
    public static class AddInfo extends BinaryOperationInfo {
        public AddInfo() {
            super(Priority.ADD);
        }

        @Override
        public Add getBinaryOperationInstance(AnyExpression leftExpression, AnyExpression rightExpression, boolean isChecked) {
            if (isChecked) {
                return new CheckedAdd(leftExpression, rightExpression);
            } else {
                return new Add(leftExpression, rightExpression);
            }
        }
    }

    public Add(AnyExpression leftExpression, AnyExpression rightExpression) {
        super("+", Priority.ADD, leftExpression, rightExpression);
    }

    @Override
    public String toMiniString() {
        String leftString = leftExpression.toMiniString();
        String rightString = rightExpression.toMiniString();

        if (leftExpression.priority < priority) {
            leftString = "(" + leftString + ")";
        }
        if (rightExpression.priority < priority) {
            rightString = "(" + rightString + ")";
        }
        return leftString + " " + operation + " " + rightString;
    }

    @Override
    public int evaluate(int x) {
        return leftExpression.evaluate(x) + rightExpression.evaluate(x);
    }

    @Override
    public boolean equals(Object expression) {
        if (expression instanceof Add add) {
            return leftExpression.equals(add.leftExpression) && rightExpression.equals(add.rightExpression);
        }
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return leftExpression.evaluate(x, y, z) + rightExpression.evaluate(x, y, z);
    }

    @Override
    public double evaluate(double x) {
        return leftExpression.evaluate(x) + rightExpression.evaluate(x);
    }
}
