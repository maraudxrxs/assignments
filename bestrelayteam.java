import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Runner {
    public String name;
    public double firstleg;
    public double secondleg;

    public Runner(String name, double firstleg, double secondleg) {
        this.name = name;
        this.firstleg = firstleg;
        this.secondleg = secondleg;
    }
}

public class bestrelayteam {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int runners = Integer.parseInt(br.readLine()); // Number of runners
        ArrayList<Runner> allRunners = new ArrayList<Runner>();

        for (int i = 0; i < runners; i++) {
            String input = br.readLine();
            String[] properties = input.split(" ");
            String runner_name = properties[0];
            double first_leg = Double.parseDouble(properties[1]);
            double second_leg = Double.parseDouble(properties[2]);
            allRunners.add(new Runner(runner_name, first_leg, second_leg));
        }

        Collections.sort(allRunners, Comparator.comparing(x -> x.secondleg));

        // Compare the timings now
        double finalTiming = 80.00;
        int[] lineUp = new int[4];

        for (int i = 0; i < runners; i++) {
            double trial = 0.00;
            trial = trial + allRunners.get(i).firstleg;

            int[] runner_index = new int[4];
            runner_index[0] = i;
            int index = 1;
            for (int k = 0; k < runners; k++) {
                if (k != i) {
                    runner_index[index] = k;
                    trial = trial + allRunners.get(k).secondleg; // Fixed the indexing issue here
                    index++;
                }

                if (index == 4) {
                    break;
                }
            }

            if (trial < finalTiming) {
                finalTiming = trial;
                lineUp = runner_index;
            }
        }

        PrintWriter pw = new PrintWriter(System.out);
        pw.println(finalTiming);

        for (int j = 0; j < 4; j++) {
            pw.println(allRunners.get(lineUp[j]).name); // Fixed the printing issue here
        }

        pw.flush();
        br.close();
    }
}
