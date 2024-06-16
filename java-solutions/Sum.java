public class Sum {
    public static void main(String[] args) {
        int res = 0;
        for (int i = 0; i < args.length; i++) {
            String str = args[i];
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
                res += Integer.parseInt(number.toString());
            }
        }
        System.out.println(res);
    }
}
