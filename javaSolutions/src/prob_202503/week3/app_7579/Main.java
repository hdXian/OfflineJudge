package prob_202503.week3.app_7579;

import java.util.*;
import java.io.*;
import java.math.*;

// 처음 풀이 (메모리 초과)
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M;
    public static int[] mem;
    public static int[] cost;

    public static int totalMem;
    public static int totalCost;
    public static int K;
    public static int[][] table;

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 메모리 상 어플 개수
        M = Integer.parseInt(tkn.nextToken()); // 확보해야 할 메모리 공간

        mem = new int[N+1];
        mem[0] = 0;
        totalMem = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<N+1; i++) {
            mem[i] = Integer.parseInt(tkn.nextToken());
            totalMem += mem[i];
        }
        K = totalMem - M;

        cost = new int[N+1];
        cost[0] = 0;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=1; i<N+1; i++) {
            cost[i] = Integer.parseInt(tkn.nextToken());
            totalCost += cost[i];
        }

        table = new int[K+1][N+1];
        for(int i=0; i<K+1; i++) Arrays.fill(table[i], 0);
    }

    public static void printArr(int[] arr) {
        for (int j : arr) System.out.print(j + " ");
        System.out.println();
    }

    public static void printTable() {
        System.out.println("printTable()");
        for(int i=0; i<K+1; i++) {
            for(int j=0; j<N+1; j++) {
                System.out.printf("%5d ", table[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        init();
        System.out.print("mem: ");
        printArr(mem);
        System.out.print("cost: ");
        printArr(cost);

        calc();

        int max_load_cost = table[K][N];
        int result = totalCost - max_load_cost;
        System.out.println("result = " + result);
    }

    // 최대 K를 담을 수 있는 메모리 공간에, 최대의 비용으로 구성된 앱들을 골라 담기.
    public static void calc() {

//        printTable();

        int res; // res는 메모리에 프로그램을 얹고 남은 용량
        // table = int[K+1][N+1]
        // 아니면.. row가 프로그램 개수가 아니라, 확보할 메모리 크기가 되어야 하나?
        for (int n=1; n<N+1; n++) {

            // k는 메모리에 채울 용량
            for (int k=0; k<K+1; k++) {
                res = k-mem[n];

                // n번 프로그램을 담고 용량이 남으면 (n번 프로그램을 담을 수 있다면)
                if (res >= 0) {
                    // 두 가지 경우 중 비용이 더 큰 경우를 저장
                    // (n번 프로그램 비용 + 남은 res를 채우는 최대 비용)
                    // (n번 프로그램을 제외하고 k를 채우는 최대 비용)
                    int c1 = cost[n] + table[res][n-1];
                    int c2 = table[k][n-1];

                    table[k][n] = Math.max(c1, c2);
                }
                // 만약 n번 프로그램을 담을 수 없다면
                else {
                    // n번 프로그램을 제외하고 담았던 이전 기록을 그대로 가져옴
                    table[k][n] = table[k][n-1];
                }

            }

        }

        System.out.println("table[K][N] = " + table[K][N]);

    }

}
