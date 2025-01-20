package prob_2024.goodvilege_1949;

import java.util.*;
import java.io.*;

public class Main_2 {

    public static int N; // 마을 개수
    public static int[] people; // 마을 인구수

    public static Node[] nodes; // 트리 구성에 사용할 노드
    public static int[] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(reader.readLine());

        // dp 테이블 초기화
        dp = new int[N+1];
        Arrays.fill(dp, 0);

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
            parent = Math.min(src, dst);
            child = Math.max(src, dst);
            nodes[parent].children.add(child);
            nodes[child].parent = parent;
        }

        for(int i=1; i<=N; i++) {
            System.out.println("nodes[i] = " + nodes[i]);
        }

        // 각 노드가 우수마을이다 -> 그 하위 트리들의 우수마을 여부가 결정된다.
        // 어떤 노드가 우수마을이다 -> 그 하위 노드들은 우수마을이 아니다.
        // 해당 노드가 우수마을일 때 우수마을의 전체 인구수를 dp 테이블에 저장.
        for(int i=N; i>0; i--) {
            dp[i] = calc(i);
        }

        for(int i=1; i<=N; i++) {
            System.out.printf("dp[%d] = %d\n", i, dp[i]);
        }

        System.out.println(dp[0]);

    }

    // dp 테이블에 넣을 값을 계산 -> 자신이 우수마을일 때, 우수마을이 아닐 때 중 더 큰 값으로 저장.
    public static int calc(int n) {
        Node curNode = nodes[n];

        // 1. 자신의 인구수 + 2세대 뒤 마을들의 dp값 (자신이 우수마을일 경우)
        int sum1 = people[n];

        // 아래 과정은 자식이 없으면 수행되지 않음
        for(int c: curNode.children) {
            Node child = nodes[c]; // 자식 노드

            // 자식 노드의 자식들의 인구수를 더함
            for(int gc: child.children) {
                sum1 += dp[gc];
            }

        }

        // 2. 1세대 뒤 마을들의 dp값 (자신이 우수마을이 아닐 경우)
        int sum2 = 0;

        // 아래 과정은 자식이 없으면 수행되지 않음
        for(int c: curNode.children) {
            sum2 += dp[c];
        }

        int total = Math.max(sum1, sum2); // 1번 케이스, 2번 케이스 중 더 큰걸 리턴

        return total;
    }

    static class Node {
        public int number;
        public int parent;
        public List<Integer> children = new ArrayList<>();

        public Node(int number) {
            this.number = number;
            this.parent = 0; // 부모를 처음에는 0으로 초기화
        }

        @Override
        public String toString() {
            return String.format("Node[%d], parent = %d, children = " + children, number, parent);
        }

    }

}