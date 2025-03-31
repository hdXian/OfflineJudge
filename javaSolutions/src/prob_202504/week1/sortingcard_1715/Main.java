package prob_202504.week1.sortingcard_1715;

import java.math.*;
import java.io.*;
import java.util.*;

public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N;
    public static Queue<BigInteger> cards = new PriorityQueue<>();

    public static void init() throws Exception {
        N = Integer.parseInt(reader.readLine()); // 1 ~ 10만

        long val;
        for(int i=0; i<N; i++) {
            val = Integer.parseInt(reader.readLine());
            cards.add(BigInteger.valueOf(val));
        }
    }

    public static BigInteger calc() throws Exception {
        // 큐에서 2개씩 꺼내서 더한다.
        // 그걸 다시 큐에 넣는다. 그리디네.
        // 다만 n회차씩은 구분해야 함.

        // 아니다. 무조건 2개씩 끊는게 아니라, 가장 작은 2개씩 뽑아서 계속 더하는거다. 1개 남을때까지.
        BigInteger result = BigInteger.ZERO;
        BigInteger deck = BigInteger.ZERO;
        BigInteger n1, n2;
        while (cards.size() >= 2) {
            n1 = cards.poll();
            n2 = cards.poll();
            deck = n1.add(n2);
            result = result.add(deck);
            cards.add(deck);
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        init();
        BigInteger result = calc();
        System.out.println(result);
    }

}
