package prob_202501.week4.leagyeoflegeno_23059;

import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static Map<String, Integer> map = new HashMap<>();
    public static Map<Integer, String> reverseMap = new HashMap<>();

    public static List<List<Integer>> edges = new ArrayList<>();
    public static int[] degree;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 1 ~ 20만
        degree = new int[N*2+1];
        Arrays.fill(degree, 0);

        StringTokenizer tkn;
        String A, B;
        int seq = 0; // 사람 번호는 1부터 시작

        // 선후관계 입력받기
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());

            A = tkn.nextToken();
            B = tkn.nextToken();

            // 처음 등장한 아이템이면 map에 추가, 새로운 edges (인접 노드 리스트) 추가
            if (!map.containsKey(A)) {
                map.put(A, ++seq);
                edges.add(new ArrayList<>()); // 1번 사람의 인접 노드들 List -> edges.get(0);
            }

            if (!map.containsKey(B)) {
                map.put(B, ++seq);
                edges.add(new ArrayList<>());
            }

            // A를 먼저 구매해야 B를 구매할 수 있다. -> A의 인접 노드에 B 추가, B의 진입 차수 1 증가
            Integer na = map.get(A);
            Integer nb = map.get(B);

            edges.get(na-1).add(nb); // A의 인접 노드에 B 추가
            degree[nb]++; // B의 진입 차수 1 증가
        }

        // 번호를 key로 가진 반대 Map 생성
        for(String key: map.keySet()) {
            Integer val = map.get(key);
            reverseMap.put(val, key);
        }
        System.out.println("reverseMap = " + reverseMap);

        printDegree();
        topologySort();

    }

    // map -> Key: String, Value: Integer
    // reverseMap -> Key: Integer, Value: String
    // 위상 정렬
    public static void topologySort() {
        StringBuilder sb = new StringBuilder();

        // 진입 차수가 0인 노드들끼리의 순서는 사전 순이다.

        // 진입 차수가 0인 노드들을 처음에 받는다.
        Queue<String> pq = new PriorityQueue<>();
        for (Integer value : map.values()) {
            System.out.println("value = " + value);
            System.out.println("degree[value] = " + degree[value]);
            if (degree[value] == 0)
                pq.add(reverseMap.get(value));
        }

        Queue<String> q = new LinkedList<>(pq); // pq로 정렬한 노드들로 큐를 구성
        pq.clear();

        int count = 0;
        int length;
        while (!q.isEmpty()) {
            System.out.println("q = " + q);
            length = q.size();

            // 진입 차수가 0인 요소를 꺼낸다.
            for(int i=0; i<length; i++) {
                String item = q.poll();
                System.out.println("item = " + item);
                count++;
                sb.append(item).append('\n');

                Integer num = map.get(item); // 해당 아이템의 번호 가져오기
                List<Integer> nodes = edges.get(num - 1); // 해당 아이템 번호의 인접 노드들 번호 가져오기

                // 해당 요소와 연결된 노드들의 진입 차수를 1 감소시킨다.
                for (Integer n: nodes) {
                    degree[n]--;
                    if (degree[n] == 0) {
                        pq.add(reverseMap.get(n)); // key가 번호인 map에서 아이템 이름을 가져온다.
                    }
                }
            }

            System.out.println("pq = " + pq);
            q.addAll(pq); // pq로 정렬해놓은 노드들을 큐의 뒤에 삽입한다.
            pq.clear(); // pq를 비운다.
        }

        // 꺼내진 노드 개수가 전체 개수보다 적다 -> 모든 아이템을 살 수 없다 (위상정렬이 불가능하다)
        System.out.println("count = " + count);
        System.out.println("reverseMap.size() = " + reverseMap.size());
        if (count != reverseMap.size()) {
            System.out.println(-1);
        }
        else {
            sb.deleteCharAt(sb.length()-1); // 마지막 개행문자 제거
            System.out.println("sb = " + sb);
        }

    }

    public static void printDegree() {
        for(int i=1; i<=2*N; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for(int i=1; i<=2*N; i++) {
            System.out.print(degree[i] + " ");
        }
        System.out.println();
    }

}
