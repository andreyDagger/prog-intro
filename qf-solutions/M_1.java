import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class M_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (; t > 0; t--) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }

            int res = 0;
            Map<Integer, Integer> cnt = new HashMap<>();
            for (int j = 1; j < n; j++) {
                cnt.put(a[j - 1], cnt.getOrDefault(a[j - 1], 0) + 1);
                for (int k = j + 1; k < n; k++) {
                    res += cnt.getOrDefault(2 * a[j] - a[k], 0);
                }
            }
            System.out.println(res);
        }
    }
}
