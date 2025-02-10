package prob_202502.week2.jumpdistance_6209;

import java.io.*;
import java.util.*;

public class Main2 {

    public static int D, N, M;

    public static int[] edges; // 이분 탐색을 위한 배열

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        D = Integer.parseInt(tkn.nextToken()); // 전체 거리 (1 ~ 10억)
        N = Integer.parseInt(tkn.nextToken()); // 섬 개수 (1 ~ 5만)
        M = Integer.parseInt(tkn.nextToken()); // 제거할 섬 개수 (0 ~ n)

        // 각 간선을 오름차순 정렬한 뒤, 배열에 저장한다.
        edges = new int[N+2];
        int tmp;
        // 거리 입력받기
        for(int i=1; i<=N; i++) {
            tmp = Integer.parseInt(reader.readLine());
            edges[i] = tmp;
        }

        // 양끝 노드 처리
        edges[0] = 0;
        edges[N+1] = D;

        Arrays.sort(edges);

        int start = 0;
        int end = D;

        // 처음에는 기준 값을 중간 값으로 설정하고, 해당 값이 최솟값일 때 무시할 수 있는 노드들을 체크한다.
        // 무시할 수 있는 노드가 M보다 많으면 기준 값을 줄여야 한다. (기준을 낮춘다 -> 건너는 돌 섬의 개수를 늘린다.)
        // 무시할 수 있는 노드가 M보다 적으면 기준 값을 높여야 한다. (기준을 높인다 -> 건너는 돌 섬의 개수를 줄인다.)

        int res = binarySearch();
        System.out.println("res = " + res);

    }

    public static int binarySearch() {
        int start = 0;
        int end = D;
        int result = D; // 노드가 하나도 없을 경우를 대비해 D로 초기화

        int mid;
        while (start <= end) {

            // mid를 한번에 이동할 거리의 최솟값 중 최댓값이라고 가정한다.
            mid = (start + end) / 2;
            int count = 0; // 제거할 노드를 카운트할 변수
            int cur = 0; // 거리가 mid보다 커서 제거하지 않고 방문할 노드의 값으로 업데이트.
            // 모든 노드들에 대해 탐색을 돌린다.
            for(int i=1; i<=N+1; i++) {
                // 두 노드 간 거리가 mid값보다 작을 경우 제거한다.
                if ((edges[i]-edges[cur]) < mid) {
                    count++;
                }
                // mid값보다 크거나 같을 경우 해당 노드를 방문해야 함. cur를 해당 노드로 이동시킴.
                else {
                    cur = i;
                }
            }
            // 이걸 한바퀴 돌리면 mid 값을 기준으로 제거할 노드의 개수를 카운트할 수 있다.
            // for문으로 순차 방문하는 이유는 앞에서 거리순으로 노드들을 정렬했기 때문.

            // 제거한 노드의 개수가 M보다 클 경우 -> mid 값을 더 작게 조정해야 한다. end를 mid-1로 이동시킨다.
            if (count > M) {
                end = mid-1;
            }
            // 제거한 노드의 개수가 M보다 작거나 같을 경우 -> mid 값을 더 크게 조정해야 한다.
            // -> 최솟값 중 최대가 아니라는 의미다. 노드를 더 제거해야 한다는 것.
            else {
                result = mid;
                start = mid+1;
            }

        }

        return result;
    }

}
