package expression.parser;

import expression.*;
import expression.exceptions.CheckedNegate;
import expression.myExceptions.*;

import java.util.ArrayList;
import java.util.List;

public final class RealExpressionParser extends BaseParser {

    private final static List<String> operationsStringList = List.of("+", "-", "*", "/", "gcd", "lcm", "reverse");

    private Element prevElement = Element.NONE;
    private int balance = 0;
    private final boolean throwsExceptions;

    public RealExpressionParser(CharSource source, boolean throwsExceptions) {
        super(source);
        this.throwsExceptions = throwsExceptions;
    }

    public RealExpressionParser(String source, boolean throwsExceptions) {
        this(new StringSource(source), throwsExceptions);
    }

    public AnyExpression parse() throws ParseException {
        balance = 0;
        prevElement = Element.NONE;

        AnyExpression expression = parseExpression();
        if (balance != 0) {
            throw new BracketsBalanceException("brackets don't match");
        }
        return expression;
    }

    private AnyExpression parseExpression() throws ParseException {
        List<AnyExpression> expressionList = new ArrayList<>();
        List<BinaryOperation.BinaryOperationInfo> operations = new ArrayList<>();

        while (true) {
            skipWhitespaces();
            if (test(')') || eof()) {
                if (test(')')) {
                    balance--;
                    if (balance < 0) {
                        throw new BracketsBalanceException("brackets don't match");
                    }
                    take();
                }
                break;
            }
            if (operation() && !unary()) {
                operations.add(parseBinaryOperation());
            } else {
                expressionList.add(parseSingleToken());
            }
        }

        return buildExpression(expressionList, operations);
    }

    private String readUnaryOperation() {
        if (take("reverse")) {
            return "reverse";
        } else if (take("-")) {
            return "-";
        }
        throw new AssertionError("Not an unary operation");
    }

    private boolean unaryMinus() {
        return (test('-') && (prevElement == Element.OPERATION ||
                prevElement == Element.NONE ||
                prevElement == Element.OPEN_BRACKET));
    }

    private boolean unary() {
        return unaryMinus() || test("reverse");
    }

    private AnyExpression parseSingleToken() throws ParseException {
        //           Example:
        // 1 + (2 * -( reverse (4 + 5)))
        // Tokens: 1, 2 * -( reverse (4 + 5))
        //                       |
        //                       V
        //              Tokens: 2, -( reverse (4 + 5))
        //                               |
        //                               V
        //                         Tokens: 4, 5

        skipWhitespaces();
        if (take('(')) {
            balance++;
            prevElement = Element.OPEN_BRACKET;
            return parseExpression();
        } else if (take(')')) {
            throw new BadUnaryOperationException("After unary operation can't be ')'");
        } else if (between('0', '9')) {
            return new Const(parseInteger(false));
        } else if (operation()) {
            if (unary()) {
                prevElement = Element.OPERATION;
                String unaryOperation = readUnaryOperation();
                if (unaryOperation.equals("-")) {
                    if (between('0', '9')) {
                        return new Const(parseInteger(true));
                    } else {
                        if (throwsExceptions) {
                            return new CheckedNegate(parseSingleToken());
                        } else {
                            return new UnaryMinus(parseSingleToken());
                        }
                    }
                } else if (unaryOperation.equals("reverse")) {
                    skipWhitespaces();
                    return new UnaryReverse(parseSingleToken());
                }
            } else {
                throw new BinaryOperationMatchingException("Binary operations don't match with variables and numbers");
            }
        } else if (variable()) {
            return parseVariable();
        } else if (eof()) {
            throw new UnexpectedEofException("Unexpected eof");
        }
        throw new InvalidCharacterException("Invalid character " + take() + ". ");
    }

    private AnyExpression parseVariable() {
        prevElement = Element.VARIABLE;
        return new Variable(Character.toString(take()));
    }

    private boolean variable() {
        return Character.isAlphabetic(look());
    }

    private BinaryOperation.BinaryOperationInfo parseBinaryOperation() {
        prevElement = Element.OPERATION;
        if (take("gcd")) {
            return new Gcd.GcdInfo();
        } else if (take("lcm")) {
            return new Lcm.LcmInfo();
        } else if (take("*")) {
            return new Multiply.MultiplyInfo();
        } else if (take("/")) {
            return new Divide.DivideInfo();
        } else if (take("-")) {
            return new Subtract.SubtractInfo();
        } else if (take("+")) {
            return new Add.AddInfo();
        }
        throw new AssertionError("Unknown operation");
    }

    private int parseInteger(boolean negated) {
        prevElement = Element.NUMBER;
        StringBuilder number = new StringBuilder();
        if (negated) {
            number.append('-');
        }
        while (true) {
            if (between('0', '9')) {
                number.append(take());
            } else {
                break;
            }
        }

        return Integer.parseInt(number.toString());
    }

    private AnyExpression buildExpression(List<AnyExpression> expressionList,
                                          List<BinaryOperation.BinaryOperationInfo> operationList)
            throws BinaryOperationMatchingException{

        if (operationList.size() + 1 != expressionList.size()) {
            throw new BinaryOperationMatchingException("Binary operations don't match with variables and numbers");
        }
        if (operationList.isEmpty()) {
            return expressionList.get(0);
        }

        int min_priority = Integer.MAX_VALUE;
        for (BinaryOperation.BinaryOperationInfo binaryOperationInfo : operationList) {
            if (min_priority > binaryOperationInfo.getPriority()) {
                min_priority = binaryOperationInfo.getPriority();
            }
        }

        AnyExpression result = null;
        BinaryOperation.BinaryOperationInfo prevOperation = null;
        List<AnyExpression> expressionSublist = new ArrayList<>();
        List<BinaryOperation.BinaryOperationInfo> operationSublist = new ArrayList<>();
        for (int i = 0; i < expressionList.size(); i++) {
            expressionSublist.add(expressionList.get(i));
            if (i == expressionList.size() - 1 || operationList.get(i).getPriority() == min_priority) {
                AnyExpression subExpression = buildExpression(expressionSublist, operationSublist);
                if (prevOperation == null) {
                    result = subExpression;
                } else {
                    result = prevOperation.getBinaryOperationInstance(result, subExpression, throwsExceptions);
                }
                expressionSublist.clear();
                operationSublist.clear();
                if (i == expressionList.size() - 1) {
                    break;
                }
                prevOperation = operationList.get(i);
            } else {
                operationSublist.add(operationList.get(i));
            }
        }
        return result;
    }

    private boolean operation() {
        for (String op : operationsStringList) {
            if (test(op)) {
                return true;
            }
        }
        return false;
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(look())) {
            take();
        }
    }
}