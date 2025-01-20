package prob_2024.RedGreen_10026;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        char[][] matrix = new char[n][n];
        char[][] matrix_rg = new char[n][n];

        boolean[][] visited = new boolean[n][n];
        boolean[][] visited_rg = new boolean[n][n];

        for(int i=0; i<n; i++) {
            Arrays.fill(visited[i], false);
            Arrays.fill(visited_rg[i], false);
        }

        String line;
        for(int i=0; i<n; i++) {
            line = reader.readLine();
            matrix[i] = line.toCharArray();
            matrix_rg[i] = line.toCharArray();
        }

        for(char[] arr: matrix_rg) {
            for(int i=0; i<n; i++) {
                if (arr[i] == 'G')
                    arr[i] = 'R';
            }
        }

        for(int i=0; i<n; i++) {
            System.out.println(String.valueOf(matrix[i]));
        }

        System.out.println();
        for(int i=0; i<n; i++) {
            System.out.println(String.valueOf(matrix_rg[i]));
        }

        int count = 0;
        int count_rg = 0;

        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {

                if (!visited[r][c]) {
                    bfs(r, c, matrix[r][c], n, matrix, visited);
                    count++;
                }

                if (!visited_rg[r][c]) {
                    bfs(r, c, matrix_rg[r][c], n, matrix_rg, visited_rg);
                    count_rg++;
                }

            }
        }

        System.out.println("count = " + count);
        System.out.println("count_rg = " + count_rg);

        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                System.out.print(visited[r][c] + " ");
            }
            System.out.println();
        }

        System.out.println();
        for(int r=0; r<n; r++) {
            for(int c=0; c<n; c++) {
                System.out.print(visited_rg[r][c] + " ");
            }
            System.out.println();
        }

    }

    private static void bfs(int row, int col, char ch, int n, char[][] matrix, boolean[][] visited) {

        if (row < 0 || row >= n)
            return;
        if (col < 0 || col >= n)
            return;

        if (matrix[row][col] == ch && !visited[row][col]) {
            visited[row][col] = true;
        }
        else {
            return;
        }

        // 상
        bfs(row-1, col, ch, n, matrix, visited);

        // 하
        bfs(row+1, col, ch, n, matrix, visited);

        // 좌
        bfs(row, col-1, ch, n, matrix, visited);

        // 우
        bfs(row, col+1, ch, n, matrix, visited);

    }


}
