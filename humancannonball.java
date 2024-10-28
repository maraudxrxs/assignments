import java.util.*;
import java.io.*;

import static java.lang.Math.*;

class Vertex {
    String type;
    int num;
    double x;
    double y;

    public Vertex(int n, String t, double i, double j) {
        this.type = t;
        this.num = n;
        this.x = i;
        this.y = j;
    }
}

public class humancannonball {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        ArrayList<Vertex> vertices = new ArrayList<>();

        for (int k = 0; k < 2; k++) { // to read start and end coordinates
            String[] line = br.readLine().split(" ");
            double i = Double.parseDouble(line[0]);
            double j = Double.parseDouble(line[1]);
            vertices.add(new Vertex(k, "not cannon", i, j));
        }

        int cannons = Integer.parseInt(br.readLine());
        for (int k = 0; k < cannons; k++) {// add in cannons for vertices list
            String[] line = br.readLine().split(" ");
            double i = Double.parseDouble(line[0]);
            double j = Double.parseDouble(line[1]);
            vertices.add(new Vertex(k + 2, "cannon", i, j));
        }

        double[][] adjMatrix = new double[cannons + 2][cannons + 2];
        for (int k = 0; k < vertices.size(); k++) {
            for (int l = 0; l < vertices.size(); l++) {
                Vertex vertex1 = vertices.get(k);
                Vertex vertex2 = vertices.get(l);
                double dist = sqrt(pow(vertex1.x - vertex2.x, 2) + pow(vertex1.y - vertex2.y, 2));
                double time1 = dist / 5;
                double time2 = Double.MAX_VALUE;
                if (vertex1.type.equals("cannon")) {
                    time2 = 2 + (abs(dist - 50) / 5);
                }
                adjMatrix[k][l] = min(time1, time2);
            }
        }

        double[] distance = new double[cannons + 2];
        Arrays.fill(distance, Double.MAX_VALUE);
        distance[0] = 0.0;
        for (int i = 1; i < vertices.size(); i++) { 
            for (int u = 0; u < vertices.size(); u++) { // for v
                for(int v = 0; v < vertices.size(); v++){
                    if (distance[u] + adjMatrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + adjMatrix[u][v];
                }
                }
            }
        }

        pw.println(distance[1]);
        pw.flush();
        br.close();
    }
}
