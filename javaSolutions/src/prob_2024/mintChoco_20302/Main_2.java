package prob_2024.mintChoco_20302;

import java.io.*;
import java.util.*;

public class Main_2 {

    public static HashMap<Integer, Integer> prime_map = new HashMap<>();
    public static int[] primeCounts;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        ArrayList<Integer> upper = new ArrayList<>(); // 분자
        ArrayList<Integer> lower = new ArrayList<>(); // 분모

        // 다음 줄 읽어들이기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        // 첫 숫자는 분자에 추가
        upper.add(Integer.parseInt(tkn.nextToken()));

        // 분자, 분모 숫자 추가
        int num;
        String exp;
        while (tkn.hasMoreTokens()) {
            exp = tkn.nextToken();
            num = Integer.parseInt(tkn.nextToken());

            if (exp.equals("*"))
                upper.add(num);
            else
                lower.add(num);

        }

        Integer upper_max = Collections.max(upper);
        Integer lower_max = Collections.max(lower);

        int primes_length = (upper_max > lower_max) ? (upper_max+1) : (lower_max+1);
        boolean[] primes = new boolean[primes_length];
        primeCounts = new int[primes_length];

        // 처음에는 모두 true로 초기화
        Arrays.fill(primes, true);
        Arrays.fill(primeCounts, 0);

        // primes 배열 초기화 -> 소수가 아닌 인덱스에 대해 false 처리
        calcPrime(primes, primes_length);

        ArrayList<Integer> primeArr = new ArrayList<>();
        // 소수들 map 초기화
        for(int i=2; i<primes_length; i++) {
            if (primes[i]) {
                primeArr.add(i);
//                prime_map.put(i, 0); // 소수: 개수
            }
        }

        // keyset으로 List 생성 후 정렬
//        ArrayList<Integer> primeArr = new ArrayList<>(prime_map.keySet());
        Collections.sort(primeArr);
        System.out.println("\nprimeArr = " + primeArr);

        for (Integer u : upper) {
            calcUpper(u, primeArr);
        }

        for (Integer l: lower) {
            calcLower(l, primeArr);
        }

        System.out.println("prime_map = " + prime_map);
        // 지수 중에 -1이 있으면 유리수
//        if(prime_map.containsValue(-1)) {
//            System.out.println("toothpaste");
//        }
//        else {
//            System.out.println("mint chocolate");
//        }

        boolean mint_flag = true;
        for(int i=2; i<primes_length; i++) {
            if (primeCounts[i] < 0) {
                mint_flag = false;
//                System.out.println("toothpaste");
                break;
            }
        }

        if (mint_flag) {
            System.out.println("mint chocolate");
        }
        else {
            System.out.println("toothpaste");
        }

    }

    // 분자 - 소인수분해해서 소수 map에 +1
    private static void calcUpper(int n, List<Integer> primeArr) {
        // TODO
        System.out.println("calcUpper: n = " + n);
        for (Integer prime: primeArr) {
            if (n < 2) break; // n이 2 아래면 소인수 분해 끝난거임
            Integer val = prime_map.get(prime);
            System.out.println("prime = " + prime);

            // n보다 큰 수는 건너뜀
//            if (val > n) continue;

            // 나누어 떨어지는동안 계속 나눔
            while ((n%prime) == 0) {
                n /= prime;
//                prime_map.put(prime, val+1);
                primeCounts[prime]++;
                System.out.println("calcUpper: prime " + prime + "의 지수 1 증가");
            }
        }

        // hashmap을 순서보장해야 할지도? 아니다. key들을 끌고올까?
    }

    // 분모 - 소인수분해해서 소수 map에 -1
    private static void calcLower(int n, List<Integer> primeArr) {
        // TODO
        System.out.println("calcLower: n = " + n);
        for (Integer prime: primeArr) {
            if (n < 2) break; // n이 2 아래면 소인수 분해 끝난거임
            Integer val = prime_map.get(prime);
            System.out.println("prime = " + prime);

            // n보다 큰 수는 건너뜀
//            if (val > n) continue;

            // 나누어 떨어지는동안 계속 나눔
            while ((n%prime) == 0) {
                n /= prime;
//                prime_map.put(prime, val-1);
                primeCounts[prime]--;
                System.out.println("calcLower: prime " + prime + "의 지수 1 감소");
            }

        }
    }

    // primes 배열 초기화
    private static void calcPrime(boolean[] primes, int length) {

        System.out.println("calcPrime");
        // 2~ 100000사이의 수 중 2부터 350사이 중 소수의 배수들을 제거.
        for(int i=2; i<(length/2); i++) {
            System.out.println("i = " + i);
            // i가 소수일 경우 -> i의 배수는 모두 소수가 아님.
            if (checkPrime(i)) {
                // i의 2배수부터..
                for(int k=(i+i); k<length; k+=i) {
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
