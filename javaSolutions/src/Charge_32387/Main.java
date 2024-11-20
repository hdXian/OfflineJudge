package Charge_32387;

import java.util.*;
import java.io.*;

public class Main {

    public static int N;
    public static int Q;

    public static boolean[] ports;
    public static int[] devices;

//    public static Node rootNode;

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken());
        Q = Integer.parseInt(tkn.nextToken());

        ports = new boolean[N+1]; // 포트 (충전기가 꽂혀 있는지 여부를 저장)
        devices = new int[N+1]; // 몇 번째 행동에서 꽂힌 기기인지 기억
        Arrays.fill(ports, false);
        Arrays.fill(devices, 0);

//        // 루트 노드 생성
//        rootNode = new Node(0);
//        // 포트 트리 생성
//        for(int x=1; x<=N; x++) {
//            rootNode.put(x);
//        }

        StringBuilder sb = new StringBuilder();

        int action;
        int i;
        int res;

        for(int x=1; x<=Q; x++) {
            tkn = new StringTokenizer(reader.readLine());
            action = Integer.parseInt(tkn.nextToken());
            i = Integer.parseInt(tkn.nextToken());

            // 1번 행동
            if (action == 1) {
                res = action1(x, i);
                sb.append(res).append('\n');
            }
            // 2번 행동
            else if (action == 2) {
                res = action2(i);
                sb.append(res).append('\n');
            }

        }

        System.out.println(sb);

    }

    // 1번 행동: 출력이 i인 기기를 꽂으려고 시도
    private static int action1(int seq, int i) {
        // 해당 포트에 기기가 꽂혀있다면
        if (ports[i]) {

            for(int k=i; k<=N; k++) {

            }


        }
        // 포트가 비어있다면
        else {
            ports[i] = true;
            devices[i] = seq;
            return i;
        }
    }

    // 2번 행동: i번 포트의 기기를 제거
    private static int action2(int i) {
        // 해당 포트에 기기가 꽂혀 있다면
        if (ports[i]) {
            ports[i] = false;
            return devices[i];
        }
        // 포트가 비어있다면
        else {
            return -1;
        }
    }

    private static void removeNode(Node current, Node parent, int n) {

        // current가 null임 (제거하려는 노드가 최종적으로 없음)
        if (current == null)
            return;

        // 일단 n에 해당하는 노드를 찾는다.
        int tmp = current.val;

        // 큰 놈은 오른쪽
        if (tmp < n) {
            removeNode(current.right, current, n);
        }
        // 작은 놈은 왼쪽
        else if (tmp > n) {
            removeNode(current.left, current, n);
        }
        // 이 노드가 지우려는 노드인 경우 (tmp == n)
        else {

            // 이게 리프 노드인 경우 -> 해당 노드만 제거
            if (current.left == null && current.right == null) {
                if (parent == null)
                    return;
                if (current == parent.left)
                    parent.left = null;
                else
                    parent.right = null;
            }
            // 자식 노드가 하나뿐인 경우 -> 자식 노드를 현재 노드의 자리로 이동
            else if (current.left == null || current.right == null) {
                Node child = (current.left == null) ? current.right : current.left;

                if (current == parent.left)
                    parent.left = child;
                else
                    parent.right = child;

            }
            // 자식 노드가 둘인 경우
            else {
                // 왼쪽 중에 가장 큰 놈을 가져오자. (왼쪽 서브트리에서 끝까지 오른쪽으로 가면 그중 가장 큰 놈임)
                // 이 노드의 값을 가져온 값으로 대체하고, 가져온 값의 원래 노드는 제거
                Node maxSubNode = current.left;

                while (maxSubNode.right != null) {
                    maxSubNode = maxSubNode.right;
                }

                current.val = maxSubNode.val;

                // 기존 maxSubNode는 제거
                removeNode(current, parent, maxSubNode.val);
            }
        }

    }

    // 이걸로 가능한 포트들을 관리해야 할 것 같은데.
    // val이 포트 번호인거임.
    static class Node {
        public Node left;
        public Node right;
        public int val;

        public Node(int val) {
            this.val = val;
        }

        // 재귀 너무 깊으면 반복문으로 전환 고려
        public Node find(int n) {
            if (val == n) {
                return this;
            }
            // 자기보다 큰 수는 오른쪽으로 검색
            else if (val < n) {
                if (right == null)
                    return null; // 오른쪽 노드 더 없으면 false
                else
                    return right.find(n); // 있으면 오른쪽 노드로 진행
            }
            // 자기보다 작은 수는 왼쪽으로 검색
            else {
                if (left == null)
                    return null;
                else
                    return left.find(n);
            }
        }

        // 재귀 너무 깊으면 반복문으로 전환 고려
        public void put(int n) {
            // 자기보다 크거나 같으면 오른쪽에 삽입
            if (val <= n) {
                if (right == null)
                    right = new Node(n);
                else
                    right.put(n);
            }
            // 자기보다 작으면 왼쪽에 삽입
            else {
                if (left == null)
                    left = new Node(n);
                else
                    left.put(n);
            }
        }

    }

}
