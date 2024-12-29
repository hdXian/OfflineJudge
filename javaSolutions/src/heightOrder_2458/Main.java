package heightOrder_2458;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    public static int N, M;
    public static Person[] people;
    public static int result = 0; // 출력할 답

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 학생 수
        M = Integer.parseInt(tkn.nextToken()); // 키를 비교한 횟수

        people = new Person[N+1];
        for(int i=1; i<=N; i++) {
            people[i] = new Person();
        }

        int n1, n2;
        for (int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            n1 = Integer.parseInt(tkn.nextToken());
            n2 = Integer.parseInt(tkn.nextToken());
            // n1 < n2
            people[n1].higher.add(n2);
            people[n2].lower.add(n1);
        }

        for(int i=1; i<=N; i++) {
            System.out.println("in bfs Person " + i);
            if (bfs(i))
                result++;
        }

        System.out.println("result = " + result);

    }

    public static boolean bfs(int n) {
        Person person = people[n];

        boolean[] visited = new boolean[N+1];
        Arrays.fill(visited, false);
        visited[0] = true;
        visited[n] = true;

        // lower들을 bfs 탐색
        System.out.println("in bfs lower()");
        System.out.println("person.lower = " + person.lower);
        Queue<Integer> queue = new LinkedList<>();
        for(Integer i : person.lower) {
            queue.add(i);
            visited[i] = true;
        }

        while (!queue.isEmpty()) {
            System.out.println("queue = " + queue);
            int cur = queue.poll();
            Person p = people[cur];

            for(Integer i: p.lower) {
                if (!visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }

        }

        // higher들을 bfs 탐색
        System.out.println("in bfs higher()");
        queue.clear();
        System.out.println("person.higher = " + person.higher);
        for(Integer i: person.higher) {
            queue.add(i);
            visited[i] = true;
        }

        while(!queue.isEmpty()) {
            System.out.println("queue = " + queue);
            int cur = queue.poll();
            Person p = people[cur];

            for(Integer i: p.higher) {
                if (!visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                }
            }

        }

        System.out.println("print visited");
        for(boolean b: visited) {
            System.out.print(b + " ");
            if (!b) {
                System.out.println();
                return false; // 하나라도 false가 있으면 (방문 안한 곳이 있으면) false 리턴
            }
        }
        System.out.println();
        return true;
    }

    static class Person {
        public List<Integer> higher = new ArrayList<>();
        public List<Integer> lower = new ArrayList<>();
    }

}
