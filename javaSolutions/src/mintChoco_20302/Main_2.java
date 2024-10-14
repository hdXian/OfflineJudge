package mintChoco_20302;

import java.io.*;
import java.util.*;

public class Main_2 {

    public static boolean[] primes = new boolean[100000];

    public static HashMap<Integer, Integer> prime_map = new HashMap<>();

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        Arrays.fill(primes, true);

        // 소수가 아닌 인덱스에 대해 false 처리
        calcPrime();

        // 소수들 map 초기화
        for(int i=2; i<100000; i++) {
            if (primes[i]) {
                prime_map.put(i, 0);
            }
        }

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        // 첫 숫자는 분자에 추가
        calcUpper(Integer.parseInt(tkn.nextToken()));

        int num;
        String exp;
        while(tkn.hasMoreTokens()) {
            // 연산자, 숫자
            exp = tkn.nextToken();
            num = Integer.parseInt(tkn.nextToken());

            if(exp.equals("*")) {
                calcUpper(num);
            }
            else {
                calcLower(num);
            }

        }

    }

    // 분자 - 소인수분해해서 소수 map에 +1
    private static void calcUpper(int n) {
        // TODO
        // hashmap을 순서보장해야 할지도? 아니다. key들을 끌고올까?
    }

    // 분모 - 소인수분해해서 소수 map에 -1
    private static void calcLower(int n) {
        // TODO
    }

    // primes 배열 초기화
    private static void calcPrime() {

        // 2~ 100000사이의 수 중 2부터 350사이 중 소수의 배수들을 제거.
        for(int i=2; i<350; i++) {
            // i가 소수일 경우 -> 10만 사이의 수 중 i의 배수는 모두 소수가 아님.
            if (checkPrime(i)) {
                // i의 2배수부터..
                for(int k=(i+i); k<=100000; k+=i) {
                    primes[k] = false;
                }
            }
        }
    }

    private static boolean checkPrime(int n) {

        for(int i=n-1; i>1; i--) {
            if (n % i == 0)
                return false;
        }

        return true;
    }

}
