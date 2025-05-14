package ct_exercise.tree_09;

import java.io.*;
import java.util.*;
import java.math.*;

public class BinSearchTree_impl_369 {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            left = null;
            right= null;
        }
    }

    static class MyBinarySearchTree {
        private Node root;

        private Node insert(Node node, int val) {
            if (node == null) return new Node(val);

            if (node.val >= val) node.right = insert(node.right, val);
            else node.left = insert(node.left, val);
            return node;
        }

        private boolean search(Node node, int val) {
            if (node == null) return false;
            if (node.val == val) return true;

            if (node.val >= val) return search(node.right, val);
            else return search(node.left, val);
        }

        public MyBinarySearchTree() {
            this.root = null;
        }

        public void insertVal(int val) {
            this.root = insert(root, val);
        }

        public boolean find(int val) {
            return search(root, val);
        }

    }

    static boolean[] solution(int[] lst, int[] search_lst) {
        MyBinarySearchTree BST = new MyBinarySearchTree();
        for(int val: lst) BST.insertVal(val);

        List<Boolean> search_result = new ArrayList<>();
        for(int val: search_lst) search_result.add(BST.find(val));

        boolean[] result = new boolean[search_result.size()];
        int seq = 0;
        for(boolean res: search_result) result[seq++] = res;

        return result;
    }

    public static void main(String[] args) throws Exception {
        int[] lst1 = {5, 3, 8, 4, 2, 1, 7, 10};
        int[] sl1 = {1, 2, 5, 6};
        boolean[] result1 = solution(lst1, sl1);
        for(boolean r: result1) System.out.print(r + " ");
        System.out.println();

        int[] lst2 = {1, 3, 5, 7, 9};
        int[] sl2 = {2, 5, 6, 8, 10};
        boolean[] result2 = solution(lst2, sl2);
        for(boolean r: result2) System.out.print(r + " ");
        System.out.println();

    }

}
