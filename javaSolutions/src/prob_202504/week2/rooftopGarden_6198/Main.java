package prob_202504.week2.rooftopGarden_6198;

import java.util.*;
import java.io.*;
import java.math.*;

// 두번째 풀이 - 스택 활용
public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[] hs;
    static Stack<Integer> stack = new Stack<>();

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 빌딩 수. 1 ~ 8만
        hs = new int[N+1];

        // 빌딩 높이 입력. 1 ~ 10억
        for(int i=1; i<=N; i++) hs[i] = Integer.parseInt(reader.readLine());
    }

    static void calc() throws Exception {
        // 빌딩들을 순회한다.

        // 스택이 비었으면 해당 빌딩 높이를 push한다.

        // 스택이 비지 않았으면
        // 현재 빌딩 높이보다 낮은 높이들을 스택에서 pop한다.
        // 스택에 남은 건물들의 개수를 카운트해서 더한다.
        // 현재 빌딩 높이를 push한다.

        long result = 0;
        for (int h: hs) {
            // 현재 건물을 보지 못하는 건물들을 모두 배제한다. (pop한다. 얘넨 현재 건물에 가로막혀서 앞으로 나오는 건물도 볼 수 없다.)
            while (!stack.isEmpty() && stack.peek() <= h)
                stack.pop();

            // 스택에 남은 건물들은 현재 건물을 볼 수 있는 건물들이다. 카운트에 합산한다.
            result += stack.size();

            // 현재 건물을 스택에 push한다.
            stack.push(h);
        }

        System.out.println("result = " + result);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
