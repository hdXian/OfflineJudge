package messiGimossi_17297;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    public static int M;
    public static String[] words = {" ", "Messi", "Gimossi"};
    public static int[] wordLengths = {1, 5, 7};

    public static String[] fibo;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        M = Integer.parseInt(reader.readLine());

        System.out.println("M = " + M);

        // M이 int 범위를 넘지 않으니, fibo의 요소 개수도 int 범위를 넘진 않음.
        fibo = new String[46]; // 피보나치 47항부터 오버플로우 남 (피보나치 46번째 항 = 18억 3631만 1903) -> 글자 수가 있으므로 반절도 필요 없어보임.

//        fibo[1] = "1";
//        fibo[2] = "102";
        fibo[1] = "5";
        fibo[2] = "57"; // 글자수들을 저장

        long length1 = 5;
        long length2 = 5 + 1 + 7;
        long curLength = 0;

        char lastChar = ' ';
        for(int i=3; i<=45; i++) {
            System.out.println("i = " + i);

            fibo[i] = fibo[i-1] +  fibo[i-2]; // '0', '1', '2'로만 표현 중

            curLength = length1 + 1 + length2; // 표현할 문자열의 실제 길이
            System.out.println("curLength = " + curLength);

            if (curLength >= M) {
                System.out.println("break");
                // 여기에 도달하면 5757.. 로 표현된 메시 문자열의 실제 길이가 M보다 커짐.

                // 1. fibo[i]를 실제 문자열로 만든다
                // 2. 실제 문자열로 만든 fibo[i]에서 인덱스를 뽑아 먹는다.
                generateString(fibo[i]);

                System.out.println("curLength - M = " + (curLength - M));
                System.out.println("fibo[i].length() = " + fibo[i].length());
                break;
            }

            length1 = length2;
            length2 = curLength;
        }

    }

    private static void generateString(String s) {

        StringBuilder sb = new StringBuilder();
        long curLength = 0;

        String str;
        for(char ch: s.toCharArray()) {

            if (ch == '5') {
                curLength += 6;

                if (curLength >= M) { // 문자열 길이가 M보다 길어지면
                    str = "Messi ";
                    int diff = (int) curLength - M;

                    char res = str.charAt(6-diff-1);
                    if (res == ' ')
                        System.out.println("Messi Messi Gimossi");
                    else {
                        System.out.println(res);
                    }
                    break;
                }

            }
            else {
                curLength += 8;

                if (curLength >= M) {
                    str = "Gimossi ";
                    int diff = (int) curLength - M;

                    char res = str.charAt(8-diff-1);
                    if (res == ' ')
                        System.out.println("Messi Messi Gimossi");
                    else {
                        System.out.println(res);
                    }
                    break;
                }
            }

        }

    }

}
