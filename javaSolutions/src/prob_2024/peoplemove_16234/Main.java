package prob_2024.peoplemove_16234;

import java.util.*;
import java.io.*;

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

        while(true) {
            for(int i=0; i<N*N; i++)
                parents[i] = i;

            // 1. 인구 상태를 확인하여 국경선을 열고 연합을 구성한다.
            unite();
            for(int i=0; i<N*N; i++) {
                System.out.print(parents[i] + " ");
            }
            System.out.println();

            // 2. 인구 이동을 수행한다.
            boolean flag = movePeople();

            // 3. 연합이 생기지 않을 때까지 반복한다.
            if(flag) break;
            seq++;

            for(int row=0; row<N; row++) {

                for(int col=0; col<N; col++) {
                    System.out.print(land[row][col] + " ");
                }
                System.out.println();
            }

        }

        System.out.println("seq = " + seq);

    }

    public static boolean movePeople() {
        // 2-1. 루트들을 뽑는다.
        List<Integer> roots = new ArrayList<>();
        for(int i=0; i<N*N; i++) {
            if (i == findParent(i))
                roots.add(i);
        }
        System.out.println("roots = " + roots);

        // 루트 개수가 N*N과 같다 -> 연합이 만들어지지 않았다.
        if (roots.size() == N*N) {
            return true;
        }

        // 2-2. 루트들에 대해서 평균을 구하고 값을 집어넣는다.
        List<Integer> countries;
        for (int root: roots) {
            int countryNum;
            countries = new ArrayList<>();
            int total = 0;

            // 연합 국가의 수와 인구수 합을 구한다.
            for(int row=0; row<N; row++) {
                for(int col=0; col<N; col++) {
                    countryNum = (row*N) + col;

                    // root가 같다 -> 같은 그룹이다
                    if (findParent(countryNum) == root) {
                        total += land[row][col]; // 전체 인구 합에 더한다
                        countries.add(countryNum); // 회원국 리스트에 추가한다
                    }

                }
            }

            // 평균 구하기
            int people = total / (countries.size());

            // 값 집어넣기
            for(int c: countries) {
                int row = c/N;
                int col = c%N;
                land[row][col] = people;
            }

        }

        // 여기까지 왔다 -> 연합이 생겨서 인구 이동을 수행했다.
        return false;
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

        int p1 = findParent(countryNum);

        // 상
        if ((r-1) >= 0) {
            anotherCountryNum = countryNum - N;
            int p2 = findParent(anotherCountryNum);

            if (p1 != p2) {
                anotherPeople = land[r-1][c];
                diff = Math.abs(people - anotherPeople);
                if ((diff >= L) && (diff <= R)) {
                    union(countryNum, anotherCountryNum);
                }
            }

        }

        // 하
        if ((r+1) < N) {
            anotherCountryNum = countryNum + N;
            int p2 = findParent(anotherCountryNum);

            if (p1 != p2) {
                anotherPeople = land[r+1][c];
                diff = Math.abs(people - anotherPeople);
                if ((diff >= L) && (diff <= R)) {
                    union(countryNum, anotherCountryNum);
                }
            }

        }

        // 좌
        if ((c-1) >= 0) {
            anotherCountryNum = countryNum-1;
            int p2 = findParent(anotherCountryNum);

            if (p1 != p2) {
                anotherPeople = land[r][c-1];
                diff = Math.abs(people - anotherPeople);
                if ((diff >= L) && (diff <= R)) {
                    union(countryNum, anotherCountryNum);
                }
            }

        }

        // 우
        if ((c+1) < N) {
            anotherCountryNum = countryNum+1;
            int p2 = findParent(anotherCountryNum);

            if (p1 != p2) {
                anotherPeople = land[r][c+1];
                diff = Math.abs(people - anotherPeople);
                if ((diff >= L) && (diff <= R)) {
                    union(countryNum, anotherCountryNum);
                }
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
