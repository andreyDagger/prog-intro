package expression.exceptions;

import expression.TripleExpression;
import expression.myExceptions.ParseException;
import expression.parser.RealExpressionParser;

public class ExpressionParser implements TripleParser {

    public TripleExpression parse(String expression) throws ParseException {
        return new RealExpressionParser(expression, true).parse();
    }
}
