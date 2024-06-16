public class SumLongOctal {
    public static void main(String[] args) {
        long res = 0;
        for (String str: args) {
            for (int j = 0; j < str.length();) {
                while (j < str.length() && Character.isWhitespace(str.charAt(j))) {
                    j++;
                }
                StringBuilder number = new StringBuilder();
                while (j < str.length() && !Character.isWhitespace(str.charAt(j))) {
                    number.append(str.charAt(j));
                    j++;
                }

                if (number.isEmpty()) {
                    continue;
                }
                String stringNumber = number.toString();
                int number_length = stringNumber.length();
                char last = stringNumber.charAt(number_length - 1);
                if (last == 'o' || last == 'O') {
                    res += Long.parseLong(stringNumber.substring(0, number_length - 1), 8);
                } else {
                    res += Long.parseLong(stringNumber);
                }
            }
        }
        System.out.println(res);
    }
}
