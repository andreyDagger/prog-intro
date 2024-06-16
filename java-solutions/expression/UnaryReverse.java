package expression;

public class UnaryReverse extends UnaryOperation {
    public UnaryReverse(AnyExpression expression) {
        super("reverse", Priority.UNARY_REVERSE, expression);
    }

    private int reverse(int x) {
        int result = 0;
        String strX = Long.toString(Math.abs((long)x));
        for (int i = strX.length() - 1; i >= 0; i--) {
            result = 10 * result + Integer.parseInt(Character.toString(strX.charAt(i)));
        }
        return result * MyMath.sign(x);
    }

    @Override
    public double evaluate(double x) {
        throw new AssertionError("Reverse for double's are not supported yet");
    }

    @Override
    public int evaluate(int x) {
        return reverse(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return reverse(expression.evaluate(x, y, z));
    }
}
