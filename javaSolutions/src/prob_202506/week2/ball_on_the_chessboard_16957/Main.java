package prob_202506.week2.ball_on_the_chessboard_16957;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // 12시부터 시계방향
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 1, 0, -1, -1, -1};

    static int R, C;

    static int[][] board;
    static int[][] balls;

    static class Point implements Comparable<Point> {
        int number;
        int row, col;
        public Point(int number, int row, int col) {
            this.number = number;
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Point p) {
            return this.number - p.number; // 숫자 기준 오름차순
        }
    }

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        R = Integer.parseInt(tkn.nextToken());
        C = Integer.parseInt(tkn.nextToken());

        board = new int[R][C];
        balls = new int[R][C];

        for(int i=0; i<R; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<C; j++) {
                board[i][j] = Integer.parseInt(tkn.nextToken());
            }
        }

    }

    static Point search(Point start) {

        PriorityQueue<Point> pq = new PriorityQueue<>();
        pq.add(start);
        Point cur = start;

        while(!pq.isEmpty()) {
            cur = pq.poll();
            int r = cur.row;
            int c = cur.col;

            pq.clear();

            for(int i=0; i<8; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                if (nr >=0 && nc >=0 && nr < R && nc < C && board[nr][nc] < board[r][c]) {
                    pq.add(new Point(board[nr][nc], nr, nc));
                }
            }

        }

        return cur;
    }

    static void calc() {
        // 모든 요소들에 대해 탐색을 돌린다.
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                Point result = search(new Point(board[i][j], i, j));
                balls[result.row][result.col]++;
            }
        }

        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                System.out.print(balls[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
