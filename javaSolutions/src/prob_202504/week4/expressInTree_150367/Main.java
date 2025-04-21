package prob_202504.week4.expressInTree_150367;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader getFileReader() throws Exception {
        URL urlResource = Solution.class.getResource("input.txt");
        File input = new File(urlResource.toURI());
        FileInputStream fis = new FileInputStream(input);
        return new BufferedReader(new InputStreamReader(fis));
    }

    public static void main(String[] args) throws Exception {
        BufferedReader reader = getFileReader();

        Solution s = new Solution();
        String line;
        int n, m, x, y, r, c, k;

        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");

            long[] numbers = new long[split.length];
            for(int i=0; i<split.length; i++)
                numbers[i] = Long.parseLong(split[i]);

            int[] result = s.solution(numbers);

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(int i: result)
                sb.append(i).append(", ");
            sb.delete(sb.length()-2, sb.length());
            sb.append("]");

            System.out.println(sb);
        }

    }

}
