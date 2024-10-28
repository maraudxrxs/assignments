import java.util.*;
import java.io.*;

public class nicknames {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine()); // number of names
        AVLTree allNames = new AVLTree();

        for (int i = 0; i < n; i++) { // load in all the names into the AVL Tree
            String name = br.readLine();
            allNames.insert(name);
        }

        int c = Integer.parseInt(br.readLine()); // number of nicknames
        for (int j = 0; j < c; j++) {
            String nickname = br.readLine();
            String start = allNames.findFirst(nickname, allNames.root);
            StringBuilder sb = new StringBuilder(nickname);
            sb.append("zzzzzzzz");
            String newNickname = sb.toString();
            String last = allNames.findLast(newNickname, allNames.root);
            if(start == null || last == null){
                pw.println(0);
                continue;
            }
            int index1 = allNames.rank(start);
            int index2 = allNames.rank(last);
            pw.println(start + " " + last);
            pw.println(index2 - index1);
        }
        pw.flush();
        br.close();
    }
}

class Node {
    String data;
    Node left, right;
    int height;
    int size;

    public Node(String data) {
        this.data = data;
        this.height = 1;
        this.size = 1;
    }
}

class AVLTree {
    public Node root;

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    public void insert(String data) {
        root = insert(root, data);
    }

    private Node insert(Node node, String data) {
        if (node == null) {
            return new Node(data);
        }

        int cmp = data.compareTo(node.data);

        if (cmp < 0) {
            node.left = insert(node.left, data);
        } else if (cmp > 0) {
            node.right = insert(node.right, data);
        } else {
            return node; // Duplicate data not allowed
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        int balance = getBalance(node);

        // Left heavy
        if (balance > 1) {
            if (data.compareTo(node.left.data) < 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        // Right heavy
        if (balance < -1) {
            if (data.compareTo(node.right.data) > 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    public int rank(String data) {
        return rank(root, data);
    }

    private int rank(Node node, String data) {
        if (node == null) return 0;

        int cmp = data.compareTo(node.data);

        if (cmp < 0) {
            return rank(node.left, data);
        } else if (cmp > 0) {
            return size(node.left) + 1 + rank(node.right, data);
        } else {
            return size(node.left) + 1;
        }
    }

    private int height(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        y.size = size(y.left) + size(y.right) + 1;
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        x.size = size(x.left) + size(x.right) + 1;
        y.size = size(y.left) + size(y.right) + 1;

        return y;
    }
    
    public String findFirst(String x, Node y) { // Find the biggest r such that x > r
    String first = null;
    while(y != null){
        int cmp = x.compareTo(y);
        if(cmp > 0){
            first = y.data;
            y = y.right;
        } else{
            if(cmp == 0){
                first = y.data;
            }
            y = y.left;
        }
    }
    return first;
}

public String findLast(String x, Node y) { // Find the smallest r such that x < r
    String last = null;

    while (y != null) {
        int cmp = x.compareTo(y.data);
        if (cmp < 0) {
            last = y.data;
            y = y.left;
        } else {
            if(cmp == 0){
                last = y.data;
            }
            y = y.right;
        }
    }

    return last;
}

}
