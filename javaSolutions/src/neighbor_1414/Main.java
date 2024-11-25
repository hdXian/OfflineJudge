package neighbor_1414;

import java.util.*;
import java.io.*;

public class Main {

    public static int[][] graph; // 간선 비용 때문에 인접 행렬도 같이 써야 함
    public static int N;

    public static ArrayList<ArrayList<Integer>> closeList; // 인접 리스트

    public static final String ALPHA = "0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(reader.readLine());

        // 그래프를 그리고
        graph = new int[N][N];
        for(int i=0; i<N; i++) {
            Arrays.fill(graph[i], Integer.MAX_VALUE);
        }

        closeList = new ArrayList<>();
        for (int i=0; i<N; i++) {
            closeList.add(new ArrayList<>());
        }

        String line;
        int val;
        for(int i=0; i<N; i++) {
            line = reader.readLine();
            // 노드 i의 인접 리스트
            ArrayList<Integer> list = closeList.get(i);

            for(int j=0; j<N; j++) {
                char ch = line.charAt(j);
                val = ALPHA.indexOf(ch);
                if (ch != '0') {
                    graph[i][j] = val; // 일단 저장

                    // 인접 리스트에 추가
                    if (i != j) {
                        list.add(j);

                        // 반대편에도 추가
                        ArrayList<Integer> dst = closeList.get(j);
                        if (!dst.contains(i)) {
                            dst.add(i);
                        }

                    }
//                    graph[i][j] = Math.min(val, graph[j][i]); // 더 작은 놈으로 저장
                }

            }

        }

        printGraph();

        boolean check = bfs(graph);
        System.out.println("check = " + check);

        // 모든 노드를 방문하는 최소 거리를 구한 다음에
        // 그럼 결국 모든 노드를 방문하면서, 동시에 각 노드를 방문하는 최단 거리를 구해야 함
        // 이게 최소 스패닝 트리임. MST를 공부해야 함.
        // 완탐 경우의 수를 다 찾은 다음 그 중 최소 거리를 구하나?
        // 결국 최단 경로? 다익스트라?

        // 모든 노드를 방문하는게 불가하면 -1 리턴 -> bfs를 쓰나?

        // 모든 노드를 방문 가능하다면, 그 최소 거리를 제외한 나머지 간선 비용을 모두 합쳐서 리턴

        // bfs로 탐색 안돌려도 될 것 같다.
        // 최소 신장 트리 만들어서 집합에 속해있지 않은 노드가 존재하면 -1 리턴하면 될듯.

        // 1. 간선을 하나로 줄이기
        // 자기 자신에게 연결된 랜선 뽑아서 result에 더해놓기
        // 중복된 간선의 경우 작은 거 하나만 남기고 나머지 하나는 result에 더해놓기

        // 2. 남은 그래프로 최소 신장 트리 만들기

        // 3. (남은 그래프의 전체 간선 비용) - (최소 신장 트리 비용) 결과를 result에 더하기

        // 4. result 출력하기

    }

    // bfs를 이용해 신장 트리 여부를 판별
    private static boolean bfs(int[][] graph) {

        boolean[] visited = new boolean[N];
        Arrays.fill(visited, false);

        Queue<Integer> q = new LinkedList<>();

        // 시작은 0
        q.add(0);
        visited[0] = true;

        int cur;
        while(!q.isEmpty()) {
            cur = q.poll();
            System.out.println("cur = " + cur);

            // cur의 인접 리스트에서 방문하지 않은 노드들을 가져와 큐에 추가
            for (int node: closeList.get(cur)) {
                if (!visited[node]) {
                    q.add(node);
                    visited[node] = true;
                }
            }

        }

        for(boolean b: visited) {
            // 하나라도 방문한 노드가 없으면 false (전체 노드 방문 불가)
            if (!b)
                return false;
        }

        // visited의 모든 요소가 true라면 전체 방문 가능.
        return true;
    }

    private static void printGraph() {
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                System.out.printf("%2d ", graph[i][j]);
            }
            System.out.println();
        }
    }

}
