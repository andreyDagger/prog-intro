import java.io.IOException;
import java.util.*;

public class C_2 {

    static ArrayList<Integer>[] gBlack;
    static ArrayList<Integer>[] gWhite;
    static int h, w;
    static ArrayList<Integer> res = new ArrayList<>();

    static void addEdge(int x, int y) {
        if (Math.abs(x - y) == w + 1) {
            gWhite[x].add(y);
            gWhite[y].add(x);
        }
        else {
            gBlack[x].add(y);
            gBlack[y].add(x);
        }
    }

    static void eulerPath(int v, int c) {
        if (c == 1) {
            while (gBlack[v].size() > 0) {
                int to = gBlack[v].get(gBlack[v].size() - 1);
                gBlack[v].remove(gBlack[v].size() - 1);
                eulerPath(to, c ^ 1);
                res.add(v);
            }
        }
        else {
            while (gWhite[v].size() > 0) {
                int to = gWhite[v].get(gWhite[v].size() - 1);
                gWhite[v].remove(gWhite[v].size() - 1);
                eulerPath(to, c ^ 1);
                res.add(v);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        w = sc.nextInt();
        h = sc.nextInt();

        gBlack = new ArrayList[(w + 1) * (h + 1)];
        gWhite = new ArrayList[(w + 1) * (h + 1)];
        for (int i = 0; i < gBlack.length; i++) {
            gBlack[i] = new ArrayList<>();
            gWhite[i] = new ArrayList<>();
        }

        sc.nextLine();
        int start = 0;
        for (int i = 0; i < h; i++) {
            String s = sc.nextLine();
            for (int j = 0; j < w; j++) {
                if (s.charAt(j) == 'X') {
                    int lu = i * (w + 1) + j;
                    int ru = lu + 1;
                    int ld = lu + w + 1;
                    int rd = ld + 1;
                    addEdge(rd, lu);
                    addEdge(ld, ru);
                    addEdge(lu, ld);
                    addEdge(ru, rd);
                    start = lu;
                }
            }
        }

        eulerPath(start, 1);
        System.out.println(res.size() - 1);
        for (int x : res) {
            System.out.println(x % (w + 1) + " " + x / (w + 1));
        }
    }
}