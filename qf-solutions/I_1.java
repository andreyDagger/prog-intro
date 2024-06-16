import java.util.Scanner;

public class I_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int xl = 1_000_000_000, xr = -1_000_000_000;
        int yl = 1_000_000_000, yr = -1_000_000_000;
        for (; n > 0; n--) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int h = sc.nextInt();

            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }
        int h_res = (Math.max(xr - xl, yr - yl) + 1) / 2;
        System.out.println((xl + xr) / 2 + " " + (yl + yr) / 2 + " " + h_res);
    }
}