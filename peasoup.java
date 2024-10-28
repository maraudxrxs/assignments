import java.util.Scanner;

public class peasoup {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int total_rest = 0; // total number of restaurants with pancakes and pea soup
        
        for(int i = 0; i < n; i++){ // scan through each restaurants
            int k = sc.nextInt(); // number of menu items
            sc.nextLine();
            
            String name = sc.nextLine(); // find name of restaurant
            boolean isPancake = false;
            boolean isPeaSoup = false;
            for (int j = 0; j < k; j++){
                String item = sc.nextLine();
                if (item.equals("pancakes")){
                    isPancake = true;
                } else if (item.equals("pea soup")){
                    isPeaSoup = true;
                }
            }

            if (isPancake == true && isPeaSoup == true){
                System.out.println(name); // print restaurant name
                total_rest++;
                break;
            }
        } 

        if (total_rest == 0){ // if no restaurant fulfill the conditions
            System.out.println("Anywhere is fine I guess"); // return anywhere is fine i guess
        }
    }
    
}
