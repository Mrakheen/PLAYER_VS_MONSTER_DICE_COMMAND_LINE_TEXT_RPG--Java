//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameUtility{
    public GameUtility(){} //Default constructor

    public static int rollDice(String input){ 
        int NUM, DICE;
        String user_input = input;
        if (user_input.length() >= 2) {
            Pattern pattern1 = Pattern.compile("d[1-9]", Pattern.CASE_INSENSITIVE);
            Matcher matcher1 = pattern1.matcher(user_input);
            boolean matchFound1 = matcher1.find();
            if(matchFound1){
                String[] arr4 = user_input.split("d", 2);
                if (arr4[0].equals("")) {
                    NUM = 1;
                }
                else{
                    NUM = Integer.parseInt(arr4[0]);
                }
                DICE = Integer.parseInt(arr4[1]);
                DICE = DICE+1;
                int randomout2 = 0;
                Random random2 = new Random();
                while(randomout2 == 0){
                    randomout2 = random2.nextInt(DICE);
                }
                int output2 = (NUM*randomout2);
                return output2;
    
            }
            else {
                System.out.println("Invalid input!!");
                return 0;
            }
        }
        else {
            System.out.println("Invalid input!!");
            return 0;
        }
    }

}






