//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import java.util.*;
import Phase2.GameUtility;
import Phase2.Player;
import Phase2.Weapon;
import Phase2.Map;
import java.io.*;

public class Combat{
    public static int InitiateCombat(Player player1,Player player2){ //returns 1 if player 1 first, returns 2 if player 2 first in PvP.
        int player2roll=0;
        int player1roll=0;
        while(player1roll==player2roll){
            player1roll = GameUtility.rollDice("d20");
            player2roll = GameUtility.rollDice("d20");
            player1roll = player1roll+Creature.DEX_mod(player1.DEX);
            player2roll = player2roll+Creature.DEX_mod(player2.DEX);
        }
        if(player1roll>player2roll){
            return 1;
        }
        else{
            return 2;
        }
    }

    public static boolean isDisarmed(Player attacker,Player target){
        int STR_modifier1=0;
        if(attacker.STR<5){
            STR_modifier1 = attacker.STR-1;
        }
        if(attacker.STR>=5){
            STR_modifier1 = attacker.STR+1;
        }
        int STR_modifier2=0;
        if(target.STR<5){
            STR_modifier2 = target.STR-1;
        }
        if(target.STR>=5){
            STR_modifier2 = target.STR+1;
        }
        int attacker_roll = GameUtility.rollDice("d20")+STR_modifier1;
        int target_roll = GameUtility.rollDice("d20")+STR_modifier2;
        if(attacker_roll>target_roll){
            return true;
        }
        else{
            return false;
        }
    }

}





