package mutalisk_12869;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(reader.readLine());

        Integer[] scvs = new Integer[N];

        // scv 체력 입력받기
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        for(int i=0; i<N; i++) {
            scvs[i] = Integer.valueOf(tkn.nextToken());
        }

        Arrays.sort(scvs, Collections.reverseOrder());

        for(int i=0; i<N; i++)
            System.out.print(scvs[i] + " ");

        System.out.println();

        int count = 0;
        while (scvs[0] > 0) {
            for(int i=0; i<N; i++)
                System.out.print(scvs[i] + " ");
            System.out.println();
            attack(scvs);
            count++;
            Arrays.sort(scvs, Collections.reverseOrder());
        }

        System.out.println("count = " + count);

    }

    private static void attack(Integer[] scvs) {
        int dmg = 9;
        for(int i=0; i<N; i++) {
            // 차례대로 9, 3, 1의 데미지를 입힘
            scvs[i] -= dmg;
            dmg /= 3;
        }

        System.out.println("attack");
        for(int i=0; i<N; i++)
            System.out.print(scvs[i] + " ");
        System.out.println();
    }

}
