import java.io.*;
import java.util.*;

class Card {
    public String card_type;
    public long cost_price;
    public long sell_price;
    public long costOfCombo;
    public int numOfCards;

    public Card(String cardType, long cost, long sale) {
        this.card_type = cardType;
        this.cost_price = cost;
        this.sell_price = sale;
        this.costOfCombo = 0;
        this.numOfCards = 0;
    }
}

public class cardtrading {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        String input = br.readLine();
        String[] ntkString = input.split(" ");
        long n = Long.valueOf(ntkString[0]); //initial no of cards on deck
        long t = Long.valueOf(ntkString[1]); //total card types
        long k = Long.valueOf(ntkString[2]); // total no of combos
        ArrayList<Card> allCards = new ArrayList<Card>();

        String[] deck = br.readLine().split(" ");
        int[] finalDeck = new int[(int) t + 1]; //create dictionary to put in the total number of cards for each card types
        for(String card : deck){
            int index = Integer.valueOf(card);
            finalDeck[index] = finalDeck[index] + 1; //if there's a card, add 1 to the total number of cards in the array
        }

        for (long i = 1; i <= t; i++) {
            String[] cost_rev = br.readLine().split(" "); //input for each card type and its cost and selling price
            allCards.add(new Card(String.valueOf(i), Long.valueOf(cost_rev[0]), Long.valueOf(cost_rev[1])));
        }

        for (Card cards : allCards) {
            Integer cardtype = Integer.valueOf(cards.card_type);
            //use the dictionary to update the total num of cards in the object property
            cards.numOfCards = finalDeck[cardtype.intValue()];

            if (cards.numOfCards == 2) { //if got 2 cards on deck -> cost of combo is the price of selling both cards
                cards.costOfCombo = cards.sell_price * 2;
            } else if (cards.numOfCards == 1) { //if got 1 card on deck -> cost of combo is the price of selling 1 card and buying the other card
                cards.costOfCombo = cards.sell_price + cards.cost_price;
            } else if (cards.numOfCards == 0) { //if got no card on deck -> cost of combo is the price of buying 2 cards
                cards.costOfCombo = cards.cost_price * 2;
            }
        }

        Collections.sort(allCards, Comparator.comparing(x -> x.costOfCombo)); //sort arraylist based on the cost of combo

        long total_cost = 0; //dummy variables to store the total sales and total costs
        long total_sales = 0;
        long counter = 0;

        for (Card cards : allCards) {
            if (cards.numOfCards == 1 && counter < k) { //increment total cost by cost of selling of card if counter < k
                total_cost = total_cost + cards.cost_price;
            } else if (cards.numOfCards == 0 && counter < k) {
                total_cost = total_cost + cards.cost_price * 2;
            } else if (cards.numOfCards > 0 && counter >= k) { //increment total sales by buying price of card if counter >= k
                total_sales = total_sales + cards.sell_price * cards.numOfCards;
            }
            counter++;
        }

        pw.println(total_sales - total_cost);
        pw.flush();
        br.close();
    }
}
