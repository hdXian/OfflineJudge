package ct_exercise.tree_09;

import java.util.*;
import java.io.*;
import java.math.*;

public class TreeCircuit_366 {

    static void preOrder(int n, int[] tree, List<Integer> result) {
        if (n > tree.length) return;

        // V L R
        result.add(tree[n-1]);
        preOrder(n*2, tree, result);
        preOrder(n*2+1, tree, result);
    }

    static void inOrder(int n, int[] tree, List<Integer> result) {
        if (n > tree.length) return;

        // L V R
        inOrder(n*2, tree, result);
        result.add(tree[n-1]);
        inOrder(n*2+1, tree, result);
    }

    static void postOrder(int n, int[] tree, List<Integer> result) {
        if (n > tree.length) return;

        // L R V
        postOrder(n*2, tree, result);
        postOrder(n*2+1, tree, result);
        result.add(tree[n-1]);
    }

    static List<String> solution(int[] nodes) {
        List<Integer> pre_result = new ArrayList<>();
        List<Integer> in_result = new ArrayList<>();
        List<Integer> post_result = new ArrayList<>();

        preOrder(1, nodes, pre_result);
        inOrder(1, nodes, in_result);
        postOrder(1, nodes, post_result);

        List<String> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for(int i: pre_result) sb.append(i).append(' ');
        sb.replace(sb.length()-1, sb.length()-1, "\"");
        result.add(sb.toString());

        sb = new StringBuilder();
        sb.append("\"");
        for(int i: in_result) sb.append(i).append(' ');
        sb.replace(sb.length()-1, sb.length()-1, "\"");
        result.add(sb.toString());

        sb = new StringBuilder();
        sb.append("\"");
        for(int i: post_result) sb.append(i).append(' ');
        sb.replace(sb.length()-1, sb.length()-1, "\"");
        result.add(sb.toString());

        return result;
    }

    public static void main(String[] args) throws Exception {
        int[] nodes = {1, 2, 3, 4, 5, 6, 7};
        List<String> result = solution(nodes);
        System.out.println(result);
    }

}
