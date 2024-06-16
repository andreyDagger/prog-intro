package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Must be at least 1 argument");
            return;
        }

        System.out.println(new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(new Const(2),
                                     new Variable("x")
                        )
                ),
                new Const(1)).evaluate(Integer.parseInt(args[0])));

        TripleExpression result = new ExpressionParser().parse("x * (x - 2)*x + 1");
        System.out.println(result);
    }
}
