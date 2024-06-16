package expression;

public class MyMath {
    public static int sign(int a) {
        return Integer.compare(a, 0);
    }

    public static int calculate_gcd(int a, int b) {
        long longA = Math.abs((long)a);
        long longB = Math.abs((long)b);
        while (longA > 0 && longB > 0) {
            if (longA >= longB) {
                longA %= longB;
            } else {
                longB %= longA;
            }
        }
        return (int)Math.max(longA, longB);
    }

    public static int calculate_lcm(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
            //throw new ArithmeticException("LCM is not supported when exactly one zero value");
        }
        return a / calculate_gcd(a, b) * b;
    }

    public static int exactMultiply(int a, int b) throws ArithmeticException {
        if (a == 0 || b == 0) {
            return 0;
        } else if (a == 1) {
            return b;
        } else if (b == 1) {
            return a;
        } else if (a == -1) {
            return negateExact(b);
        } else if (b == -1) {
            return negateExact(a);
        } else if (a == Integer.MIN_VALUE || a == Integer.MAX_VALUE || b == Integer.MIN_VALUE || b == Integer.MAX_VALUE) {
            throw new ArithmeticException("overflow");
        }
        int sign = MyMath.sign(a) * MyMath.sign(b);
        a = Math.abs(a);
        b = Math.abs(b);
        if (sign == 1) {
            if (a > Integer.MAX_VALUE / b) {
                throw new ArithmeticException("overflow");
            }
        } else if (sign == -1) {
            int right = Integer.MAX_VALUE / b;
            if (Integer.MAX_VALUE % b == b - 1) {
                right++;
            }
            if (a > right) {
                throw new ArithmeticException("overflow");
            }
        }
        return sign * a * b;
    }

    public static int addExact(int a, int b) throws ArithmeticException {
        int r = a + b;
        if (((a ^ r) & (b ^ r)) < 0) {
            throw new ArithmeticException("overflow");
        }
        return r;
    }

    public static int divideExact(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("division by zero");
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ArithmeticException("overflow");
        }
        return a / b;
    }

    public static int subtractExact(int a, int b) throws ArithmeticException {
        int r = a - b;
        if (((a ^ b) & (a ^ r)) < 0) {
            throw new ArithmeticException("overflow");
        }
        return r;
    }

    public static int negateExact(int a) throws ArithmeticException {
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -a;
    }
}
