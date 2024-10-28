import java.util.*;
import java.io.*;

public class islands3 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }

            String[] firstLine = line.split(" ");
            int r = Integer.parseInt(firstLine[0]); //number of rows
            int c = Integer.parseInt(firstLine[1]); //number of columns
            char[][] grid = new char[r][c]; //initialise adjacency matrix
            int[][] visited = new int[r][c]; //initialise visited column

            for(int i = 0; i < r; i++){
                String input = br.readLine();
                char[] row = new char[c];
                for (int j = 0; j < c; j++){
                    row[j] = input.charAt(j);
                }
                grid[i] = row;
            }

            int result = 0;
            for(int i = 0; i < r; i++){
                for(int j = 0; j < c; j++){
                    if(grid[i][j] == 'L' && visited[i][j] == 0){
                        result++;
                        dfs(grid, visited, i, j);
                    }
                }
            }

            pw.println(result);
            pw.flush();
        }
        br.close();
    }

    static void dfs(char[][] grid, int[][] visited, int row, int column){
        int totalRows = visited.length;
        int totalColumns = visited[0].length;
        
        if(row < 0|| column < 0 || row >= totalRows || column >= totalColumns || grid[row][column] == 'W'){ //either out of bounds or hit water then return
            return;
        } else if(visited[row][column] == 1){
            return;
        }

        visited[row][column] = 1;

        dfs(grid, visited, row + 1, column);
        dfs(grid, visited, row - 1, column);
        dfs(grid, visited, row, column + 1);
        dfs(grid, visited, row, column - 1);
    }
}
