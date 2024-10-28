import java.io.*;
import java.util.*;

class UnionFind {
    public int[] p;
    public HashMap<Integer, ArrayList<Integer>> sumAndSize;

    int head(int a, int b) {
        return a + b;
    }

    public UnionFind(int n) {
        p = new int[2 * n + 1];
        sumAndSize = new HashMap<>();

        for (int k = 1; k <= n; k++) {
            p[head(k, n)] = head(k, n);
            p[k] = head(k, n);
            ArrayList<Integer> lst = new ArrayList<>();
            lst.add(1);
            lst.add(k);
            sumAndSize.put(head(k, n), lst);
        }
    }

    int find(int k) {
        int current = k;
        ArrayList<Integer> children = new ArrayList<>();
        while (p[current] != current) {
            children.add(current);
            current = p[current];
        }

        for (int child : children) {
            p[child] = current;
        }

        return current;
    }

    boolean sameSet(int a, int b){
        int x = find(a);
        int y = find(b);

        return x == y;
    }

    void union(int i, int j) {
        int x = find(i);
        int y = find(j);
        
        if (!sameSet(i, j)) {
            ArrayList<Integer> lst = new ArrayList<>();
            lst.add(size(i) + size(j));
            lst.add(sum(i) + sum(j));

            sumAndSize.put(x, lst);
            p[y] = x;

            sumAndSize.remove(y);
        }
    }

    void move(int i, int j) {
        int x = find(i);
        int y = find(j);

        if (!sameSet(i, j)){
            ArrayList<Integer> lst1 = new ArrayList<>();
            ArrayList<Integer> lst2 = new ArrayList<>();

            lst1.add(size(i) - 1);
            lst1.add(sum(i) - i);

            lst2.add(size(j) + 1);
            lst2.add(sum(j) + i);

            sumAndSize.put(x, lst1);
            sumAndSize.put(y, lst2);
            
            p[i] = y;
        }
    }

    int size(int i) {
        int x = find(i);
        return sumAndSize.get(x).get(0);
    }

    int sum(int i) {
        int x = find(i);
        return sumAndSize.get(x).get(1);
    }
}

public class almostunion {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        while (true) {
            String line = br.readLine();
            if(line == null){
                break;
            }
            String[] firstLine = line.split(" ");
            int n = Integer.parseInt(firstLine[0]); // number of integers
            int m = Integer.parseInt(firstLine[1]); // number of commands
            UnionFind allSets = new UnionFind(n);

            for (int k = 0; k < m; k++) {
                String[] input = br.readLine().split(" ");
                int operation = Integer.parseInt(input[0]);
                if (operation == 1) {
                    int p = Integer.parseInt(input[1]);
                    int q = Integer.parseInt(input[2]);
                    allSets.union(p, q);
                } else if (operation == 2) {
                    int p = Integer.parseInt(input[1]);
                    int q = Integer.parseInt(input[2]);
                    allSets.move(p, q);
                } else {
                    int p = Integer.parseInt(input[1]);
                    int Size = allSets.size(p);
                    int Sum = allSets.sum(p);
                    pw.println(Size + " " + Sum);
                }
            }
        }
        pw.flush();
        br.close();
    }
}

