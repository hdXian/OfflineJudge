package prob_2024.mintChoco_20302;

import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());

        BigDecimal upper = BigDecimal.ONE;
        BigDecimal lower = BigDecimal.ONE;

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        // 첫 숫자는 분자에 추가
        upper = upper.multiply(BigDecimal.valueOf(Integer.parseInt(tkn.nextToken())));

        int num;
        String exp;
        while(tkn.hasMoreTokens()) {

            // 연산자, 숫자
            exp = tkn.nextToken();
            num = Integer.parseInt(tkn.nextToken());

            if(exp.equals("*")) {
                upper = upper.multiply(BigDecimal.valueOf(num));
            }
            else {
                lower = lower.multiply(BigDecimal.valueOf(num));
            }

        }

        System.out.println("upper = " + upper);
        System.out.println("lower = " + lower);

        // 소수점 버림
        BigDecimal div = upper.divide(lower, RoundingMode.HALF_DOWN);
        System.out.println("res = " + div);

        BigDecimal lower_div = lower.multiply(div);

        // upper - (lower * div)가 0이 아니면 유리수라는 (나누어 떨어지지 않았다는) 의미
        boolean res = (upper.equals(lower_div));
        if (res) {
            System.out.println("mint chocolate");
        }
        else {
            System.out.println("toothpaste");
        }

    }

}
