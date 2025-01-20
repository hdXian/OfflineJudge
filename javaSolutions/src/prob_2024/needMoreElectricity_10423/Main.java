package prob_2024.needMoreElectricity_10423;

import java.io.*;
import java.util.*;

public class Main {

    public static int N, M, K;
    public static boolean[] isElec;
    public static List<Edge> edges = new ArrayList<>();

    public static int[] parents; // union-find 배열
    public static int result;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 도시 개수
        M = Integer.parseInt(tkn.nextToken()); // 케이블 개수
        K = Integer.parseInt(tkn.nextToken()); // 발전소 개수

        isElec = new boolean[N+1]; // 발전소 여부
        parents = new int[N+1]; // union-find 루트 노드 저장 배열

        // 발전소 도시들을 리스트에 추가
        tkn = new StringTokenizer(reader.readLine());
        while (tkn.hasMoreTokens()) {
            int num = Integer.parseInt(tkn.nextToken());
            isElec[num] = true;
        }

        // 부모 노드를 자기 자신으로 초기화
        for(int i=1; i<=N; i++) {
            parents[i] = i;
        }

        // 발전소들 미리 union
        for(int i=1; i<=N; i++) {
            if (isElec[i]) { // 첫 번째 발전소 찾기
                for (int j=i+1; j<=N; j++) {
                    if (isElec[j]) // 이후 발견하는 발전소들과 union
                        union(i, j);
                }
                break; // 하나 찾고 말꺼임
            }
        }

        // 테스트 코드
//        for(int i=1; i<=N; i++) {
//            System.out.println("parents[i] = " + parents[i]);
//        }

        // 그래프 그리기
        int u, v, w;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            u = Integer.parseInt(tkn.nextToken());
            v = Integer.parseInt(tkn.nextToken());
            w = Integer.parseInt(tkn.nextToken());

            edges.add(new Edge(u, v, w));
        }

        // 간선을 오름차순으로 정렬
        Collections.sort(edges);

        System.out.println("edges = " + edges);

        kruskal();

        System.out.println("result = " + result);

        // 1. MST를 만든다.
        // 2. 발전기 한 곳과만 연결되면 되므로 MST의 간선 중에서 필요없는 간선을 또 끊는다? -> 맞는 발상
        // 3. MST에서 필요 없는 간선을 어떻게 제거하는가? 크루스칼 알고리즘을 떠올려본다.
        // 4. MST를 만드는 크루스칼 알고리즘은, 간선을 오름차순으로 정렬해 가장 짧은 간선들만을 우선적으로 포함시키며 모든 원소가 하나의 집합에 포함되게끔 만드는 알고리즘이다.
        // 5. 이 문제를 MST의 변형으로 생각해서, 각 발전소를 하나의 공통된 노드로 취급한다. 어디든 연결만 되면 되니까.
        // 6. 즉, 발전소들의 루트 노드를 하나로 처음에 묶어놓은 다음, MST를 만드는 크루스칼 알고리즘을 돌린다.

        // 처음엔 MST를 만든 다음에 각 노드들에 대해 가장 가까운 발전소로 향하는 간선만을 남기려고 했는데, 너무 복잡해짐.

    }

    private static void kruskal() {

        // 종료조건 어떻게 하지..
        // 1. 간선 하나를 꺼낸다.
        // 2. 간선 양쪽의 노드의 루트가 같은지 확인한다.
        // 3. 같으면 추가하지 않고 넘어간다.
        // 4. 다르면 union 시키고 간선 비용에 포함한다.
        int total = 0;
        Edge e;
        while (check() && !edges.isEmpty()) {
            e = edges.get(0);
//            System.out.println("union " + e.src + ", " + e.dst);
            boolean isUnion = union(e.src, e.dst);
            if (isUnion)
                total += e.cost;
            edges.remove(0);
        }
        System.out.println("total = " + total);
        result = total;
    }

    // 종료 조건 체크하기 -> 루트 노드가 하나뿐이어야 함.
    private static boolean check() {
        int roots = 0;
        for(int i=1; i<=N; i++) {
            if (parents[i] == i)
                roots++;
        }
        return (roots > 1); // 루트 노드가 하나 이상일 경우 true
    }

    private static int findParent(int n) {
        if (parents[n] != n)
            parents[n] = findParent(parents[n]);
        return parents[n];
    }

    private static boolean union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);
        if (p1 != p2) {
            if (p1 < p2) {
                parents[p2] = p1;
                return true;
            }
            else {
                parents[p1] = p2;
                return true;
            }
        }
        return false;
    }

    static class Edge implements Comparable<Edge> {
        public int src;
        public int dst;
        public int cost;

        public Edge(int src, int dst, int cost) {
            this.src = src;
            this.dst = dst;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(this.cost, e.cost);
        }

        @Override
        public String toString() {
            return String.format("Edge[src=%d, dst=%d, cost=%d]", src, dst, cost);
        }

    }


}
