package mintChoco_20302;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main_3 {

    public static boolean[] primes = new boolean[100001];
    public static int[] primeCount = new int[100001];
    public static boolean zero_flag = false;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        ArrayList<Integer> upper = new ArrayList<>(); // 분자
        ArrayList<Integer> lower = new ArrayList<>(); // 분모

        Arrays.fill(primes, true);
        Arrays.fill(primeCount, 0);

        // primes 배열 초기화 -> 소수가 아닌 인덱스에 대해 false 처리
        // 2~ 100000사이의 수 중 2부터 350사이 중 소수의 배수들을 제거.
        for(int i=2; i<350; i++) {
            // i가 소수일 경우 -> i의 배수는 모두 소수가 아님.
            if (checkPrime(i)) {
                // i의 2배수부터..
                for(int k=(i+i); k<=100000; k+=i) {
                    primes[k] = false;
                }
            }
        }

        // 소수 배열
        ArrayList<Integer> primeArr = new ArrayList<>();
        for(int i=2; i<100000; i++) {
            if (primes[i]) {
                primeArr.add(i);
            }
        }
        Collections.sort(primeArr);
        System.out.println("\nprimeArr = " + primeArr);

        // 수식 읽어들이기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        // 첫 숫자는 분자에 추가
        int first = Math.abs(Integer.parseInt(tkn.nextToken()));
        calcUpper(first, primeArr);

        int num;
        String exp;
        while (tkn.hasMoreTokens()) {

            exp = tkn.nextToken();
            num = Math.abs(Integer.parseInt(tkn.nextToken()));

            if (exp.equals("*")) {
                calcUpper(num, primeArr);
            }

            else {
                calcLower(num, primeArr);
            }

        }

        boolean mint_flag = true;
        for (int count : primeCount) {
            if (count < 0) {
                mint_flag = false;
                break;
            }
        }

        if (zero_flag || mint_flag) {
            System.out.println("mint chocolate");
        }
        else {
            System.out.println("toothpaste");
        }

    }

    // 분자 - 소인수분해해서 지수 +1
    private static void calcUpper(int n, List<Integer> primeArr) {
        if (n == 0)
            zero_flag = true;

        // TODO
        System.out.println("calcUpper: n = " + n);
        for (Integer prime: primeArr) {
            if (n < 2) break; // n이 2 아래면 소인수 분해 끝난거임
            System.out.println("prime = " + prime);

            // n보다 큰 수는 건너뜀
//            if (prime > n) break;

            // 나누어 떨어지는동안 계속 나눔
            while ((n%prime) == 0) {
                n /= prime;
                primeCount[prime]++;
                System.out.println("calcUpper: prime " + prime + "의 지수 1 증가");
            }
        }
    }

    // 분모 - 소인수분해해서 지수 -1
    private static void calcLower(int n, List<Integer> primeArr) {
        // TODO
        System.out.println("calcLower: n = " + n);
        for (Integer prime: primeArr) {
            if (n < 2) break; // n이 2 아래면 소인수 분해 끝난거임
            System.out.println("prime = " + prime);

            // n보다 큰 수는 건너뜀
//            if (prime > n) break;

            // 나누어 떨어지는동안 계속 나눔
            while ((n%prime) == 0) {
                n /= prime;
                primeCount[prime]--;
                System.out.println("calcLower: prime " + prime + "의 지수 1 감소");
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
