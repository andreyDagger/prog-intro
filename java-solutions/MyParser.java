public class MyParser implements Parser {
    private void addDigits(StringBuilder result, char c) {
        if (Character.isDigit(c) || c == '-') {
            result.append(c);
        } else if (Character.isAlphabetic(c)) {
            c = Character.toLowerCase(c);
            int integerValue = (int) c - (int) 'a';
            String stringValue = Integer.toString(integerValue);
            for (int i = 0; i < stringValue.length(); i++) {
                result.append(stringValue.charAt(i));
            }
        } else {
            throw new IllegalArgumentException(
                "toDigit must take only alphanumeric characters, but " + c + " was taken");
        }
    }

    private int getRadix(StringBuilder number) {
        int result = 10;
        char lastChar = number.charAt(number.length() - 1);
        if (lastChar == 'o' || lastChar == 'O') {
            number.deleteCharAt(number.length() - 1);
            result = 8;
        }
        return result;
    }

    public int parseInteger(StringBuilder rawNumber) {
        int radix = getRadix(rawNumber);

        int res = 0;

        StringBuilder parsedInteger = new StringBuilder();
        for (int i = 0; i < rawNumber.length(); i++) {
            addDigits(parsedInteger, rawNumber.charAt(i));
        }

        int sign = 1;
        if (parsedInteger.charAt(0) == '-') {
            sign = -1;
            parsedInteger.deleteCharAt(0);
        }

        int pw = 1;
        for (int i = parsedInteger.length() - 1; i >= 0; i--) {
            res += pw * Character.getNumericValue(parsedInteger.charAt(i));
            pw *= radix;
        }

        return sign * res;
    }
}
