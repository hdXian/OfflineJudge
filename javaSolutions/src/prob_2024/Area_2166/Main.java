package prob_2024.Area_2166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(reader.readLine());
        BigInteger[] xs = new BigInteger[n+1];
        BigInteger[] ys = new BigInteger[n+1];
//        double res = 0;
        BigDecimal res2 = new BigDecimal("0.0");

        for (int i = 0; i < n; i++) {
            String[] line = reader.readLine().split(" ");
            xs[i] = new BigInteger(line[0]);
            ys[i] = new BigInteger(line[1]);
            System.out.println("xs = " + xs[i]);
            System.out.println("ys = " + ys[i]);
        }

        xs[n] = xs[0];
        ys[n] = ys[0];

        for (int i = 0; i < n; i++) {
//            res += (xs[i] * ys[i+1]) - (ys[i] * xs[i+1]);
//            xs[i].multiply(ys[i+1]).subtract(ys[i].multiply(xs[i+1]));
            BigInteger tmp1 = xs[i].multiply(ys[i + 1]);
            BigInteger tmp2 = ys[i].multiply(xs[i + 1]);
            System.out.println("tmp1 = " + tmp1);
            System.out.println("tmp2 = " + tmp2);
            res2 = res2.add(new BigDecimal(tmp1.subtract(tmp2)));
        }

//        res = (Math.abs(res) / 2.0);
        res2 = res2.abs();
        res2 = res2.divide(new BigDecimal("2.0"), 1, RoundingMode.HALF_UP);

//        System.out.printf("%.1f\n", res);
        System.out.println("res2 = " + res2);
    }

}
