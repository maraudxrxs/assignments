import java.io.*;
import java.util.*;

class NameComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        String s1 = str1.substring(0, 2);
        String s2 = str2.substring(0, 2);

        return s1.compareTo(s2);
    }
}

public class SortOfSorting {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        while (true) {
            int numofNames = Integer.parseInt(br.readLine());

            if (numofNames == 0) {
                break;
            }

            String[] allNames = new String[numofNames];
            for (int j = 0; j < numofNames; j++) {
                allNames[j] = br.readLine();
            }

            Arrays.sort(allNames, new NameComparator());

            for (String name : allNames) {
                pw.println(name);
            }

            pw.println();
        }

        pw.flush();
        br.close();
    }
}
