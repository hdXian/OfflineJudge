package prob_202503.week2.panda_1937;

import java.util.*;
import java.io.*;
import java.math.*;

// 기존 코드
public class Main2 {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int N;
    public static int[][] forest;

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
                dfs(r, c, r, c, 1);
            }
        }

        for(int r=0; r<N; r++)
            for(int c=0; c<N; c++)
                result = Math.max(result, table[r][c]);

        System.out.println(result);
    }

    public static void dfs(int start, int end, int row, int col, int depth) {
        // 시작 점을 정하고, 상하좌우로 이동한다.
        int val = forest[row][col];
        boolean isEdge = true;

        // 상
        if (row > 0) {
            if (forest[row-1][col] > val) {

                // 테이블에 저장된 값이 있다면 (시작점의 기존 값, 깊이 + 테이블 값) 중 더 큰 것으로 저장a
                if (table[row-1][col] != -1)
                    table[start][end] = Math.max(table[start][end], depth + table[row-1][col]);

                else
                    dfs(start, end, row-1, col, depth+1);

                isEdge = false;
            }
        }

        // 하
        if (row < N-1) {
            if (forest[row+1][col] > val) {

                if (table[row+1][col] != -1)
                    table[start][end] = Math.max(table[start][end], depth + table[row+1][col]);
                else
                    dfs(start, end, row+1, col, depth+1);

                isEdge = false;
            }
        }

        // 좌
        if (col > 0) {
            if (forest[row][col-1] > val) {

                if (table[row][col-1] != -1)
                    table[start][end] = Math.max(table[start][end], depth + table[row][col-1]);
                else
                    dfs(start, end, row, col-1, depth+1);

                isEdge = false;
            }
        }

        // 우
        if (col < N-1) {
            if (forest[row][col+1] > val) {

                if (table[row][col+1] != -1)
                    table[start][end] = Math.max(table[start][end], depth + table[row][col+1]);
                else
                    dfs(start, end, row, col+1, depth+1);

                isEdge = false;
            }
        }

        // 끝에 도달했으면 table 값과 비교해서 저장
        if (isEdge)
            table[start][end] = Math.max(table[start][end], depth);
    }

}
