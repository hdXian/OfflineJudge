package prob_202503.week2.panda_1937;

import java.util.*;
import java.io.*;
import java.math.*;

// 해설 참고 코드
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int N;
    public static int[][] forest;

    public static int result;
    public static int[][] table;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};

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
                result = Math.max(result, dfs(r, c));
            }
        }

        printTable();

        System.out.println("result = " + result);

    }

    public static int dfs(int row, int col) {
        if (table[row][col] != -1)
            return table[row][col];

        // 시작 점을 정하고, 상하좌우로 이동한다.
        System.out.printf("visit forest[%d][%d]\n", row, col);
        table[row][col] = 1;

        // 상하좌우 체크
        for(int k=0; k<4; k++) {
            int nr = row + dr[k];
            int nc = col + dc[k];

            // 조건을 만족하면
            if ((nr >= 0) && (nc >= 0) && (nr <= N-1) && (nc <= N-1) && (forest[nr][nc] > forest[row][col])) {
                table[row][col] = Math.max(table[row][col], dfs(nr, nc)+1); // 지금까지 저장된 값과, 다른 칸으로 이동했을 때의 움직일 수 있는 값을 비교.
            }

        }

        // 더이상 못 움직이는 경우 for문을 못 돌고 1로 초기화된 테이블 값을 뱉을꺼임.
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
