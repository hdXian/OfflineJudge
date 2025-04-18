package prob_202503.week1.gameDev_1516;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main2 {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N;
    public static int[] buildTimes;
    public static int[] degree;
    public static List<Integer>[] neighbors;

    public static void main(String[] args) throws Exception {
        init();
        calcDegree();
        topologySort();
    }

    // 해당 건물 번호에 대한 위상정렬 수행
    public static void topologySort() {
        int[] table = new int[N+1];
        for (int i=1; i<=N; i++)
            table[i] = buildTimes[i];

        Queue<Integer> q = new LinkedList<>();

        // 진입차수가 0인 건물들을 큐에 추가
        for (int i=1; i<=N; i++) {
            if (degree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int node = q.poll();

            // 이웃 노드들의 진입차수를 감소시키고, 0 되면 다음 대기열에 추가
            for(int neighbor: neighbors[node]) {
                degree[neighbor]--; // 진입차수 감소

                // 여기서 neighbor를 방문한다는 것은 -> 해당 node를 거쳐 neighbor 노드에 도달할 수 있다는 것
                if (table[neighbor] < table[node] + buildTimes[neighbor])
                    table[neighbor] = table[node] + buildTimes[neighbor];

                if (degree[neighbor] == 0)
                    q.add(neighbor);
            }

        }

        StringBuilder sb = new StringBuilder();
        for(int i=1; i<=N; i++)
            sb.append(table[i]).append("\n");

        System.out.println(sb);

    }

    // 진입차수 계산
    public static void calcDegree() throws Exception {
        StringTokenizer tkn;
        int time;
        int building_num;
        for (int i=1; i<=N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            time = Integer.parseInt(tkn.nextToken());
            buildTimes[i] = time;

            while (true) {
                building_num = Integer.parseInt(tkn.nextToken()); // 먼저 지어야하는 건물 번호 -> 얘네들이 내림차순으로 제공되나?
                if (building_num == -1) break;

                neighbors[building_num].add(i);
                degree[i]++;
            }

        }

    }

    // 정적 변수 및 배열 초기화
    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 전체 건물 개수
        buildTimes = new int[N+1]; // 인덱스 1번부터 쓰기
        degree = new int[N+1];
        Arrays.fill(buildTimes, 0);
        Arrays.fill(degree, 0);

        neighbors = new List[N+1];
        for(int i=1; i<=N; i++)
            neighbors[i] = new ArrayList<>();
    }

}
