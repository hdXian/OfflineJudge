package prob_202504.week2.rooftopGarden_6198;

import java.util.*;
import java.io.*;
import java.math.*;

// 처음 풀이 - 브루트포스
public class Main2 {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int N;
    static int[] hs;

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 빌딩 수. 1 ~ 8만
        hs = new int[N+1];

        // 빌딩 높이 입력. 1 ~ 10억
        for(int i=1; i<=N; i++) hs[i] = Integer.parseInt(reader.readLine());
    }

    static void calc() throws Exception {
        long result = 0;

        int tmp;
        for (int i=N-1; i>=1; i--) {
            tmp = 0;
            for (int k=i+1; k<=N; k++) {
                if (hs[i] <= hs[k])
                    break;
                tmp++;
            }
            result += tmp;
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
