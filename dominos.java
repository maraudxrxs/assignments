import java.io.*;
import java.util.*;

class Graph{
    int vertices;
    ArrayList<ArrayList<Integer>> adj;
    int[] visited;
    int[] p;
    ArrayList<Integer> toposort;

    public Graph(int n){ //initialise graph
        vertices = n;
        adj = new ArrayList<>();
        visited = new int[vertices];
        p = new int[vertices];
        Arrays.fill(p, -1);
        toposort = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
                adj.add(new ArrayList<>());
            }
    }

    public void addEdge(int u, int v){ //add all the edges in graph
        adj.get(u).add(v);
    }

    public void dfsToposort(int u){
        visited[u] = 1;
        ArrayList<Integer> neighbours = adj.get(u);
        for(int neighbour : neighbours){
            if(visited[neighbour] == 0){
                p[neighbour] = u;
                dfsToposort(neighbour);
            }
        }
        toposort.add(u);
    }

    public void dfsRec(int u){
        visited[u] = 1;
        ArrayList<Integer> neighbours = adj.get(u);
        for(int neighbour : neighbours){
            if(visited[neighbour] == 0){
                dfsRec(neighbour);
            }
        }

    }

    public int kosaraju(){
        for(int i = 0; i < vertices; i++){
            if(visited[i] == 0){
                dfsToposort(i);
            }
        }

        Arrays.fill(visited, 0);
        int totalSCCs = 0;
        for(int i = vertices - 1; i >= 0 ; i--){
            if(visited[toposort.get(i)] == 0){
                dfsRec(toposort.get(i));
                totalSCCs++;
            }
        }
        return totalSCCs;
    }
    
}

public class dominos {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int cases = Integer.parseInt(br.readLine());

        for(int c = 0; c < cases; c++){
            String[] input = br.readLine().split(" ");
            int n = Integer.parseInt(input[0]); //num of dominoes
            int m = Integer.parseInt(input[1]); //num of lines after
            Graph allDominos = new Graph(n);

            for (int i = 0; i < m; i++) {
                String[] edges = br.readLine().split(" ");
                int u = Integer.parseInt(edges[0]);
                int v = Integer.parseInt(edges[1]);
                allDominos.addEdge(u - 1, v - 1);
            }

            int knockDowns = allDominos.kosaraju();
            pw.println(knockDowns);
            pw.flush();
        }
        br.close();
    }
}
