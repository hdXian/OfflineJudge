package prob_202503.week4.minmax_2357;

import java.io.*;
import java.util.*;
import java.math.*;

// 부분 합을 구하는 세그먼트 트리 (기본)
public class Main {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int N, M;
    public static int[] arr;
    public static int[] seg_tree;

    public static void init() throws Exception {
        StringTokenizer tkn = new StringTokenizer(reader.readLine());
        N = Integer.parseInt(tkn.nextToken()); // 숫자 개수 (1~10만), 각 정수는 1~10억
        M = Integer.parseInt(tkn.nextToken()); // 쌍 개수 (1~10만)

        arr = new int[N+1]; // 1부터 시작
        for (int i=1; i<=N; i++) {
            tkn = new StringTokenizer(reader.readLine());
            arr[i] = Integer.parseInt(tkn.nextToken());
        }

        // 크기가 n인 배열에 대하여
        // 세그먼트 트리의 높이 h -> log2(n)
        // 필요한 트리 노드 개수 -> 2^(h+1)
        double log2 = Math.ceil(Math.log(N) / Math.log(2)); // 세그먼트 트리 높이
        int nodes = (int) Math.pow(2, log2+1);
        seg_tree = new int[nodes];
        Arrays.fill(seg_tree, 0);
    }

    public static void main(String[] args) throws Exception {
        init();
        makeSegTree(1, 1, N);
        printSegTree();
        int testSum = findSum(1, 1, N, 3, 5);
        System.out.println("testSum = " + testSum);
    }

    public static int makeSegTree(int nodeNum, int start, int end) {
        // 리프 노드일 경우 노드 번호에 해당 배열 값을 저장하고 그 값을 리턴
        if (start == end) {
            seg_tree[nodeNum] = arr[start];
            return seg_tree[nodeNum];
        }

        // 아니면 재귀호출 + 구간 합 리스트에 저장
        int mid = (start + end) / 2;
        int left_sum = makeSegTree(nodeNum*2, start, mid);
        int right_sum = makeSegTree(nodeNum*2+1, mid+1, end);
        seg_tree[nodeNum] = left_sum + right_sum;
        return seg_tree[nodeNum];
    }

    public static int findSum(int nodeNum, int start, int end, int left, int right) {
        // 찾는 범위를 벗어난 경우 -> 더 검색하지 않음.
        if (start > right || end < left)
            return 0;

        // 범위에 포함된 경우 -> 해당 노드 값 리턴
        if (start >= left && end <= right)
            return seg_tree[nodeNum];

        // 범위가 겹친 경우 -> 더 검색을 해야 함. 찾다보면 범위에 맞는 노드가 등장. 아예 없으면 0 리턴됨.
        int mid = (start + end) / 2;
        int find_left = findSum(nodeNum*2, start, mid, left, right);
        int find_right = findSum(nodeNum*2+1, mid+1, end, left, right);
        return find_left + find_right;
    }

    public static void printSegTree() {
        System.out.println("printSegTree()");
        for(int i: seg_tree)
            System.out.print(i + " ");
        System.out.println();
    }

}
