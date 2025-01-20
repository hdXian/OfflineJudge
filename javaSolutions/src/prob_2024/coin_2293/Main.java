package prob_2024.coin_2293;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n,k;
        String[] line = reader.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        k = Integer.parseInt(line[1]);

        ArrayList<Integer> coinList = new ArrayList<>();
        Integer[] coinArr = new Integer[n];
        for (int i = 0; i < n; i++) {
            // 동전 큰 값은 버림
            Integer value = Integer.valueOf(reader.readLine());
            if (value <= k)
                coinList.add(value);
        }

        int[] table = new int[k+1];
        IntStream.range(0, k+1)
                .forEach((i) -> table[i] = 0); // 0으로 초기화, table[0]도 0 (점화식에 사용)

        // 정렬
        Collections.sort(coinList);

        for (Integer coin : coinList) {
            // 자기 몫으로 나누어 떨어지는 부분에 1 추가
            table[coin]++;

            // 지금 동전보다 작은 값들은 건들지 않음
            for (int i=coin+1; i<=k; i++) {
                // table[i]는 바뀌기 전의 값
                // table[i-coin]은 건들지 않았거나 바뀐 후의 값
                table[i] = table[i-coin] + table[i];
            }

        }

        System.out.println("table = " + Arrays.toString(table));
        System.out.println(table[k]);

    }



}
