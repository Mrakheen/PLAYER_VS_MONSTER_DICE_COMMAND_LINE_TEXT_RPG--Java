//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.GameUtility;
import Phase2.Weapon;
import Phase2.Creature;
import Phase2.MonsterType;
import Phase2.CsvReadException;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception.*;

public class Monster extends Creature{
    public MonsterType monstertype;
    public int MonsterID;

    //This method is used for Monster to attack another player.
    public int attack(Creature creature_target){
        int AttackRoll = GameUtility.rollDice("d20")+Creature.DEX_mod(this.DEX);
        int damaged1=0;
        if(AttackRoll>=creature_target.AC){
            damaged1 = this.rollHit();
            Creature.takeDamage(damaged1,creature_target);
            return damaged1; //hit
        }
        else{
            return 0; //miss
        }
    }

    public int rollHit(){
        int damage=0; 
        damage = GameUtility.rollDice("d6") + Creature.STR_mod(this.STR);
        return damage;
    }

    public static Monster MonsterloadFromCsv(String data){
        Monster monster = new Monster();
        String[] arr = new String[data.length()]; 
        arr = data.split(",",data.length());
        try{
            if(arr.length==7){
                monster.creatureName = arr[0];
                String monstyp = arr[1];
                if(monstyp.equals("HUMANOID")){
                    monster.monstertype = MonsterType.Humanoid;
                }
                else if(monstyp.equals("FIEND")){
                    monster.monstertype = MonsterType.Fiend;
                }
                else if(monstyp.equals("DRAGON")){
                    monster.monstertype = MonsterType.Dragon;
                }
                else{
                    System.out.println("Invalid Monster Type!!");
                    throw new CsvReadException(data);
                }
                try{
                    monster.HP = Integer.parseInt(arr[2]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                try{
                    monster.AC = Integer.parseInt(arr[3]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                try{
                    monster.STR = Integer.parseInt(arr[4]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                try{
                    monster.DEX = Integer.parseInt(arr[5]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                try{
                    monster.CON = Integer.parseInt(arr[6]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                return monster; 
            }
            else{
                System.out.println("Input String is incorrect!");
                throw new CsvReadException(data);
            }    
        }
        catch(CsvReadException e){
            System.out.println(e);
            Monster monsternull = new Monster();
            monsternull.HP = -1;
            return monsternull;
        }
    }

}






