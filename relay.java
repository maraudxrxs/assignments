import java.io.*;
import java.util.*;

class Sprinter { 
    public String sprinterName; 
    public double firstLeg; 
    public double secondLeg; 

    public Sprinter (String sprinterName, double firstLeg, double secondLeg) {
        this.sprinterName = sprinterName; 
        this.firstLeg = firstLeg; 
        this.secondLeg = secondLeg; 
    }
}

public class relay {
    public static void main(String[] args){
        Kattio io = new Kattio(System.in, System.out);

        // first line of input
        int numOfRunners = io.getInt();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        double trialTiming = 0; //dummy 
        double finalTiming = 50000; //dummy 
        ArrayList<Sprinter> teamArr = new ArrayList<Sprinter>();
        ArrayList<Integer> finalTeam = new ArrayList<Integer>();

        for (int i = 0; i< numOfRunners; i++) {  
            teamArr.add(new Sprinter(io.getWord(), io.getDouble(), io.getDouble()));
        }

        Collections.sort(teamArr, Comparator.comparing(x -> x.secondLeg));
            
        for (int i = 0; i < numOfRunners; i++) {
            ArrayList<Integer> trialTeam = new ArrayList<Integer>();
            trialTiming = teamArr.get(i).firstLeg; 
            trialTeam.add(i); 

            int counter = 0;
            for (int k = 0; k < numOfRunners; k++) {
                if (k != i) {
                    trialTeam.add(k);
                    counter++;
                    trialTiming += teamArr.get(k).secondLeg;
                } 

                if (counter == 3) break;
                                  
            }

            if (trialTiming < finalTiming) {
                finalTeam = trialTeam;
                finalTiming = trialTiming; 
            }
        }
        pw.println(finalTiming); 
        pw.println(teamArr.get(finalTeam.get(0)).sprinterName);
        pw.println(teamArr.get(finalTeam.get(1)).sprinterName);
        pw.println(teamArr.get(finalTeam.get(2)).sprinterName);
        pw.println(teamArr.get(finalTeam.get(3)).sprinterName);
        pw.flush();
        io.close();
    }
}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}
