package prob_202505.week2.pibonacci_24417;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N; // n의 피보나치 수

    static void calc() {
        // 재귀호출
        // (a + b) mod x = (a mod x + b mod x) mod x
        long p = 1000000007; // 10억 7
        // 재귀호출 실행횟수
        long recur_result = 0;

        long pre1 = 1;
        long pre2 = 1;
        for(int i=0; i<N-2; i++) {
            recur_result = (pre1 + pre2) % p;
            pre2 = pre1;
            pre1 = recur_result;
        }

        System.out.println("recur_result = " + recur_result);
        // dp 호출횟수
        long dp_result = N-2;
        System.out.println("dp_result = " + dp_result);
        System.out.printf("%d %d\n", recur_result, dp_result);
    }

    public static void main(String[] args) throws Exception {
        N = Integer.parseInt(reader.readLine()); // n의 피보나치 수 (5 ~ 2억)
        calc();
    }

}
