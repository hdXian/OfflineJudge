package ct_exercise.swea.max_prize_1244;

import java.util.*;
import java.io.*;

class Solution2 {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int clength = 0;
    static int[] cardArr;
    static int N = 0;
    static int result = 0;

    static String sortArr() {
        Arrays.sort(cardArr);
        StringBuilder sb = new StringBuilder();
        for(int n: cardArr) sb.append(n);
        return sb.reverse().toString(); // 내림차순이므로 뒤집어서 리턴
    }

    static void swap(int a, int b) {
        int tmp = cardArr[a];
        cardArr[a] = cardArr[b];
        cardArr[b] = tmp;
    }

    static void dfs(int prei, int depth) {
        if (depth == N) {
            int tmp = 0;
            for(int i=0; i<clength; i++) {
                tmp += (int) Math.pow(10, clength-1-i) * cardArr[i];
            }
            result = Math.max(result, tmp);
        }
        else {
            for(int i=prei; i<clength; i++) {
                for(int j=i+1; j<clength; j++) {
                    swap(i, j);
                    dfs(i, depth+1);
                    swap(i, j); // 백트래킹
                }
            }
        }

    }

    static void init(String line) {
        StringTokenizer tkn = new StringTokenizer(line);

        String cards = tkn.nextToken(); // 숫자 카드 (문자열)
        N = Integer.parseInt(tkn.nextToken()); // 교환 횟수.

        cardArr = new int[cards.length()];
        for(int i=0; i<cardArr.length; i++) {
            cardArr[i] = cards.charAt(i) - '0';
        }

        clength = cardArr.length;
        result = 0;

        // 교환 횟수의 최적화가 필요함
        // 교환 횟수가 카드 개수보다 많다면 선택정렬을 통한 내림차순 정렬이 최댓값임.
        // 1. 교환 횟수가 카드 개수보다 많으면 (교환 횟수 - 배열 길이)로 남은 교환 횟수를 구한다.
        // 2. 남은 교환 횟수가 짝수번이면 같은 위치를 짝수번 바꿈으로써 내림차순 정렬 상태를 유지할 수 있다.
        // 3. 남은 교환 횟수가 홀수번이면 내림차순 정렬 상태에서 최대한 수가 덜 작아지도록 교환해야 한다.
        // 그건 제일 끝 두 자리를 교환하는 거겠지.

    }

    static boolean isDup() {
        for(int i=0; i<clength; i++) {
            for(int j=i+1; j<clength; j++) {
                if (cardArr[i] == cardArr[j])
                    return true;
            }
        }

        return false;
    }

    static String calc(int t) {

        // 교환 횟수가 (카드 개수-1)보다 클 경우 -> 내림차순 정렬 후 판단
        // 여기서 잔여 교환 횟수를 제대로 찾아야 함. (즉 이 코드는 틀린 답)
        if (N >= (clength-1)) {
            int res = N % 2;
            String sorted = sortArr();

            // 남은 교환 횟수가 홀수일 경우 -> 내림차순 결과에서 끝에 2개만 교환
            // +) 남은 교환 횟수가 홀수여도, 카드 중에 중복된 수가 있다면 끝에를 교환하지 않아도 됨. (중복된 수끼리 교환)
            if (res%2 != 0 && !isDup()) {
                char[] cArr = sorted.toCharArray();
                char tmp = cArr[clength-1];
                cArr[clength-1] = cArr[clength-2];
                cArr[clength-2] = tmp;
                sorted = new String(cArr);
            }
            // 남은 교환 횟수가 짝수인 경우 -> 내림차순 정렬 그대로 리턴

            return String.format("#%d %s\n", t, sorted);
        }
        // 교환 횟수가 (카드개수-1)보다 작을 경우 -> dfs 기반 브루트포스
        else {
            dfs(0, 0);
            return String.format("#%d %d\n", t, result);
        }

    }

    public static void main(String[] args) throws Exception {
        int T;
        T = Integer.parseInt(reader.readLine()); // 테케 개수. 1 ~ 10

        StringBuilder sb = new StringBuilder();
        for (int test_case = 1; test_case <= T; test_case++) {
            String line = reader.readLine();
            init(line); // 카드 배열, 길이, 교환 횟수 초기화
            String result = calc(test_case);
            sb.append(result);
        }
        System.out.println("sb = " + sb);

    }

}
