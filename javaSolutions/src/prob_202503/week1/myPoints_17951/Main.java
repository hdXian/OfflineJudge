package prob_202503.week1.myPoints_17951;

import java.util.*;
import java.io.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, K;
    public static int[] papers;

    public static void main(String[] args) throws Exception {
        init();

        int min_val = 21;
        int total = 0;

        for(int i=0; i<N; i++) {
            if (papers[i] < min_val)
                min_val = papers[i];
            total += papers[i];
        }

        int result = calc(min_val, total);
        System.out.println("result = " + result);
    }

    // 기준 값을 이분탐색으로 찾는다.
    // 각 그룹을 합친 문제 수의 최솟값을 최대가 되도록 하려고 한다.
    // 그럼 이진탐색을 할 때의 start, end값의 기준은 무엇이 되어야 하는가?
    // 기준값 중 최소가 될 수 있는 start는 -> 하나의 그룹이 가질 수 있는 값 중 최소는 문제들 중 최소.
    // 기준값 중 최대가 될 수 있는 end는 -> 하나의 그룹이 가질 수 있는 값 중 최대는 문제들의 전체 합.
    public static int calc(int start, int end) {
        int left = start;
        int right = end;

        int result = end;
        int mid;
        while (left <= right) {
            System.out.println("left = " + left);
            System.out.println("right = " + right);

            mid = (left + right) / 2;
            System.out.println("mid = " + mid);

            int group_count = makeGroup(mid); // mid값 기준으로 그룹을 나눈다.
            System.out.println("group_count = " + group_count);
            System.out.println();

            // 만들어진 그룹이 K보다 적거나 같을 경우 -> 그룹을 늘려야 한다 -> 기준값을 줄여야 한다.
            if (group_count < K) {
                System.out.println("그룹이 모자랍니다. 기준값을 축소합니다.");
                right = mid-1;
            }
            // 만들어진 그룹이 K보다 많을 경우 -> 그룹을 줄여야 한다 -> 기준값을 늘려야 한다.
            else {
                System.out.println("그룹이 많거나 같습니다. 기준값을 늘려봅니다.");
                result = mid;
                left = mid+1;
            }

        }

        System.out.println("이분 탐색을 종료합니다. left = " + left + ", right = " + right);
        System.out.println("result = " + result);

        return result;
    }

    // 맞힌 문제들의 합 중 최솟값을 val이라 가정한다.
    // 그럼 val은 최솟값이란 의미. 이걸 기준으로 그룹을 생성하고, 조건을 만족하는지에 따라 val값을 조정해나가야 한다.
    public static int makeGroup(int val) {
        int group_count = 0;
        int sum = 0;

        // 그룹 나누기
        for(int i=0; i<N; i++) {

            // 기준값을 처음 넘을 때까지 그룹에 추가한다.
            if (sum >= val) {
                System.out.println("sum이 val을 넘어섰음: sum = " + sum);
                group_count++;
                sum = 0;
            }
            sum += papers[i];
            System.out.println("그룹에 시험지를 추가하는중: paper = " + papers[i] + ", sum = " + sum);

        }

        // 남은 문제들을 모아 마지막 그룹으로 지정한다.
        if (sum >= val)
            group_count++;

        return group_count;
    }

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 문제 수
        K = Integer.parseInt(tkn.nextToken()); // 그룹 수
        papers = new int[N];

        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            papers[i] = Integer.parseInt(tkn.nextToken());
        }

    }

}
