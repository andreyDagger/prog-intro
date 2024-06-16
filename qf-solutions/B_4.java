import java.util.Scanner;

public class B_4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        final int MAX_N = 50_000;
        final int STEP = 710;
        for (int i = 0; i < n; i++) {
            System.out.println((i - MAX_N / 2) * STEP);
        }
    }
}