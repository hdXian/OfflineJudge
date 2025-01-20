package prob_202501.week3.friendNetwork_4195;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, F;
    public static int[] parents;

    public static List<Integer> parentList;
    public static Map<String, Integer> persons;

    public static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());

        N = Integer.parseInt(tkn.nextToken()); // 테스트 케이스 개수

        Set<String> people;
        String person1, person2;
        int seq;
        // logic
        for (int i=0; i<N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            F = Integer.parseInt(tkn.nextToken()); // 친구관계 수

            parentList = new LinkedList<>();
            persons = new HashMap<>();
            seq = 0;

            people = new HashSet<>(); // 전체 사람이 들어있는 집합을 만든다.
            for (int j=0; j<F; j++) {
                System.out.println("\nstart of one testcase");
                tkn = new StringTokenizer(reader.readLine()); // Alice Bob
                person1 = tkn.nextToken();
                person2 = tkn.nextToken();

                System.out.println("person1 = " + person1 + ", person2 = " + person2);

                // 만약 둘 다 없으면
                if (!people.contains(person1) && !people.contains(person2)) {
                    System.out.println("both not in set");
                    sb.append(2).append('\n'); // 출력할 결과에 2를 찍고, 둘 다 집합에 포함 및 union 시킨다.
                    people.add(person1);
                    people.add(person2);
                    seq++;
                    persons.put(person1, seq); // 사람 Map에 추가 (이름, 번호)
                    parentList.add(seq); // parents 배열에 새로운 요소 추가 (처음 부모는 자기 자신으로)

                    seq++;
                    persons.put(person2, seq);
                    parentList.add(seq);

                    Integer n1 = persons.get(person1);
                    Integer n2 = persons.get(person2);

                    System.out.println("n1 = " + n1 + ", n2 = " + n2);

                    union(n1, n2);
                    System.out.println("parentList = " + parentList);
                    System.out.println("people = " + people);
                    continue;
                }

                if (!people.contains(person1)) {
                    people.add(person1);
                    seq++;
                    persons.put(person1, seq); // 네트워크에 없던 새로운 사람이면 persons Map에 추가
                    parentList.add(seq); // 부모 리스트도 동적으로 추가
                }

                if (!people.contains(person2)) {
                    people.add(person2);
                    seq++;
                    persons.put(person2, seq); // 네트워크에 없던 새로운 사람이면 persons Map에 추가
                    parentList.add(seq); // 부모 리스트도 동적으로 추가
                }

                System.out.println("people = " + people);

                Integer n1 = persons.get(person1);
                Integer n2 = persons.get(person2);
                System.out.println("n1 = " + n1 + ", n2 = " + n2);
                union(n1, n2);
                System.out.println("parentList = " + parentList);

                int tmp = 0;
                Integer p1 = findParent(n1); // n1번 사람의 부모 -> 1
                Integer p2 = findParent(n2); // n2번 사람의 부모 -> 1
                System.out.println("p1 = " + p1 + ", p2 = " + p2);

                // 두 사람이 속한 그룹과 같은 그룹에 있는 사람들을 찾아야 한다.
                // 두 사람 각각의 부모와 부모가 같은 사람들을 찾아야 한다.
                for(Integer n: persons.values()) {
                    Integer p = findParent(n);
                    if (p.equals(p1) || p.equals(p2))
                        tmp++;
                }

                System.out.println("append " + tmp);
                sb.append(tmp).append('\n');
                System.out.println("end of one testcase");
                System.out.println();
            }

        }

        System.out.println("sb = " + sb);

    }

    public static void union(int n1, int n2) {
        int p1 = findParent(n1); // 부모 노드의 번호
        int p2 = findParent(n2); // 부모 노드의 번호

        if (p1 != p2) {
            if (p1 < p2) {
                parentList.set((p2-1), p1); // p2 인덱스의 요소를 p1으로 변경한다.
            }
            else {
                parentList.set((p1-1), p2); // p1 인덱스의 요소를 p2로 변경한다.
            }
        }

    }

    // 함수에 전달하는 n은 Map에 저장해놓은 각 사람의 번호
    public static Integer findParent(int n) {
        int idx = n-1;
        Integer parent = parentList.get(idx); // 여기서 찾은 parent는 부모의 번호
        if (parent != n) {
            parent = findParent(parent);
        }

        return parent;
    }

}
