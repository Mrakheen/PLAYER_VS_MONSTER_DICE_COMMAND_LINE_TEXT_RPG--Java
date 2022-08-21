//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.GameUtility;
import java.util.*;
import java.io.*;
import java.lang.*;

public abstract class Creature{
    public String creatureName;
    public String creationDate;
    public int HP, STR, DEX, CON, AC;
    public int rollinitiative;
    public Map map;

    public Creature(){
        int player1roll=0;
        player1roll = GameUtility.rollDice("d20");
        player1roll = player1roll+Creature.DEX_mod(this.DEX);
        this.rollinitiative = player1roll;
        this.map = new Map();
    }

    public abstract int attack(Creature creature_target);

    public static void takeDamage(int inputDamage, Creature obj){
        if((obj.HP - inputDamage)>=0 && inputDamage>0){
            obj.HP = obj.HP - inputDamage;
        }
        else if((obj.HP - inputDamage)<0 && inputDamage>0){
            obj.HP = 0;
        }
    }

    public static int CON_mod(int CONval){
        int CON_modifier=0;
        if(CONval<5){
            CON_modifier = CONval-1;
        }
        else if(CONval>=5){
            CON_modifier = CONval+1; 
        }
        return CON_modifier;
    }

    public static int DEX_mod(int DEXval){
        int DEX_modifier=0;
        if(DEXval<5){
            DEX_modifier = DEXval-1;
        }
        else if(DEXval>=5){
            DEX_modifier = DEXval+1;
        }
        return DEX_modifier;
    }

    public static int STR_mod(int STRval){
        int STR_modifier1=0;
        if(STRval<5){
            STR_modifier1 = STRval-1;
        }
        else if(STRval>=5){
            STR_modifier1 = STRval+1;
        }
        return STR_modifier1;
    }

    public static Comparator<Creature> creatureComparator = new Comparator<Creature>() {
        public int compare(Creature s1, Creature s2) {
            int c1 = s1.rollinitiative;
            int c2 = s2.rollinitiative;
            //ascending
            return c1-c2;
        }
    };

    public String toString() {  
        return creatureName+","+"HP:"+" "+HP+","+"STR:"+" "+STR+","+"DEX:"+" "+DEX+","+"CON:"+" "+CON; 
    }

}







