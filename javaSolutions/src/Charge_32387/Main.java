package Charge_32387;

import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static int Q;

    public static boolean[] ports;
    public static int[] devices;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        Q = Integer.parseInt(tkn.nextToken());

        ports = new boolean[N+1]; // 포트 (충전기가 꽂혀 있는지 여부를 저장)
        devices = new int[N+1]; // 몇 번째 행동에서 꽂힌 기기인지 기억
        Arrays.fill(ports, false);
        Arrays.fill(devices, 0);

        StringBuilder sb = new StringBuilder();

        int action;
        int i;
        int res;

        for(int x=1; x<=Q; x++) {
            tkn = new StringTokenizer(reader.readLine());
            action = Integer.parseInt(tkn.nextToken());
            i = Integer.parseInt(tkn.nextToken());

            // 1번 행동
            if (action == 1) {
                res = action1(x, i);
                sb.append(res).append('\n');
            }
            // 2번 행동
            else if (action == 2) {
                res = action2(i);
                sb.append(res).append('\n');
            }

        }

        System.out.println(sb);
    }

    // 1번 행동: 출력이 i인 기기를 꽂으려고 시도
    private static int action1(int seq, int i) {
        // 해당 포트에 기기가 꽂혀있다면
        if (ports[i]) {
            // i보다 큰 포트 중 가장 작은 포트를 찾음 (여기가 가장 오래 걸림)
            int k = i;
            while(ports[k]) {
                if (k>=N) {
                    return -1;
                }
                k++;
            }
            ports[k] = true;
            devices[k] = seq;
            return k;

        }
        // 포트가 비어있다면
        else {
            ports[i] = true;
            devices[i] = seq;
            return i;
        }
    }

    // 2번 행동: i번 포트의 기기를 제거
    private static int action2(int i) {
        // 해당 포트에 기기가 꽂혀 있다면
        if (ports[i]) {
            ports[i] = false;
            return devices[i];
        }
        // 포트가 비어있다면
        else {
            return -1;
        }
    }

}
