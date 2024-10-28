import java.util.*;
import java.io.*;

class IntegerTriple implements Comparable<IntegerTriple>{
    public int u;
    public int v;
    public int w;

    public IntegerTriple(int start, int end, int weight){
        this.u = start;
        this.v = end;
        this.w = weight;
    }

    @Override
    public int compareTo(IntegerTriple other) {
        return Integer.compare(this.w, other.w);
    }
}

public class lostmap {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine()); //num of villages
        int[] visited = new int[n + 1]; //initialise visited array
        int[][] adjMatrix = new int[n + 1][n + 1]; //initialise adjacency matrix

        for(int i = 1; i <= n; i++){
            String[] row = br.readLine().split(" ");
            for(int j = 1; j <= n; j++){
                adjMatrix[i][j] = Integer.parseInt(row[j-1]);
            }
        }

        visited[1] = 1;

        PriorityQueue<IntegerTriple> pq = new PriorityQueue<>();
        //add neighbours of vertex 1 to pq
        for(int i = 1; i <= n; i++){
            if(adjMatrix[1][i] > 0){
                int weight = adjMatrix[1][i];
                pq.offer(new IntegerTriple(1, i, weight));
            }
        }

        while(!pq.isEmpty()){
            IntegerTriple edge = pq.poll();
            int u = edge.u;
            int v = edge.v;
            if(visited[v] == 0){
                pw.println(u + " " + v);
                visited[v] = 1;
                for(int i = 1; i <= n; i++){
                    if(adjMatrix[v][i] > 0 && visited[i] == 0){
                        int weight = adjMatrix[v][i];
                        pq.offer(new IntegerTriple(v, i, weight));
                    }
                }
            }
        }

        pw.flush();
        br.close();

    }
}
