package prob_202503.week1.gameDev_1516;

import java.sql.Array;
import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N;
    public static int[] buildTimes;
    public static int[] degree;
    public static int[] table;
    public static List<Integer>[] neighbors;

    public static void main(String[] args) throws Exception {
        init();
        calcDegree();
        printDeg();
        topologySort();

//        StringBuilder sb = new StringBuilder();
//        for(int i=1; i<=N; i++) {
//            System.out.println("i = " + i);
//            int res = topologySort(i);
//            System.out.println("res = " + res);
//            System.out.println();
//            sb.append(res).append("\n");
//        }
//        System.out.println("sb = " + sb);

        // 건물들 정보를 입력받으면서 각 건물이 지어지는 시간과, 위상정렬에 쓸 진입차수를 계산한다.
        // 마지막에 위상정렬을 돌린 다음, 1~N번까지의 각 건물이 지어지는데 걸리는 시간을 계산해 출력한다.
        // 진입차수를 저장해놓고, 위상정렬 돌린 다음에, 같은 진입차수에 대해서는 걸리는 시간이 더 짧은 놈으로 계산해서 N까지 도달하는 시간을 구하면 되나?
    }

    // 해당 건물 번호에 대한 위상정렬 수행
    public static void topologySort() {
        int[] local_degree = degree.clone();
        Queue<Integer> q = new LinkedList<>();

        // 진입차수가 0인 건물들을 큐에 추가
        for (int i=1; i<=N; i++) {
            if (local_degree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            System.out.println("q = " + q);
            int node = q.poll();
            System.out.println("node = " + node);

            // 이웃 노드들의 진입차수를 감소시키고, 0 되면 다음 대기열에 추가
            System.out.println("neighbors = " + neighbors[node]);
            for(int neighbor: neighbors[node]) {
                local_degree[neighbor]--; // 진입차수 감소

                table[neighbor] = Math.max(table[neighbor], table[node] + buildTimes[neighbor]);
                System.out.println("table[" + neighbor + "] = " + table[neighbor]);

                if (local_degree[neighbor] == 0)
                    q.add(neighbor);
            }

        }

        StringBuilder sb = new StringBuilder();
        for (int i=1; i<=N; i++) {
            sb.append(table[i]).append("\n");
        }

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
            table[i] = time;

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
        table = new int[N+1];
        Arrays.fill(buildTimes, 0);
        Arrays.fill(degree, 0);
        Arrays.fill(table, 0);

        neighbors = new List[N+1];
        for(int i=1; i<=N; i++)
            neighbors[i] = new ArrayList<>();
    }

    public static void printDeg() {
        System.out.println("printDeg()");
        for(int i=1; i<=N; i++) {
            System.out.print(degree[i] + " ");
        }
        System.out.println();
    }

}
