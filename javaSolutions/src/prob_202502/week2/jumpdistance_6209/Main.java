package prob_202502.week2.jumpdistance_6209;

import java.util.*;
import java.io.*;

public class Main {

    public static int D, N, M;
    public static Queue<Integer> distances = new PriorityQueue<>(); // 입력받는 거리들

    static class Node implements Comparable<Node> {
        int number;
        int left, right;
        int both;

        public Node(int number, int left, int right) {
            this.number = number;
            this.left = left;
            this.right = right;
            this.both = left + right;
        }

        // 더 작은 간선을 가진 노드로 비교
        @Override
        public int compareTo(Node n) {
            int min1 = Math.min(this.left, this.right);
            int min2 = Math.min(n.left, n.right);
            if (min1 != min2) {
                return Integer.compare(Math.min(this.left, this.right), Math.min(n.left, n.right));
            }
            else {
                return Integer.compare(this.both, n.both);
            }
        }

        @Override
        public String toString() {
            return String.format("Node[number=%d, left=%d, right=%d]\n", number, left, right);
        }

    }

    public static List<Node> nodes = new ArrayList<>();
    public static int result;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        D = Integer.parseInt(tkn.nextToken()); // 전체 거리 (1 ~ 10억)
        N = Integer.parseInt(tkn.nextToken()); // 섬 개수 (1 ~ 5만)
        M = Integer.parseInt(tkn.nextToken()); // 제거할 섬 개수 (0 ~ n)

        // 거리들 입력받기
        int tmp;
        for (int i=0; i<N; i++) {
            tmp = Integer.parseInt(reader.readLine());
            distances.add(tmp);
        }

        // 거리 계산해서 노드 추가하기
        int cost;
        int idx = 0; // 추가할 노드의 인덱스
        int total_distance = 0;
        int pre_distance = 0;
        while (!distances.isEmpty()) {
            int cur_distance = distances.poll(); // 현재 거리

            // 계산된 간선은 새로 추가된 노드의 왼쪽 간선이자, 이전 노드의 오른쪽 간선임.
            cost = cur_distance - pre_distance; // 간선 비용 계산

            // 노드를 추가하고, 왼쪽 간선의 비용을 초기화
            nodes.add(new Node(idx, cost, 0));

            // 이전 노드의 오른쪽 간선 비용을 새로 추가된 간선으로 설정
            if (idx > 0) {
                nodes.get(idx-1).right = cost;
            }

            idx++;
            total_distance += cost;
            pre_distance = cur_distance;
        }

        // 만약 노드가 하나도 없다면 D를 출력하고 종료
        if (nodes.isEmpty()) {
            result = D;
            System.out.println("result = " + result);
            return;
        }

        // 마지막 노드의 오른쪽 비용을 추가해야 함.
        cost = D - total_distance;
        nodes.get(idx-1).right = cost;

        // 노드 리스트 오름차순 정렬
        Collections.sort(nodes);
        System.out.println("nodes = " + nodes);

        // 이제부터 노드를 m개만큼 제거하면 됨.
        // 리스트에서 간선이 가장 작은 놈을 찾아서 양쪽 간선 업데이트하고 제거.
        Node node;
        for (int i=0; i<M; i++) {
            node = nodes.get(0);
            System.out.println("node = " + node);
            int num = node.number;
            int left = node.left;
            int right = node.right;

            for(Node n: nodes) {
                // 왼쪽에 있는 노드의 right를 추가
                if (n.number == (num-1)) {
                    n.right += right;
                }
                // 오른쪽에 있는 노드의 left를 추가
                if (n.number == (num+1)) {
                    n.left += left;
                }
            }
            nodes.remove(0);
            System.out.println("nodes after remove = " + nodes);

            Collections.sort(nodes);
        }

        Node res = nodes.get(0);
        System.out.println("Math.min(res.left, res.right) = " + Math.min(res.left, res.right));

        // m개만큼 섬 제거하기
        // 간선 제거할 때마다 간선 정보 업데이트 해야함. -> 결국 노드랑 간선이 이어진 정보를 유지하고 있어야 함.

        // 간선의 순서를 반드시 가지고 있어야 함.
        // 1. 간선을 제거한다.
        // 2. 양쪽의 간선 정보를 가져온다.
        // 3. 양쪽의 간선 정보를 업데이트한다.
    }

}
