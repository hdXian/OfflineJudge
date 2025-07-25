package prob_202505.week4.internet_1800;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N, P, K;
    static int[] distances; // 거리 합의 최댓값이 100억이라 long으로 선언

    static class Node implements Comparable<Node> {
        int number;
        int cost;
        public Node(int number, int cost) {
            this.number = number;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return Integer.compare(this.cost, n.cost);
        }

    }

    // Node 클래스를 두가지 방식으로 썼음
    // 1. (노드 번호, 실제 간선 비용) -> 이거는 이웃 노드들 확인할 때만 씀
    // 2. (노드 번호, 0 또는 1) -> 기준값보다 싸거나 같으면 비용 0, 아니면 비용 1 -> 이거는 다익스트라에서 돌리는 pq에 들어감
    static Map<Integer, List<Node>> neighbors = new HashMap<>(); // 노드 번호별 이웃 노드들을 저장할 Map

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 사람 수. 번호는 1 ~ N.
        P = Integer.parseInt(tkn.nextToken()); // 케이블 수. 1 ~ 1만
        K = Integer.parseInt(tkn.nextToken()); // 무료로 해줄 케이블 수. 0 ~ N

        distances = new int[N+1]; // 인덱스 1부터 쓰기

        for(int i=1; i<=N; i++) neighbors.put(i, new ArrayList<>()); // 이웃들을 저장할 Map 초기화

        int a, b, x;
        for(int i=0; i<P; i++) {
            tkn = new StringTokenizer(reader.readLine());
            a = Integer.parseInt(tkn.nextToken());
            b = Integer.parseInt(tkn.nextToken());
            x = Integer.parseInt(tkn.nextToken());

            // a, b번 노드 서로에게 이웃을 추가
            neighbors.get(a).add(new Node(b, x));
            neighbors.get(b).add(new Node(a, x));
        }

    }

    static void printDistances() {
        System.out.println("print distances");
        for(int i=1; i<=N; i++) {
            System.out.print(distances[i] + " ");
        }
        System.out.println();
    }

    static boolean dijkstra(int min_cost) {

        // 어차피 연결에 사용된 간선 중 가장 비싼 간선을 기준으로 가격이 책정되므로,
        // min_cost 이하의 간선들만 선택하면 지출 비용은 min_cost로 고정된다.
        // 단, min_cost보다 큰 간선들은 K개 이하까지 고를 수 있다.

        // K+1번째로 비싼 간선 선택하기 -> 지루하고 현학적임. 반례가 존재하면 내 로직이 무너짐.
        // K개 간선을 무료로 선택했다는 것을 전제하고, min_cost를 기준으로 간선을 선택하면서 min_cost를 조금씩 줄여나간다.
        // 그렇게 선택된 간선들 중 K+1번째로 비용이 비싼 간선이 실제 지불해야하는 비용이다.
        // 조건을 만족하는 최소 min_cost를 찾고, 그 때 최단경로에 선택된 간선들을 찾아 K+1번째로 비싼 간선의 비용을 리턴한다.

        // 기준값보다 비싸? 그럼 죽어 -> 팩트임. 기억에 오래 남음
        // min_cost보다 작거나 같은 간선은 가중치를 0으로 잡는다.
        // min_cost보다 큰 간선은 가중치를 1로 잡는다.
        // 그 상태로 다익스트라를 돌리면, 경로 자체는 최단거리가 아닐지라도 어쨌든 최대한 min_cost보다 비용이 적은 간선들로만 최단 경로를 구성하게 될 것이다.

        // 0. 최단 비용 배열을 초기화한다.
        Arrays.fill(distances, 10001); // 1만 1. 간선은 최대 1만개.

        // 1. 시작 노드의 비용을 0으로 설정하고, 큐에 집어넣는다.
        PriorityQueue<Node> pq = new PriorityQueue<>();
        distances[1] = 0;
        pq.add(new Node(1, 0));

        // 2. 현재 노드의 인접 노드들에 대해 최소 비용을 업데이트한다.
        // 2-1. 이 때, 최소 비용이 업데이트된 노드들만 큐에 추가한다.
        // 3. 큐가 빌 때까지 반복한다.
        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            // 인접 노드들에 대해
            for(Node next: neighbors.get(cur.number)) {
                int nc = next.cost > min_cost ? 1 : 0;
                int new_cost = distances[cur.number] + nc;

                if (new_cost < distances[next.number]) {
                    distances[next.number] = new_cost;
                    pq.add(new Node(next.number, nc));
                }

            }

//            printDistances();
//            System.out.println();
        }

//        System.out.println("distances[N] = " + distances[N]);
        return distances[N] <= K;
    }

    static int calc() {
        int start = 0;
        int end = 1000000; // 간선 비용은 최대 100만

        int result = -1;
        while(start <= end) {
            int mid = (start + end)/ 2;

            boolean isAble = dijkstra(mid);
            // 가능하다 -> 최소 비용을 더 줄여도 조건을 만족할 수도 있다.
            if (isAble) {
                end = mid-1;
                result = mid;
            }
            // 불가능하다 -> 최소 비용이 모자라다. 더 늘린다.
            else {
                start = mid+1;
            }

        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        init();
        int result = calc();
        System.out.println(result);
    }

}
