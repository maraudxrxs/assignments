import java.io.*;

public class t9spelling {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(br.readLine()); // scan number of cases
        String[] arr = new String[256]; // hard code character to key press dictionary/array
        arr[' '] = "0";
        arr['a'] = "2";
        arr['b'] = "22";
        arr['c'] = "222";
        arr['d'] = "3";
        arr['e'] = "33";
        arr['f'] = "333";
        arr['g'] = "4";
        arr['h'] = "44";
        arr['i'] = "444";
        arr['j'] = "5";
        arr['k'] = "55";
        arr['l'] = "555";
        arr['m'] = "6";
        arr['n'] = "66";
        arr['o'] = "666";
        arr['p'] = "7";
        arr['q'] = "77";
        arr['r'] = "777";
        arr['s'] = "7777";
        arr['t'] = "8";
        arr['u'] = "88";
        arr['v'] = "888";
        arr['w'] = "9";
        arr['x'] = "99";
        arr['y'] = "999";
        arr['z'] = "9999";

        for (int i = 1; i <= cases; i++) { // loop through each case to find keypress for each case
            String input = br.readLine();
            int length = input.length(); // length of the input
            StringBuilder output = new StringBuilder();
            char[] array = new char[length]; // array to store the number for each character, to detect if there's any character that uses the same number to type

            for (int j = 0; j < length; j++) { // loop through the string to find the key press

                char c = input.charAt(j);

                if (j > 0 && array[j - 1] == arr[c].charAt(0)) {
                    output.append(" "); // if got the same number before, add a space
                }

                if (c == ' ') {
                    output.append('0');
                } else {
                    output.append(arr[c]);
                }
                array[j] = arr[c].charAt(0);
            }

            System.out.println("Case #" + i + ": " + output.toString());
        }
    }
}
