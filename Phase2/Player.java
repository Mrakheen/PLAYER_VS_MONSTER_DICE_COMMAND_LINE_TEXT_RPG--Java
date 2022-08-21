//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.GameUtility;
import Phase2.Weapon;
import Phase2.Creature;
import Phase2.CsvReadException;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception.*;

public class Player extends Creature{
    private Weapon weapon;

    public void initializeweapon(){
        this.weapon = new Weapon();
    }
    public int getWeaponBonus(){
        return this.weapon.bonusDamage;
    }
    public void setWeaponBonus(int Bonus){
        this.weapon.bonusDamage = Bonus;
    }
    public String getWeaponName(){
        return this.weapon.weaponName;
    }
    public void setWeaponName(String Name){
        this.weapon.weaponName = Name;
    }
    public String getWeaponDamage(){
        return this.weapon.weaponDamage;
    }
    public void setWeaponDamage(String DiceType){
        this.weapon.weaponDamage = DiceType;
    }

    //The method is used for player to attack another player or a monster.
    public int attack(Creature creature_target){
        int AttackRoll = GameUtility.rollDice("d20")+Creature.DEX_mod(this.DEX)+this.weapon.bonusDamage;
        int damaged1=0;
        if(creature_target.AC<=AttackRoll){
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
        damage = this.weapon.rollDamage(); 
        damage = damage + Creature.STR_mod(this.STR);
        return damage;
    }

    public static int HPwithModifier(int CONval){
        int CON_modifier=0;
        if(CONval<5){
            CON_modifier = CONval-1;
        }
        if(CONval>=5){
            CON_modifier = CONval+1;
        }
        return (50 + CON_modifier);
    }

    public static int ACwithModifier(int DEXval){
        int DEX_modifier=0;
        if(DEXval<5){
            DEX_modifier = DEXval-1;
        }
        if(DEXval>=5){
            DEX_modifier = DEXval+1;
        }
        return (15 + DEX_modifier);
    }
    
    public static boolean statCheck(int CONval,int DEXval,int STRval){
        if((CONval+DEXval+STRval) <= 10 && (CONval+DEXval+STRval) >=0){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static Player PlayerloadFromCsv(String data){
        Player player = new Player();
        String[] arr = new String[data.length()]; 
        arr = data.split(",",data.length());
        try{
            if(arr.length==7){
                player.creationDate = arr[0];
                player.creatureName = arr[1];
                try{
                    player.STR = Integer.parseInt(arr[2]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of STR!");
                    throw new CsvReadException(data);
                }
                try{
                    player.DEX = Integer.parseInt(arr[3]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of DEX!");
                    throw new CsvReadException(data);
                }
                try{
                    player.CON = Integer.parseInt(arr[4]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of CON!");
                    throw new CsvReadException(data);
                }
                try{
                    player.HP = Integer.parseInt(arr[5]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of HP!");
                    throw new CsvReadException(data);
                }
                try{
                    player.AC = Integer.parseInt(arr[6]);
                }
                catch(NumberFormatException e){
                    System.out.println("NumberFormatException  Invalid value of HP!");
                    throw new CsvReadException(data);
                }
                return player;
            }
            else{
                System.out.println("Input String is incorrect!");
                throw new CsvReadException(data);
            }     
        }
        catch(CsvReadException e){
            System.out.println(e);
            Player playernull = new Player();
            playernull.HP = -1;
            return playernull;
        }
        
    }

}






