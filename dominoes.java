import java.util.*;
import java.io.*;

class Graph{
    ArrayList<ArrayList<Integer>> adj, revAdj; //revAdj is for transpose graph
    boolean[] visited;

    public Graph(int n){
        adj = new ArrayList<>();
        revAdj = new ArrayList<>();
        visited = new boolean[n];
        for(int k = 0; k < n; k++){
            ArrayList<Integer> adjacent = new ArrayList<>();
            adj.add(adjacent);
            revAdj.add(adjacent);
        }
    }

    public void addEdge(int u, int v){
        adj.get(u - 1).add(v - 1); //original
        revAdj.get(v - 1).add(u - 1); //transpose
    }

    public dfs(int i, Stack<Integer> s){
        visited[i] = true;
        for(int neighbour : adj.get(i)){
            if(!visited[neighbour]){
                dfs(neighbour);
            }
        }
        s.push(i);
    }

    public dfs_scc(int i, ArrayList<Integer> scc){
        visited[i] = true;
        scc.add(i);
        for(int neighbour : revAdj.get(i)){
            dfs_scc(neighbour, scc);
        }
    }

    public void convertCycle(){
        Stack<Integer> stack = new Stack<>();
        int n = adj.size();
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                dfs(i, stack, adj);
            }
        }

        ArrayList<ArrayList<Integer>> sccs = new ArrayList<>();
        visited = new boolean[n];
        while(!stack.isEmpty()){
            int vertice = stack.pop();
            if(!visited[vertice]){
                ArrayList<Integer> scc = new ArrayList<>();
                dfs(vertice, scc);
                sccs.add(scc);
            }
        }

        ArrayList<ArrayList<Integer>> revised = new ArrayList<>();

        for(int i = 0; i < sccs.size(); i++){
            ArrayList<Integer> scc 
        }


    }




}

public class dominoes {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int cases = Integer.parseInt(br.readLine());

        for(int i = 0; i < cases; i++){
            String[] input = br.readLine().split(" ");
            int n = Integer.parseInt(input[0]); //number of dominoes
            int m = Integer.parseInt(input[1]); //number of edges
            Graph allDominos = new Graph(n); //initiate graph for dominos
            
            for(int j = 0; j < m; j++){
                String[] edge = br.readLine().split(" ");

            }
        }

        br.close();
    }
}
