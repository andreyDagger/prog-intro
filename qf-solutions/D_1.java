import java.util.ArrayList;
import java.util.Scanner;

public class D_1 {
    static final int MOD = 998244353;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), k = sc.nextInt();
        ArrayList<Integer>[] divisors = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            divisors[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                divisors[j].add(i);
            }
        }
        long[] d = new long[n + 1];
        long[] r = new long[n + 1];
        long[] pw = new long[n + 1];
        pw[0] = 1;
        for (int i = 1; i <= n; i++) {
            pw[i] = pw[i - 1] * k % MOD;
        }
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                r[i] = ((long)i / 2) * ((pw[i / 2] + pw[i / 2 + 1]) % MOD) % MOD;
            }
            else {
                r[i] = (long)i * pw[(i + 1) / 2] % MOD;
            }
            d[i] = r[i];
            for (int l : divisors[i]) {
                if (l == i) {
                    continue;
                }
                d[i] = (d[i] - ((i / l) % MOD * d[l]) % MOD + MOD) % MOD;
            }
        }
        long res = 0;
        for (int i = 1; i <= n; i++) {
            for (int l : divisors[i]) {
                res = (res + d[l]) % MOD;
            }
        }
        System.out.println(res);
    }
}