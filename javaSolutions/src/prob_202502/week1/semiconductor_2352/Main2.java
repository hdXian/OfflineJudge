package prob_202502.week1.semiconductor_2352;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main2 {

    public static int N;
    public static int[] ports;

    public static int[] table;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 포트 개수
        ports = new int[N];

        // ports 배열 입력받기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        int seq=0;
        int n;
        while (tkn.hasMoreTokens()) {
            n = Integer.parseInt(tkn.nextToken());
            ports[seq++] = n;
        }

        table = new int[N];
        Arrays.fill(table, 0);
        int tail = 0;

        // 첫 요소 초기화
        table[0] = ports[0];
        tail++;

        int cur;
        for (int i=1; i<N; i++) {
            cur = ports[i];
            System.out.println("cur = " + cur);

            // 1. 배열 요소가 dp 테이블의 최솟값 (0번 요소)와 작으면, 0번 요소를 교체한다.
            if (cur < table[0]) {
                System.out.printf("cur(%d)가 table[0](%d)보다 작음.\n", cur, table[0]);
                table[0] = cur;
                printTable(tail);
            }
            // 2. 현재 요소가 dp 테이블의 최댓값 (tail이 가리키는 요소)보다 크면, 해당 요오슬 dp 테이블의 끝에 추가한다.
            else if (cur > table[tail-1]) {
                System.out.printf("cur(%d)가 table[tail-1](%d)보다 큼.\n", cur, table[tail-1]);
                table[tail] = cur;
                tail++;
                printTable(tail);
            }
            // 3. 배열 요소가 dp 테이블의 최소, 최댓값의 중간 크기라면, binarySearch를 통해 들어갈 인덱스를 찾아 교체한다.
            else {
                System.out.printf("cur(%d)가 table 사이 (%d ~ %d)에 존재함.\n", cur, table[0], table[tail-1]);
                int res = binarySearch(cur, 0, tail-1);
                int idx = -(res+1); // cur가 들어갈 인덱스 (이진 탐색을 통해 인덱스를 찾음)
                table[idx] = cur;
                printTable(tail);
            }

        }

        int result = tail;
        System.out.println(result);

        // 가장 긴 증가하는 수열 문제 (LIS)
        // 배열 [4, 2, 6, 3, 1, 5]가 주어졌을 때, [4, 6], [2, 3, 5] 처럼 증가하는 부분을 뽑아야 한다.

        // 1. 배열을 탐색하면서 큰 수를 뒤에 계속 이어붙인다.
        // 2. 중간에 끼어들 수 있는 수는 이분탐색을 이용하여 적절한 위치에 있던 수와 교체한다.
        // 3. 배열을 탐색하면서 맨 앞 요소보다 작은 요소가 생기면 맨 앞 요소를 교체한다.

    }

    // 이진 탐색 - 찾아서 인덱스를 반환
    public static int binarySearch(int n, int start, int end) {
        int low = start;
        int high = end;

        int mid;
        int midVal;
        while (low <= high) {
            mid = (low + high) / 2;
            midVal = table[mid];
            if (midVal < n) {
                low = mid+1;
            }
            else if (midVal > n) {
                high = mid-1;
            }
            else{
                return mid;
            }
        }

        // 여기서 못 찾으면 low는 찾고자 하는 값 이상이 처음 나오는 위치임.
        return -(low + 1);
        // 못 찾으면 (이 문제에서는 항상 못찾을 수 밖에 없음)
        // 못 찾으면 이 리턴값을 res라 했을 때, -(res+1)을 해주면 dp 테이블에 넣을 인덱스를 찾을 수 있음.
    }

    public static void printTable(int tail) {
        System.out.print("table = ");
        for(int i=0; i<tail; i++) {
            System.out.print(table[i] + " ");
        }
        System.out.println();
    }

}
