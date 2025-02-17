package prob_202502.week3.pizzaSales_2632;

import java.io.*;
import java.util.*;
import java.math.*;

// 3. 누적합 활용 - 메인 코드에 그대로 적용

public class Main3 {

    public static int P;
    public static int M, N;

    // 만들 수 있는 경우의 수를 저장
    public static int[] tableA;
    public static int[] tableB;

    public static int[] piecesA;
    public static int[] piecesB;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        P = Integer.parseInt(tkn.nextToken()); // 주문 피자 크기 (1 ~ 200만)

        tableA = new int[P+1];
        tableB = new int[P+1];
        tableA[0] = 1;
        tableB[0] = 1;

        tkn = new StringTokenizer(reader.readLine());
        M = Integer.parseInt(tkn.nextToken()); // 피자 A 조각 개수 (3 ~ 1000)
        N = Integer.parseInt(tkn.nextToken()); // 피자 B 조각 개수

        // 피자 A 조각 입력받기
        // ex) 2 2 1 7 2
        piecesA = new int[M];
        int piece;
        for(int i=0; i<M; i++) {
            piece = Integer.parseInt(reader.readLine());
            piecesA[i] = piece;
        }
        // 누적합을 이용해 모든 연속된 조각의 경우의 수 구하기 -> 이걸 사용해서 시간을 줄여야 하나보다. 이걸 찾아보자. 다른 알고리즘은 다른게 없는 것 같음.
        calcSubSum(piecesA, tableA);

        // 피자 B 조각 입력받기
        piecesB = new int[N];
        for(int i=0; i<N; i++) {
            piece = Integer.parseInt(reader.readLine());
            piecesB[i] = piece;
        }
        // 누적합을 이용해 모든 연속된 조각의 경우의 수 구하기 -> 이걸 사용해서 시간을 줄여야 하나보다. 이걸 찾아보자. 다른 알고리즘은 다른게 없는 것 같음.
        calcSubSum(piecesB, tableB);

        long result = 0;
        for(int i=0; i<=P; i++) {
            result += ((long) tableA[i] * tableB[P-i]);
        }

        System.out.println("result = " + result);

    }

    public static void calcSubSum(int[] pieces, int[] table) {
        int length = pieces.length;

        int[] prefix_sum = new int[length+1]; // 누적 합 배열

        // 누적 합 배열 초기화
        // prefix[i] : pieces 배열의 0 ~ i-1 인덱스까지 더한 값을 저장.
        for(int i=0; i<length; i++) {
            prefix_sum[i+1] = prefix_sum[i] + pieces[i];
        }

        // 한 조각으로 해결하는 경우 추가
        int piece;
        for(int i=0; i<length; i++) {
            piece = pieces[i];
            table[piece] += 1;
        }

        // 모든 조각을 이어붙인 경우 추가
        piece = 0;
        for(int i=0; i<length; i++) {
            piece += pieces[i];
        }
        if (piece <= P)
            table[piece] = 1;


        for(int i=2; i<length; i++) {
            System.out.println("i = " + i);
            // j번째 조각부터 i개의 조각 이어붙이기
            for(int start=0; start<length; start++) {
                System.out.println("start = " + start);

                int end = (start + i - 1) % length; // 이어붙일 마지막 조각의 인덱스
                System.out.println("end = " + end);
                // end 인덱스까지 더한 값(prefix 배열)에서 start 인덱스 값만 빼면 해당 구간의 값을 구할 수 있음.
                int startVal = (start == 0) ? 0 : prefix_sum[start];
                System.out.println("startVal = " + startVal);

                // 이어붙인 피자 사이즈
                int size = prefix_sum[end+1] - startVal;
                if (size <= P)
                    table[size]++;
            }

        }

    }

    public static void printTableA() {
        System.out.print("[ ");
        for(int i=0; i<=P; i++) {
            System.out.print(tableA[i] + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    public static void printTableB() {
        System.out.print("[ ");
        for(int i=0; i<=P; i++) {
            System.out.print(tableB[i] + " ");
        }
        System.out.print("]");
        System.out.println();
    }

}
