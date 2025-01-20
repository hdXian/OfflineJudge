package prob_2024.Liar_1043;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    static List<List<Integer>> parties = new ArrayList<>();
    static int max_lie = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 사람 수, 파티 수
        int n, m;

        // 사람 수, 파티 수 입력받기 (1번째 줄 입력)
        StringTokenizer st = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 진실을 들은 사람들
        int[] isKnown = new int[n+1];
        int[] confused = new int[n+1];
        IntStream.range(0, n+1)
                .forEach((i) -> {
                    isKnown[i] = 0;
                    confused[i] = 0;
                });

        // 진실을 아는 사람 세팅하기 (2번째 줄 입력)
        st = new StringTokenizer(reader.readLine());
        int num = Integer.parseInt(st.nextToken());
        // 사람 수가 0이 아니면 -> 진실을 아는 사람들 번호를 받아와서 배열에 1로 세팅
        if (num != 0) {
            int tmp;
            while (st.hasMoreTokens()) {
                tmp = Integer.parseInt(st.nextToken());
                isKnown[tmp] = 1;
            }
        }

        // 파티 입력받기 (3번째 줄 이후)
        int people;
        for(int i=0; i<m; i++) {

            st = new StringTokenizer(reader.readLine());
            people = Integer.parseInt(st.nextToken());

            List<Integer> party = new ArrayList<>();
            for(int x=0; x<people; x++) {
                party.add(Integer.valueOf(st.nextToken()));
            }

            parties.add(party);

        }
        
        dfs(0, parties.size(), isKnown, confused, 0);
        System.out.println("\n\nmax_lie = " + max_lie);

    }

    static void dfs(int idx, int length, int[] isKnown, int[] confused, int lieNum) {
        System.out.println("in dfs(idx=" + idx + ", length=" + length + ", isKnown=" + Arrays.toString(isKnown));

        // 탐색 끝 -> 거짓말 횟수 비교
        if (idx == length) {
            if (lieNum > max_lie)
                max_lie = lieNum;
            System.out.println("end of search");
            System.out.println("lieNum = " + lieNum);
            return;
        }

        List<Integer> party = parties.get(idx);
        System.out.println("current party = " + party);

        // 지금 파티에서 진실 혹은 거짓말이 가능한지 판단
        boolean canTruth = checkToTell(party, confused);
        boolean canLie = checkToTell(party, isKnown); // 거짓말 가능하면 true, 불가능하면 false

        // 진실을 말한 경우와 거짓을 말한 경우에 대해 다시 dfs. (거짓말이 불가한 경우 거짓을 말하는 경우는 생략)

        // 가능할경우 진실을 말하는 경우를 탐색 - 진실을 들은 사람을 업데이트하고 다음 dfs 진행
        if(canTruth) {
            System.out.println("able to tell truth");
            int[] next_known = isKnown.clone();
            updateArr(party, next_known);
            dfs(idx+1, length, next_known, confused, lieNum);
        }

        // 가능할경우 거짓을 말한 경우를 탐색 - 거짓말을 들은 사람을 업데이트하고 다음 dfs 진행
        if (canLie) {
            System.out.println("able to tell lie");
            int[] next_confused = confused.clone();
            updateArr(party, next_confused);
            dfs(idx+1, length, isKnown, next_confused, lieNum+1); // 거짓말하면 lieNum 1 증가
        }

        // 진실, 거짓말 다 안되면 애초에 안되는 경우. 끝까지 못 가고 종료.
    }

    // 거짓말을 듣거나 진실을 들은 사람을 업데이트
    static void updateArr(List<Integer> party, int[] arr) {
        for (Integer p : party)
            arr[p] = 1;
    }

    // 진실 혹은 거짓말 판단 - 파티 내에 진실을 아는 사람이 있으면 거짓말 불가, 거짓말 들은 사람이 있으면 진실도 불가
    static boolean checkToTell(List<Integer> party, int[] arr) {
        for (Integer p : party) {
            if (arr[p] == 1)
                return false;
        }
        return true;
    }

}
