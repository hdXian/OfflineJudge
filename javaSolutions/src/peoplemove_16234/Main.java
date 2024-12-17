package peoplemove_16234;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, L, R;
    public static int[][] land;
    public static int[] parents; // union find 부모 배열
    public static int seq = 0; // 연합 이동 횟수 (답)

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 한 줄 크기 (나라는 총 N * N개)
        L = Integer.parseInt(tkn.nextToken()); // 인구 조건 1
        R = Integer.parseInt(tkn.nextToken()); // 인구 조건 2

        // 땅과 그래프 초기화
        land = new int[N][N];
        parents = new int[N*N];
        for(int i=0; i<N*N; i++)
            parents[i] = i;

        // 각 나라 인구수 초기화
        for(int row=0; row<N; row++) {
            tkn = new StringTokenizer(reader.readLine());
            for(int col=0; col<N; col++) {
                land[row][col] = Integer.parseInt(tkn.nextToken());
            }
        }

        // 1. 인구 상태를 확인하여 국경선을 열고 연합을 구성한다.
        unite();
        for(int i=0; i<N*N; i++) {
            System.out.print(parents[i] + " ");
        }
        System.out.println();

        // 2. 인구 이동을 수행한다.

        // 3. 연합이 생기지 않을 때까지 반복한다.

    }

    // 인구 상태를 확인하고 연합을 구성
    public static void unite() {
        // 연합을 구성한다 -> union 시킨다.
        for(int row=0; row<N; row++) {
            for(int col=0; col<N; col++) {
                checkAndUnite(row, col);
            }
        }

    }

    // 주변 나라와의 인구수 차이를 비교하고 연합
    public static void checkAndUnite(int r, int c) {
        int countryNum = (r*N) + c; // 국가 번호 (0부터 시작)
        int people = land[r][c]; // 그 나라의 인구수

        int anotherCountryNum; // 다른 나라 번호
        int anotherPeople; // 다른 나라 인구
        int diff; // 인구수 차이

        // 상
        if ((r-1) >= 0) {
            anotherCountryNum = countryNum - N;
            anotherPeople = land[r-1][c];
            diff = Math.abs(people - anotherPeople);
            if ((diff >= L) && (diff <= R)) {
                union(countryNum, anotherCountryNum);
            }
        }

        // 하
        if ((r+1) < N) {
            anotherCountryNum = countryNum + N;
            anotherPeople = land[r+1][c];
            diff = Math.abs(people - anotherPeople);
            if ((diff >= L) && (diff <= R)) {
                union(countryNum, anotherCountryNum);
            }
        }

        // 좌
        if ((c-1) >= 0) {
            anotherCountryNum = countryNum-1;
            anotherPeople = land[r][c-1];
            diff = Math.abs(people - anotherPeople);
            if ((diff >= L) && (diff <= R)) {
                union(countryNum, anotherCountryNum);
            }
        }

        // 우
        if ((c+1) < N) {
            anotherCountryNum = countryNum+1;
            anotherPeople = land[r][c+1];
            diff = Math.abs(people - anotherPeople);
            if ((diff >= L) && (diff <= R)) {
                union(countryNum, anotherCountryNum);
            }
        }

    }

    public static void union(int n1, int n2) {
        int p1 = findParent(n1);
        int p2 = findParent(n2);

        if (p1 < p2) {
            parents[p2] = p1;
        }
        else {
            parents[p1] = p2;
        }
    }

    public static int findParent(int n) {
        if (parents[n] != n) {
            parents[n] = findParent(parents[n]); // 경로 압축
        }
        return parents[n];
    }


}
