import java.util.*;
import java.io.*;

public class weakvertices {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        while (true) {
            String line = br.readLine();
            if (line.equals("-1")) {
                break;
            }

            int n = Integer.parseInt(line);
            ArrayList<Integer> weakVertices = new ArrayList<>();
            HashMap<Integer, ArrayList<Integer>> AL = new HashMap<>(); // adjacency lists
            HashMap<Integer, String[]> AJ = new HashMap<>(); // adjacency matrix

            for (int i = 0; i < n; i++) {
                String[] sRow = br.readLine().split(" ");
                ArrayList<Integer> intRow = new ArrayList<>();
                for (String items : sRow) {
                    int edge = Integer.parseInt(items);
                    if (edge == 1) {
                        intRow.add(edge);
                    }
                }
                AL.put(i, intRow);
                AJ.put(i, sRow);
            }

            for (int i = 0; i < n; i++) {
                int edges = AL.get(i).size();
                if (edges < 2) {
                    weakVertices.add(i);
                } else {
                    boolean isWeak = true;
                    ArrayList<Integer> allEdges = AL.get(i);
                    for (int j = 0; j < allEdges.size(); j++) {
                        String[] strRow = AJ.get(allEdges.get(j));
                        for (int k = 0; k < allEdges.size(); k++) { // check if it's connected to the vertices of i
                            int wt = Integer.parseInt(strRow[allEdges.get(k)]);
                            if (wt == 1) {
                                isWeak = false;
                                break;
                            }
                        }
                        if (!isWeak) {
                            break;
                        }
                    }

                    if (isWeak) {
                        weakVertices.add(i);
                    }
                }
            }
            for (int vertice : weakVertices) {
                pw.print(vertice + " ");
            }

            pw.flush();
        }

        br.close();
    }
}
