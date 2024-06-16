import java.util.Scanner;

public class J_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < n; j++) {
                matrix[i][j] = line.charAt(j) - '0';
            }
        }
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || matrix[i][j] == 0) {
                    continue;
                }
                res[i][j] = 1;
                for (int k = j + 1; k < n; k++) {
                    matrix[i][k] = (matrix[i][k] - matrix[j][k] + 10) % 10;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(res[i][j]);
            }
            System.out.println();
        }
    }
}