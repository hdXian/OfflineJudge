package prob_202501.week3.friendNetwork_4195;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, F;
    public static int[] parents;
    public static int[] counts; // cheat

    public static Map<String, Integer> persons;

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 테스트 케이스 개수
        parents = new int[2000002]; // 사람 수는 최대 20만을 넘지 않음. (사용할 인덱스 1~200000)
        counts = new int[200002];

        String person1, person2;
        int seq;
        // logic
        for (int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            F = Integer.parseInt(tkn.nextToken()); // 친구관계 수

            persons = new HashMap<>();
            seq = 0;

            for (int j=0; j<F; j++) {
                System.out.println("\nstart of one testcase");
                tkn = new StringTokenizer(reader.readLine()); // Alice Bob
                person1 = tkn.nextToken();
                person2 = tkn.nextToken();

                System.out.println("person1 = " + person1 + ", person2 = " + person2);

                // 1번 추가 -> counst[1] = 1;
                if (!persons.containsKey(person1)) {
                    seq++;
                    persons.put(person1, seq); // 네트워크에 없던 새로운 사람이면 persons Map에 추가
                    parents[seq] = seq; // parents 배열의 해당 인덱스 값 초기화 (처음 부모는 자기 자신으로)
                    counts[seq] = 1;
                }
                // 1번 추가 -> counst[1] = 1;
                // 2번 추가 -> counst[2] = 1;
                if (!persons.containsKey(person2)) {
                    seq++;
                    persons.put(person2, seq); // 네트워크에 없던 새로운 사람이면 persons Map에 추가
                    parents[seq] = seq; // parents 배열의 해당 인덱스 값 초기화 (처음 부모는 자기 자신으로)
                    counts[seq] = 1;
                }

                Integer n1 = persons.get(person1);
                Integer n2 = persons.get(person2);
                System.out.println("n1 = " + n1 + ", n2 = " + n2);
                union(n1, n2);

                int p1 = findParent(n1); // n1번 사람의 부모 -> 1
                System.out.println("p1 = " + p1);

                int count = counts[p1];
                sb.append(count).append('\n');

//                System.out.println("append " + tmp);
//                sb.append(tmp).append('\n');
//                System.out.println("end of one testcase");
//                System.out.println();
            }

        }

        System.out.println("sb = " + sb);

    }

    // union 하는 과정은 생략 불가
    public static void union(int n1, int n2) {
        int p1 = findParent(n1); // 부모 노드의 번호
        int p2 = findParent(n2); // 부모 노드의 번호

        if (p1 != p2) {
            if (p1 < p2) { // 부모가 1로 정해짐
                parents[p2] = p1;
                counts[p1] += counts[p2];
            }
            else {
                parents[p1] = p2;
                counts[p2] += counts[p1];
            }
        }

    }

    // 함수에 전달하는 n은 Map에 저장해놓은 각 사람의 번호
    public static int findParent(int n) {
        if (parents[n] != n) {
            parents[n] = findParent(parents[n]);
        }
        return parents[n];
    }

    public static void printParent(int size) {
        System.out.print("[");
        for(int i=1; i<size; i++) {
            System.out.print(parents[i] + " ");
        }
        System.out.print(parents[size]);
        System.out.print("]\n");
    }

}
