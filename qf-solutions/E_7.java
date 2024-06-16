import java.io.IOException;
import java.io.*;
import java.util.*;


public class E_7 {
    static List<Integer>[] graph;
    static int[][] d;
    static int n, m;

    static void bfs(int start, int idx) {
        Queue<Integer> q = new ArrayDeque<>();
        d[idx][start] = 0;
        q.add(start);
        while (q.size() > 0) {
            int v = q.poll();
            for (int to : graph[v]) {
                if (to != start && d[idx][to] == 0) {
                    d[idx][to] = d[idx][v] + 1;
                    q.add(to);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        d = new int[3][n];
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt(), v = sc.nextInt();
            u--;
            v--;
            graph[u].add(v);
            graph[v].add(u);
        }
        int[] c = new int[m];
        for (int i = 0; i < m; i++) {
            c[i] = sc.nextInt();
            c[i]--;
        }
        if (m == 1) {
            System.out.println("YES");
            System.out.println(c[0] + 1);
            System.exit(0);
        }
        bfs(c[0], 0);

        int max_depth = -1, v = -1;
        for (int i : c) {
            if (max_depth < d[0][i]) {
                max_depth = d[0][i];
                v = i;
            }
        }

        bfs(v, 1);
        int u = -1, dist = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (i == v || i == c[0]) {
                continue;
            }
            if (d[0][v] == d[0][i] + d[1][i]) {
                if (d[0][i] == d[1][i]) {
                    u = i;
                    break;
                }
            }
        }
        if (u == -1) {
            System.out.println("NO");
            System.exit(0);
        }
        bfs(u, 2);
        for (int i = 1; i < m; i++) {
            if (d[2][c[i]] != d[2][c[i - 1]]) {
                System.out.println("NO");
                System.exit(0);
            }
        }
        System.out.println("YES");
        System.out.println(u + 1);
    }
}