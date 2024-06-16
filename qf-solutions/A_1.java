import java.util.Scanner;

public class A_1 {
    public static void main(String[] args) {
        int a, b, n;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();
        n = sc.nextInt();
        System.out.println(2 * ((n - a - 1) / (b - a)) + 1);
    }
}
