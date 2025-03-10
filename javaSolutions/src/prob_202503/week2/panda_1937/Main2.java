package prob_202503.week2.panda_1937;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main2 {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int N;
    public static int[][] forest;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};

    public static int result;
    public static int[][] table;

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());

        forest = new int[N][N];
        StringTokenizer tkn;
        for (int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());

            for(int j=0; j<N; j++) {
                forest[i][j] = Integer.parseInt(tkn.nextToken());
            }

        }

        table = new int[N][N];
        for(int i=0; i<N; i++)
            Arrays.fill(table[i], -1);

        result = -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        for(int r=0; r<N; r++) {
            for(int c=0; c<N; c++) {
                dfs(r, c);
            }
        }

        printTable();

        for(int r=0; r<N; r++)
            for(int c=0; c<N; c++)
                result = Math.max(result, table[r][c]);

        System.out.println("result = " + result);

    }

    public static int dfs(int row, int col) {
        if (table[row][col] != -1) {
            System.out.printf("cost is already calc-ed. table[%d][%d] = %d\n", row, col, table[row][col]);
            return table[row][col];
        }

        // 시작 점을 정하고, 상하좌우로 이동한다.
        System.out.printf("visit forest[%d][%d]\n", row, col);
        table[row][col] = 1;

        // 상, 하, 좌, 우 좌표 이동
        for(int n=0; n<4; n++) {
            // 움직일 다음 칸의 좌표
            int nr = row + dr[n];
            int nc = col + dc[n];

            if ((nr >= 0) && (nc >= 0) && (nr <= N-1) && (nc <= N-1) && (forest[nr][nc] > forest[row][col])) {
                table[row][col] = Math.max(table[row][col], dfs(nr, nc)+1);
            }

        }

        return table[row][col];

        // 상하좌우로 계속 이동하면서 가능한 경로를 찾고, 더이상 못 움직이면 탐색을 끝낸다.
        // 탐색이 끝났을 때 이동한 값을 result와 비교하고, 더 큰 값으로 업데이트한다.
        // 근데 이걸 무작정 반복하면 중복된 연산이 일어나려나?
        // 그럼 전에 했던 연산을 어딘가 저장해놓으란 얘긴데..
    }

    public static void printTable() {
        System.out.println("printTable()");
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }


}
