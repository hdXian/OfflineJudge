package prob_202504.week4.delivery_8980;

import java.util.*;
import java.io.*;
import java.math.*;

public class Main {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int N, C, M;

    static List<Map<Integer, Integer>> town = new ArrayList<>(); // 마을

    static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 마을 수 (2 ~ 2000)

        C = Integer.parseInt(tkn.nextToken()); // 트럭 용량 (1 ~ 10000)

        M = Integer.parseInt(reader.readLine()); // 정보 개수 (1 ~ 10000)

        // 각 마을의 택배 정보를 저장할 map 초기화
        for(int i=0; i<N; i++) {
            town.add(new HashMap<>());
        }

        // 마을의 택배 정보 저장
        int src, dst, amount;
        for(int i=0; i<M; i++) {
            tkn = new StringTokenizer(reader.readLine());
            src = Integer.parseInt(tkn.nextToken());
            dst = Integer.parseInt(tkn.nextToken());
            amount = Integer.parseInt(tkn.nextToken());
            town.get(src-1).put(dst, amount);
        }
    }

    static void calc() throws Exception {
        int count = 0; // 총 배송 수
        Map<Integer, Integer> truck = new HashMap<>();

        // 1. 가장 가까운 마을 순으로 최대한 상자를 담는다.
        Map<Integer, Integer> t;

        // 각 마을을 순회
        for(int i=1; i<=N; i++) {
            System.out.println("현재 마을 i = " + i);

            // 현재 마을이 목적지인 짐을 모두 내린다.
            if (truck.containsKey(i)) {
                System.out.println("짐을 내리는 중: truck.get(i) = " + truck.get(i));
                count += truck.get(i);
                truck.remove(i);
            }
            System.out.println("truck.keySet() = " + truck.keySet());

            // 트럭에 남은 짐 + 마을에 존재하는 짐 중 가장 가까운 것들로 트럭을 업데이트.
            // 현재 마을에 더 가까운게 있으면 전에 마을에서 안 가져온걸로 치는거임.

            // 현재 마을의 택배 목록을 가져온다.
            t = town.get(i-1); // (2, 10), (3, 20), ...
            System.out.println("t in town = " + t);

            // 트럭에 있는 짐들도 목록에 추가한다.
            for(Integer key: truck.keySet()) {
                if (t.containsKey(key)) {
                    System.out.println("truck has key? = " + key);
                    int amount = t.get(key) + truck.get(key);
                    t.put(key, amount);
                }
                else
                    t.put(key, truck.get(key));
            }

            System.out.println("t after add in truck = " + t);

            // 트럭을 비운다.
            truck.clear();

            // 마을 + 트럭 짐들의 목적지 배열
            List<Integer> keyL = new ArrayList<>(t.keySet());

            // 목적지들을 가까운 순 (오름차순)으로 정렬
            keyL.sort(Comparator.naturalOrder());

            // 목적지들에 대해 트럭에 담을 수 있을만큼 담기
            int cap = 0;
            for (Integer key: keyL) {
                int amount = t.get(key);
                if (cap + amount <= C) {
                    System.out.println("짐 싣기 key = " + key);
                    System.out.println("짐 싣기 amount = " + amount);
                    cap += amount;
                    truck.put(key, amount);
                }
                else { // 넘치면 가득 찰 만큼만 트럭에 담음
                    System.out.println("가득 차서 담을 만큼만 담기 key = " + key);
                    System.out.println("담은 양: C-cap = " + (C - cap));
                    truck.put(key, C - cap);
                    break;
                }
            }

        }

        System.out.println("count = " + count);
    }

    public static void main(String[] args) throws Exception {
        init();
        calc();
    }

}
