package prob_202503.week2.panda_1937;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

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
                result = Math.max(result, dfs(r, c, r, c, 1));
            }
        }

        printTable();

        System.out.println("result = " + result);

    }

    public static int dfs(int start, int end, int row, int col, int depth) {

        if (table[start][end] != -1)
            return table[start][end];

        // 시작 점을 정하고, 상하좌우로 이동한다.
        System.out.printf("visit forest[%d][%d], depth = %d\n", row, col, depth);
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

        return table[start][end];

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
