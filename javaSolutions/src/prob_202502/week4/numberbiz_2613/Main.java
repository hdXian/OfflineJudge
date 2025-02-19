package prob_202502.week4.numberbiz_2613;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, M;
    public static int[] balls;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 구슬 개수 (1 ~ 300)
        M = Integer.parseInt(tkn.nextToken()); // 그룹 수 (1 ~ N)

        balls = new int[N];

        // 꿰어진 구슬 입력받기
        int tmp;
        int total = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            tmp = Integer.parseInt(tkn.nextToken());
            balls[i] = tmp;
            total += tmp;
        }

        binarySearch(0, total);
    }

    // 첫 요소부터 차례대로 붙여서 기준점을 넘지 않을 때까지 이어붙이기
    public static void binarySearch(int start, int end) {
        int left = start;
        int right = end;

        int result = end;

        // 기준점 -> 각 그룹의 합의 최댓값의 기준
        int mid;
        List<Set<Integer>> groups = new ArrayList<>();

        while (left <= right) {
            mid = (left + right) / 2; // 기준점
            groups = makeGroup(mid);

            int size = groups.size();

            // 만들어진 그룹이 M보다 크다 -> 기준값을 늘려야 한다.
            if (size > M) {
                left = mid+1;
            }
            // 만들어진 그룹이 M보다 적다 -> 기준값을 줄여야 한다.
            else if (size < M) {
                right = mid-1;
            }
            // 만들어진 그룹이 M과 같다 -> 기준값을 줄여도 조건을 만족할 수도 있다.
            else {
                result = mid;
                right = mid-1;
            }

        }

        System.out.println("result = " + result);

        StringBuilder sb = new StringBuilder();
        sb.append(result).append("\n");

        for (Set<Integer> e: groups) {
            sb.append(e.size()).append(" ");
        }

        sb.deleteCharAt(sb.length()-1);

        System.out.println("sb = " + sb);
    }

    // 그룹 만들기 (val 값을 기준으로)
    // 이 함수는 그룹을 짓고, 조건을 만족하면
    public static List<Set<Integer>> makeGroup(int val) {
        System.out.println("val = " + val);

        List<Set<Integer>> groups = new ArrayList<>();

        // 모든 구슬을 돌며 그룹을 만든다.
        Set<Integer> group = new HashSet<>();
        int group_sum = 0;
        for (int i=0; i<N; i++) {
            System.out.println("i = " + i);

            if (group_sum + balls[i] <= val) {
                group_sum += balls[i];
                System.out.println("group_sum = " + group_sum);
                group.add(balls[i]);
            }
            else {
                groups.add(group);
                group = new HashSet<>();

                group_sum = balls[i];
                group.add(balls[i]);
            }

        }

        if (group_sum <= val) {
            groups.add(group);
        }

        for(Set<Integer> s: groups) {
            System.out.println(s);
        }

        return groups;
    }

}
