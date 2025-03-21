package prob_2024.goodvilege_1949;

import java.util.*;
import java.io.*;

public class Main {

    public static int N; // 마을 개수
    public static int[] people; // 마을 인구수

    public static Node[] nodes; // 트리 구성에 사용할 노드
    public static int[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine());

        // dp 테이블 초기화
        dp = new int[N+1][2];
        for(int i=0; i<2; i++) {
            Arrays.fill(dp[i], 0);
        }

        // 노드 배열 초기화
        nodes = new Node[N+1];
        for(int i=1; i<=N; i++) {
            nodes[i] = new Node(i);
        }

        // 각 마을의 인구수 입력받기
        people = new int[N+1];
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<=N; i++) {
            people[i] = Integer.parseInt(tkn.nextToken());
        }

        // nodes를 통해 트리 구성하기
        int src, dst;
        int parent, child;
        for(int i=0; i<N-1; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            // src -> dst 방향으로 트리 구성 (오름차순)
            nodes[src].children.add(dst);
            nodes[dst].children.add(src);
        }

        for(int i=1; i<=N; i++) {
            System.out.println("nodes[i] = " + nodes[i]);
        }

        // 각 노드가 우수마을이다 -> 그 하위 트리들의 우수마을 여부가 결정된다...
        // 어떤 노드가 우수마을이다 -> 그 하위 노드들은 우수마을이 아니다.
        // 해당 노드가 우수마을일 때 우수마을의 인구수를 dp 테이블에 저장.
        calc(1, 0);

//        System.out.println(dp[1][0]);
//        System.out.println(dp[1][1]);
        System.out.println(Math.max(dp[1][0], dp[1][1]));
    }

    // dp 테이블에 넣을 값을 계산 -> 자신이 우수마을일 때, 우수마을이 아닐 때 중 더 큰 값으로 저장.
    public static void calc(int n, int parent) {
        Node curNode = nodes[n];

        for(int child: curNode.children) {

            if (child != parent) {
                calc(child, n);
                dp[n][0] += Math.max(dp[child][0], dp[child][1]); // 현재 노드가 우수마을이 아닐 경우 -> (다른 마을이 우수마을인 경우, 아닌경우 중 큰 것을 추가)
                dp[n][1] += dp[child][0]; // 현재 노드가 우수마을인 경우 -> 다른 마을들이 우수마을이 아닌 경우를 추가
            }

        }
        dp[n][1] += people[n]; // -> 현재 노드가 우수마을인 경우에 현재 노드의 인구수를 추가
    }

    static class Node {
        public int number;
        public List<Integer> children = new ArrayList<>();

        public Node(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return String.format("Node[%d], children = " + children, number);
        }
    }

}