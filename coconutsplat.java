import java.util.*;
import java.io.*;

class Hand {
    int playerNum;
    String state;

    public Hand(int num, String handState) {
        this.playerNum = num;
        this.state = handState;
    }
}

public class coconutsplat {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        String[] numbers = br.readLine().split(" ");
        int s = Integer.valueOf(numbers[0]); // no of syllables
        int n = Integer.valueOf(numbers[1]); // no of players

        ArrayList<Hand> allPlayers = new ArrayList<Hand>();
        for (int i = 1; i <= n; i++) {
            allPlayers.add(new Hand(i, "folded")); // Use 'new Hand'
        }
        int[] playerState = new int[n + 1];
        int alive = n; // keep count of how many players are still in the game
        int cur = 0; // keep track of first and last player
        int winner = 0; // dummy variable -> to contain the index of the winner

        while (alive > 1) {
            int totalPlayers = allPlayers.size();
            cur = (cur + s - 1) % totalPlayers;
            Hand hand = allPlayers.get(cur); // Added type 'Hand' here

            if (hand.state.equals("folded")) { // Use 'equals' for String comparison
                hand.state = "fist";
                allPlayers.add(cur + 1, new Hand(hand.playerNum, "fist")); // Use 'new Hand'
            } else if (hand.state.equals("fist")) { // Use 'equals' for String comparison
                hand.state = "palm_down";
                cur++;
            } else if (hand.state.equals("palm_down")) { // Use 'equals' for String comparison
                int num = hand.playerNum;
                playerState[num] = playerState[num] + 1;
                if (playerState[num] == 2) {
                    alive--;
                }
                allPlayers.remove(hand);
            }
        }

        winner = allPlayers.get(0).playerNum;
        pw.println(winner);
        pw.flush();
        br.close();
    }
}
