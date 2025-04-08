package prob_202504.week2.mazeEscapeCommand_150365;

import java.net.URL;
import java.util.*;
import java.io.*;
import java.math.*;

public class Solution {

    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, -1, 1, 0};
    static String[] cmds = {"d", "l", "r", "u"};
    // d
    // ddd
    // ddl ...
    // dll ...
    // ..

    static String result = "z";
    static int N, M, K;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        // n, m은 격자 크기
        // x, y는 출발지 좌표
        // r, c는 목적지 좌표
        // k는 이동 횟수
        N = n; M = m; K=k;

        String answer = "";

        if (isImpossible(x, y, r, c))
            answer = "impossible";
        else {
            dfs(r, c, x, y, 0, "");
            // System.out.println("result = " + result);
            answer = result;
        }

        return answer;
    }

    static void dfs(int e_r, int e_c, int r, int c, int moves, String cmd) {

        // moves + 현재 위치에서 목적지까지 최단거리가 k보다 크면 리턴
        // if (isFailRoute(r, c, e_r, e_c, moves))
        if (isFailRoute(r, c, e_r, e_c, moves) || cmd.compareTo(result) > 0)
            return;

        if (moves == K && r == e_r && c == e_c) {
            // result = (result.compareTo(cmd) > 0) ? cmd : result;
            result = cmd;
            return;
        }

        int nr, nc;
        // 상하좌우 이동 -> 무지성 사방으로 이동햐는 dfs
        for(int i=0; i<4; i++) {
            // d, l, r, u
            nr = r + dr[i]; // +1
            nc = c + dc[i]; // +0
            // cmd: "ddl"

            // i = 0 -> 아래로 내려가겠단 의미
            // dr[0] = 1
            // dc[0] = 0
            // cmds[0] = "d"

            // cmd: "ddl" + "d
            if (nr > 0 && nc > 0 && nr <= N && nc <= M) {
                // end_row end_col
                dfs(e_r, e_c, nr, nc, moves+1, cmd + cmds[i]);
            }
        }

    }

    // 출발지에서 목적지로 애초에 도달이 가능한지 판별.
    // 목적지까지 최단 거리가 이동회수 k보다 크거나, 최단거리 도달 이후 남은 이동 횟수가 짝수가 아닌 경우 도달 불가 (도착한 뒤 제자리 왔다갔다 불가능하면 도달 불가)
    static boolean isImpossible(int r, int c, int e_r, int e_c) {
        int lr = e_r - r;
        int lc = e_c - c;

        int distance = Math.abs(lr) + Math.abs(lc);
        if (distance > K || (K - distance) % 2 != 0)
            return true;
        else
            return false;
    }

    // (지금까지 이동거리) + (현재 위치에서 목적지까지 최단거리)가 K보다 클 경우 도착이 불가능한 루트. 더 이상 탐색하지 않음.
    static boolean isFailRoute(int r, int c, int e_r, int e_c, int moves) {
        int lr = e_r - r;
        int lc = e_c - c;

        int distance = Math.abs(lr) + Math.abs(lc);
        return (moves + distance) > K;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = getFileReader();

        Solution s = new Solution();
        StringTokenizer tkn;
        String line;
        int n, m, x, y, r, c, k;

        while ((line = reader.readLine()) != null) {
            tkn = new StringTokenizer(line);
            n = Integer.parseInt(tkn.nextToken());
            m = Integer.parseInt(tkn.nextToken());
            x = Integer.parseInt(tkn.nextToken());
            y = Integer.parseInt(tkn.nextToken());
            r = Integer.parseInt(tkn.nextToken());
            c = Integer.parseInt(tkn.nextToken());
            k = Integer.parseInt(tkn.nextToken());

            result = "z";
            String sol = s.solution(n, m, x, y, r, c, k);
            System.out.println("sol = " + sol);
        }

    }

    static BufferedReader getFileReader() throws Exception {
        URL urlResource = Solution.class.getResource("input.txt");
        File input = new File(urlResource.toURI());
        FileInputStream fis = new FileInputStream(input);
        return new BufferedReader(new InputStreamReader(fis));
    }

}
