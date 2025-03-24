package prob_202503.week4.scale_2629;

import java.util.*;
import java.io.*;
import java.math.*;

// 풀이 2 - 1차원 배열 활용
public class Main2 {
    
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M; // 추 개수, 구슬 개수

    public static int[] rims; // 무게추 배열
    public static int[] balls; // 구슬 배열

    public static boolean[] ables;
    public static int MAX_WEIGHT;

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 무게추 개수 (1~30), 각 추의 무게 1~500
        rims = new int[N];
        MAX_WEIGHT = N*500 + 1;
        ables = new boolean[MAX_WEIGHT];

        StringTokenizer tkn;
        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++)
            rims[i] = Integer.parseInt(tkn.nextToken());

        M = Integer.parseInt(reader.readLine()); // 대상 구슬 개수
        balls = new int[M];

        tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<M; i++)
            balls[i] = Integer.parseInt(tkn.nextToken());

    }

    public static void calc() throws Exception {
        // 1. 무게추들을 가져와 가능한 무게들의 집합을 생성한다.
        Set<Integer> tmp = new HashSet<>();
        for (int rim: rims) {
            for(int n=0; n<MAX_WEIGHT; n++) {
                if (ables[n])
                    tmp.add(n);
            }

            ables[rim] = true;
            for(int t: tmp) {
                int idx = t+rim;
                if (idx < MAX_WEIGHT) ables[idx] = true;

                idx = Math.abs(t-rim);
                if (idx < MAX_WEIGHT) ables[idx] = true;
            }

            tmp.clear();
        }

        // 2. 그 구슬들이 집합에 속하는지 확인한다.
        StringBuilder sb = new StringBuilder();

        for(int b:balls) {
            if (b >= MAX_WEIGHT || !ables[b])
                sb.append("N ");
            else
                sb.append("Y ");
        }

        System.out.println("sb = " + sb);

    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }
    
}
