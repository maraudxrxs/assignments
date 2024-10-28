import java.io.*;
import java.util.*;

class almostUnionFind{
    public int[] p;
    public HashMap<Integer, ArrayList<Integer>> sumAndSize;

    int rep(int a, int n){
        return n + a;
    }

    public almostUnionFind(int n){
        p = new int[2*n + 1];
        for(int k = 1; k <= n; k++){
            p[rep(k,n)] = rep(k,n);
            p[k] = rep(k,n);
        }
    }
    
    int find(int k){
        int current = k;
        ArrayList<Integer> allNodes = new ArrayList<>(null);
        while(p[current] != current){
            allNodes.add(current);
            current = p[current];
        }

        for(int nodes : allNodes){
            p[nodes] = current;
        }

        return current;
    }

    void union(int i, int j){
        int x = find(i);
        int y = find(j);

        if(x == y){
            return;
        } else{
            ArrayList<Integer> ss1 = sumAndSize.get(x);
            ArrayList<Integer> ss2 = sumAndSize.get(y);

            ArrayList<Integer> lst = new ArrayList<>(null);
            lst.add(ss1.get(0) + ss2.get(0));
            lst.add(ss1.get(1) + ss2.get(1));

            sumAndSize.put(x, lst);
            p[y] = x;

            sumAndSize.remove(y);
        }
    }

    void move(int i, int j){
        int x = find(i);
        int y = find(j);

        if(x == y){
            return;
        } else{
            ArrayList<Integer> ss1 = sumAndSize.get(x);
            ArrayList<Integer> ss2 = sumAndSize.get(y);

            ArrayList<Integer> lst1 = new ArrayList<>(null);
            ArrayList<Integer> lst2 = new ArrayList<>(null);

            lst1.add(ss1.get(0) - 1);
            lst1.add(ss1.get(1) - i);

            lst2.add(ss2.get(0) + 1);
            lst2.add(ss2.get(1) + i);

            sumAndSize.put(x,lst1);
            sumAndSize.put(y,lst2);
        }
    }

    int size(int i){
        int x = find(i);
        return sumAndSize.get(x).get(0);
    }

    int sum(int i){
        int x = find(i);
        return sumAndSize.get(x).get(1);
    }

}

public class almostunion{
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        try {
            String[] firstLine = br.readLine().split(" ");
            int n = Integer.parseInt(firstLine[0]); // number of integers
            int m = Integer.parseInt(firstLine[1]); // number of commands
            almostUnionFind allSets = new almostUnionFind(n);

            for (int k = 0; k < m; k++) {
                String[] input = br.readLine().split(" ");
                int operation = Integer.parseInt(input[0]);
                if (operation == 1) {
                    int p = Integer.parseInt(input[1]);
                    int q = Integer.parseInt(input[2]);
                    union(p,q);

                } else if (operation == 2) {
                   int p = Integer.parseInt(input[1]);
                   int q = Integer.parseInt(input[2]);
                   move(p,q);
                
                } else {
                    int p = Integer.parseInt(input[1]);
                    int Size = size(p);
                    int Sum = sum(p);
                    pw.println(Size + " " + Sum);
                }
            }
    

            pw.flush();
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}

