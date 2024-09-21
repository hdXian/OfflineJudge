package coin_2293;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n,k;
        String[] line = reader.readLine().split(" ");
        n = Integer.parseInt(line[0]);
        k = Integer.parseInt(line[1]);
        ArrayList<Integer> coinList = new ArrayList<>();
        Integer[] coinArr = new Integer[n];

        // 입력받기
        for (int i = 0; i < n; i++) {
//            coinList.add(Integer.parseInt(reader.readLine()));
            coinArr[i] = Integer.parseInt(reader.readLine());
        }

        // 내림차순 정렬
//        coinList.sort(Collections.reverseOrder());
//        System.out.println("coins = " + coinList);

        Arrays.sort(coinArr, Collections.reverseOrder());

        result = 0;
        // 가장 큰 요소가 가장 많이 들어갈 수 있는 경우의 수를 찾고, 하나씩 줄여가며 가능한 경우를 세기
//        calc(coinList, 0, k, n);
        calc(coinArr, 0, k, n);
        System.out.println("result = " + result);
    }

    static void calc(Integer[] coinArr, int idx, int res, int n) {
        System.out.println("\ncalc(Queue" + Arrays.toString(coinArr) + ", res=" + res + ")");

        // 얘가 0이라는건, 앞에서 동전을 다 구성하는데 성공했다는 의미.
        if(res == 0) {
            System.out.println("res is 0\n");
            result++;
            return;
        }

        int coin = coinArr[idx];
        int div = res / coin; // 나머지에서 지금 동전이 챙길 수 있는 최대 몫.
        int mod = res % coin; // 동전을 최대한 가져간 나머지

        // 얘가 마지막 동전일 경우 -> 일단 아래 for문 안돌림
        if (idx == n-1) {
            if (mod == 0) {
                System.out.println("success to make k, div=" + div + "\n");
                result++;
                return;
            }
            else {
                System.out.println("impossible to make k" + "\n");
                return;
            }
        }

        for (int i = 0; i <= div; i++) {
            System.out.println("coin(" + coin + "), i = " + i);
            calc(coinArr, idx+1, res - (coin * i), n);
        }

    }

}
