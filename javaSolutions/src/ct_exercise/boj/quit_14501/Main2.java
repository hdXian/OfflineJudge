package ct_exercise.boj.quit_14501;

import java.math.*;
import java.io.*;
import java.math.*;
import java.util.StringTokenizer;

public class Main2 {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] t_arr;
    static int[] p_arr;

    static void init() throws Exception {
        N = Integer.parseInt(reader.readLine());
        t_arr = new int[N];
        p_arr = new int[N];

        StringTokenizer tkn;
        for(int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            t_arr[i] = Integer.parseInt(tkn.nextToken());
            p_arr[i] = Integer.parseInt(tkn.nextToken());
        }
    }

    static void calc() {
        // table[i] = i번째 날에 받을 수 있는 최대 금액
        int[] table = new int[N+1];

        // 각각의 날짜에 대해, 현재 날짜에 해당하는 일을 할 때와 하지 않을 때 중 더 금액이 큰 경우를 저장
        // 내가 n번째 날에 일을 했다 -> n번째 날의 임금을 받고, n + t[i]번째 날 까지 통장 잔액이 고정된다. 그럼에도 이 날에 일을 하는 것이 이득인가? -> n + t[i]번째 날 인덱스에 max 연산.
        // 즉 n번째 날에 구성 가능한 통장 잔액 최댓값은 1 ~ n-1번째 날들에 대해 일을 해서 n번째 날에 일이 끝나는 경우들을 비교하면 됨. -> for문을 돌면서 일이 끝나는 날의 인덱스에 대해 max 연산을 한다.
        // ex) 4일째에 구성 가능한 통장 잔액의 최대 -> (1일째에 3일 걸리는 일 수락하기) vs (2일째에 2일 걸리는 일 수락하기)
        // for문이 n에 도달했다면, 이전까지의 계산에 의해 n번째 날에 받을 수 있는 최대 금액이 산정되었을 것.
        // n번째 날에 받을 수 있는 돈의 최대는 n번 인덱스 이후로 업뎃되지 않으므로, (현재 n+1번째 날에 저장돼있는 금액, n번째 날까지 번 돈의 총액)을 비교해서 n+1번째 인덱스에 저장함.
        int income, next_day;
        for(int i=0; i<N; i++) {
            System.out.println("i = " + (i+1));
            income = p_arr[i]; // i번째 날 일을 한다면 받게 될 수입
            next_day = i + t_arr[i]; // i번째 날 일을 한다면 다음에 일할 수 있는 날

            if (next_day < N+1) {
                table[next_day] = Math.max(table[next_day], table[i] + income);
            }

            // i+1번째 날의 최대 수익은 (i번째 날까지의 최대 수익, 그동안 누적된 i+1번째 날의 최대 수익)
            table[i+1] = Math.max(table[i], table[i+1]);
            // 그냥 i번째 날에 일을 안하는 경우를 마지막으로 비교하는 걸수도, i번째 날에 1일 걸리는 일을 비교하는 걸 수도 있음
            // 후자는 (i번째 까지의 최대 잔액 + i번째 날 일을 하면 받을 보수) 를 가지고 비교하는거겠지.

            for (int j : table) {
                System.out.print(j + " ");
            }
            System.out.println();
            System.out.println();
        }



        System.out.println("table[N] = " + table[N]);

    }


    public static void main(String[] args) throws Exception {
        init();
        calc();
    }
}
