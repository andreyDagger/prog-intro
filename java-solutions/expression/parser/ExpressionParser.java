package expression.parser;

import expression.*;
import expression.myExceptions.ParseException;

public class ExpressionParser implements TripleParser {

    public TripleExpression parse(String expression) {
        //System.out.println(expression);
        try {
            return new RealExpressionParser(expression, false).parse();
        } catch (ParseException e) {
            System.out.println("LOL");
            System.exit(1);
        }
        throw new AssertionError();
    }
}
