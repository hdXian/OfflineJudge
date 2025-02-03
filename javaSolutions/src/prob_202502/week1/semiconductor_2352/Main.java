package prob_202502.week1.semiconductor_2352;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N;
    public static int[] port;

    // 메모제이션
    public static int[] lowers;
    public static int[] highers;

    public static int result = -1;

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine()); // 포트 개수
        port = new int[N+1];

        lowers = new int[N+1];
        highers = new int[N+1];
        Arrays.fill(lowers, -1);
        Arrays.fill(highers, -1);

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        int seq = 1;
        int n;
        while (tkn.hasMoreTokens()) {
            n = Integer.parseInt(tkn.nextToken());
            port[seq++] = n; // seq번 포트와 연결되어야 하는 포트는 n (n은 중복되지 않음)
        }


        int tmp;
        int h, l;
        for(int i=1; i<=N; i++) {
            System.out.println();
            System.out.println("i = " + i);

            if (highers[i] == -1) {
                h = calcHigher(i);
                highers[i] = h;
            }
            else
                h = highers[i];

            if (lowers[i] == -1) {
                l = calcLower(i);
                lowers[i] = l;
            }
            else
                l = lowers[i];

            tmp = l + 1 + h;
            if (result < tmp)
                result = tmp;

//            System.out.println("tmp = " + tmp);
        }

        System.out.println("result = " + result);

        // 나보다 인덱스가 큰 수는 dst도 커야 함
        // 나보다 인덱스가 작은 수는 dst도 작아야 함
        // -> 이 조건을 만족하는 가장 크기가 큰 수 집합을 찾아야 함.

        // [n보다 dst가 작은 수 중 dst가 가장 큰 인덱스] + [n] + [n보다 dst가 큰 수 중 dst가 가장 작은 인덱스]

        // 가운데부터 시작하나? 그래야겠는데.

    }

    // int를 리턴하고 재귀해야 하지 않을까?
    public static int calcLower(int n) {
        System.out.println("in lower " + n);
        int dst = port[n];
        // n보다 작은 인덱스들 중, port[] 값이 dst보다 작으면서 그중 가장 큰 놈
        int res = -1;

        // 만약 아래 인덱스 중 더 이상 자기 port 값보다 작은 놈이 없으면 -> res가 -1로 남음
        for (int i=1; i<=n; i++) {

            // 아래 인덱스 중 목적지 port가 나보다 작은 놈이 있는 경우
            if (port[i] < dst) {
                int tmp;
                if (lowers[i] == -1) {
                    tmp = calcLower(i);
                    lowers[i] = tmp;
                }
                else
                    tmp = lowers[i];

                tmp++;
                if (tmp > res) {
                    res = tmp;
                    System.out.println("lower res updated to " + res + ", in lower " + n);
                }
//                res = Math.max(res, 1 + lower(i));
            }

        }

        if (res == -1) {
            System.out.println("end of lower " + n + ", return 0");
            return 0;

        }
        else {
            System.out.println("end of lower " + n + ", return " + res);
            return res;
        }
    }

    public static int calcHigher(int n) {
        System.out.println("in higher " + n);
        int dst = port[n];
        // n보다 큰 인덱스 중 port[]값이 dst보다 크면서 그중 가장 작은 놈
        int res = -1;
        for (int i=n+1; i<=N; i++) {

            if (port[i] > dst) {
                int tmp;
                if (highers[i] == -1) {
                    tmp = calcHigher(i);
                    highers[i] = tmp;
                }
                else
                    tmp = highers[i];

                tmp++;
                if (tmp > res) {
                    res = tmp;
                    System.out.println("higher res updated to " + res + ", in higher " + n);
                }
            }

        }

        if (res == -1) {
            System.out.println("end of higher " + n + ", return 0");
            return 0;
        }
        else {
            System.out.println("end of higher " + n + ", return " + res);
            return res;
        }

    }

}
