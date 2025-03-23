package prob_202503.week3.puzzle_1525;

import java.util.*;
import java.io.*;
import java.math.*;

// 처음 풀이
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int[][] puzzle;
    public static int r, c;

    // 퍼즐의 상태를 하나의 문자열로 표현해 저장하고, 이걸 visited로 활용해서 기록. 노드 방문여부가 아니라 퍼즐의 상태를 기억해서 bfs를 돌려야 함.
    // 예를 들어 0번 칸이 2행 1열에 위치했다고 했을 때, 0번 칸이 그 칸에 도달하는 경로는 위, 아래, 오른쪽 총 3가지임.
    // 각 방향에서 0번 칸이 2행 1열로 이동해 왔다면, 각각 3가지의 다른 퍼즐 판이 만들어짐. 기존 칸과 값을 바꾸면서 움직이는데, 3개의 각각 다른 칸과 값을 바꾸면서 이동했기 때문.
    // ex. 3 1 2        0 1 2      3 1 2      3 1 2
    //     0 5 6   =>   3 5 6  or  5 0 6  or  4 5 6
    //     4 8 7        4 8 7      4 8 7      0 8 7
    // 예시의 칸은 서로 다른 3가지 상황으로부터 만들어질 수 있음. 즉 노드 방문을 visited 처리하는게 아니라 각 퍼즐의 상태를 저장해서 방문 여부(중복 여부)를 걸러내야 함.
    public static Set<String> codeSet = new HashSet<>();

    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};

    public static void init() throws Exception {
        puzzle = new int[3][3];
        StringTokenizer tkn;
        for(int i=0; i<3; i++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int j=0; j<3; j++) {
                puzzle[i][j] = Integer.parseInt(tkn.nextToken());
                if (puzzle[i][j] == 0) {
                    r = i;
                    c = j;
                }
            }
        }

    }

    public static void printPuzzle(int[][] puzzle) {
        System.out.println("printPuzzle()");
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = bfs(r, c, puzzle);
        System.out.println("result = " + result);
    }

    public static int bfs(int row, int col, int[][] puzzle) {
        int depth = 0;

        if (isCorrect(makeCode(puzzle)))
            return depth;

        Queue<Puzzle> q = new LinkedList<>();
        depth++;
        // 상하좌우 이동
        for (int k=0; k<4; k++) {
            int nr = row + dr[k];
            int nc = col + dc[k];

            // 배열 범위를 벗어나지 않고, 방문한 적 없는 칸인 경우 칸 이동
            if (nr >=0 && nc >= 0 && nr < 3 && nc < 3) {
                int[][] copy = copyPuzzle(puzzle);

                // 자리 바꾸기 (한쪽 값이 무조건 0인 swap 로직)
                copy[row][col] = copy[nr][nc]; // 원래 0이 있던 자리는 올길 칸의 값으로 변경
                copy[nr][nc] = 0; // 옮길 칸은 0으로 변경

                // 현재 퍼즐의 상태를 코드로 변경
                String code = makeCode(copy);

                // 옮긴 상태가 조건을 만족하면 depth를 리턴하고 종료
                if (isCorrect(code))
                    return depth;
                // 만족하지 않을 경우 현재 상태를 code로 만들어 hashSet에 저장
                else {
                    Puzzle p = new Puzzle(copy, nr, nc);
                    q.add(p);
                    System.out.println("puzzle added = " + p);
                    codeSet.add(makeCode(copy));
                }
            }

        }

        // 큐가 빌 때까지 반복
        while(!q.isEmpty()) {
            depth++;

            int length = q.size();

            // 처음에 큐에 들어있던 노드들을 대상으로 반복 (이전에 계산된 length)
            for(int i=0; i<length; i++) {
                Puzzle p = q.poll(); // 여기서 꺼내진 퍼즐은 조건을 만족하지 않는 것들이므로 상하좌우를 탐색한다.
                System.out.println("current p (polled) = " + p);

                // 상하좌우 탐색
                for (int k=0; k<4; k++) {
                    int nr = p.row + dr[k];
                    int nc = p.col + dc[k];

                    int[][] board_copy = copyPuzzle(p.board);

                    // 배열 범위를 벗어나지 않고, 방문한 적 없는 칸인 경우 칸 이동
                    if (nr >=0 && nc >= 0 && nr < 3 && nc < 3) {
                        int[][] copy = copyPuzzle(board_copy);

                        // 자리 바꾸기 (한쪽 값이 무조건 0인 swap 로직)
                        copy[p.row][p.col] = copy[nr][nc]; // 원래 0이 있던 자리는 올길 칸의 값으로 변경
                        copy[nr][nc] = 0; // 옮길 칸은 0으로 변경

                        String code = makeCode(copy);

                        // 옮긴 상태가 조건을 만족하면 depth를 리턴하고 종료
                        if (isCorrect(code))
                            return depth;
                            // 만족하지 않을 경우 현재 상태를 Puzzle 객체로 만들어 큐에 저장
                        else {
                            if (!codeSet.contains(code)) {
                                Puzzle new_p = new Puzzle(copy, nr, nc);
                                q.add(new_p);
                                codeSet.add(code);
                                System.out.println("puzzle added: new_p = " + new_p);
                            }
                        }
                    }

                }

            }

        }

        // 1. 현재 노드 (row, col)을 기준으로 상하좌우를 이동하며 탐색한다.
        // 2. 그 중 조건에 맞는게 있으면 현재 depth를 리턴하고 종료한다.
        // 3. 조건을 만족하는게 없으면 다음 depth로 bfs를 돌린다.

        return -1;
    }

    public static int[][] copyPuzzle(int[][] original) {
        int[][] copy = new int[3][3];
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public static String makeCode(int[][] puzzle) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                sb.append(puzzle[i][j]);
        return sb.toString();
    }

    // 조건을 만족하는지 체크
    public static boolean isCorrect(String code) {
        return code.equals("123456780");
    }

    static class Puzzle {
        public int[][] board;
        public int row;
        public int col;

        public Puzzle(int[][] board, int row, int col) {
            this.board = board;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("row = %d, col = %d\n", row, col));

            sb.append("board\n");
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    sb.append(board[i][j]).append(" ");
                }
                sb.append("\n");
            }
            sb.append("\n");

            return sb.toString();
        }

    }

}
