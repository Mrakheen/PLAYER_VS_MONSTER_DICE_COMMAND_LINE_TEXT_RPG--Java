//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.Player;
import Phase2.Monster;
import Phase2.Creature;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;   

public class Map{
    public static String max_coordinate_topleft = "(0,0)"; //25 by 25 Grid  ***Grid is rotated by 90 degress due to 2D Array*** |`````
    public static String max_coordinate_bottomleft = "(25,0)";
    public static String max_coordinate_topright = "(0,25)";
    public static String max_coordinate_bottomright = "(25,25)";
    public String playerCoordinate="";
    public int playerCoordinate_x=0;
    public int playerCoordinate_y=0;

    public static boolean MonsterspaceNotOccupied(ArrayList<Monster> monster,int x,int y){
        int i=0,k=0;
        for(i=0;i<monster.size();i++){
            if(monster.get(i).map.playerCoordinate_x == x && monster.get(i).map.playerCoordinate_y == y){
                k++;
            }
        }
        if(k>0){
            return false;
        }
        else{
            return true;
        }
    }


    public static boolean spaceNotOccupied(Creature player,String coordinate){
        if(player.map.playerCoordinate.equals(coordinate)){
            System.out.println("\nA player is already at that position!!\n");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean NotOutofGrid(String coordinate){
        if(coordinate.equals(max_coordinate_topleft)){
            System.out.println("\nPlayer Co-ordinate out of Grid!\n");
            return false;
        }
        else if(coordinate.equals(max_coordinate_bottomleft)){
            System.out.println("\nPlayer Co-ordinate out of Grid!\n");
            return false;
        }
        else if(coordinate.equals(max_coordinate_bottomright)){
            System.out.println("\nPlayer Co-ordinate out of Grid!\n");
            return false;
        }
        else if(coordinate.equals(max_coordinate_topright)){
            System.out.println("\nPlayer Co-ordinate out of Grid!\n");
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean notAtBorderofGrid(int x,int y){
        if(x==0){
            return false;
        }
        else if(y==0){
            return false;
        }
        else if(x==24){
            return false;
        }
        else if(y==24){
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean isMovement(Creature player,String mov_coordinates){
        int x_coordinates;
        int y_coordinates;
        String[] arr_in = mov_coordinates.split("\\u002c", 3);
        arr_in[0] = arr_in[0].replace("(", ""); 
        arr_in[1] = arr_in[1].replace(")", ""); 
        x_coordinates = Integer.parseInt(arr_in[0]);
        y_coordinates = Integer.parseInt(arr_in[1]);
        double x = x_coordinates;
        double y = y_coordinates;
        double x_player = player.map.playerCoordinate_x;
        double y_player = player.map.playerCoordinate_y;
        double units = Math.sqrt(Math.pow(x-x_player,2)+Math.pow(y-y_player,2));
        int mov_units = (int)units;
        if(mov_units<=5){
            return true;
        }
        else{
            System.out.println("\nPlayer is only allowed to move 5 units max!!\n");
            return false;
        }        
    }

    public static boolean isCoordinateEntry(String playerCoordinate){
        Pattern pattern1 = Pattern.compile("\\u0028([0-9]|[1][0-9]|[2][0-5])\\u002c([0-9]|[1][0-9]|[2][0-5])\\u0029", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(playerCoordinate);
        boolean matchFound1 = matcher1.find();
        if(matchFound1){
            return true;
        }
        else{
            return false;
        }
    }

    public static void changeCoordinatetoInt(Creature player,String coordinates){
        String[] arr_in = coordinates.split("\\u002c", 3);
        arr_in[0] = arr_in[0].replace("(", ""); 
        arr_in[1] = arr_in[1].replace(")", ""); 
        player.map.playerCoordinate_x = Integer.parseInt(arr_in[0]);
        player.map.playerCoordinate_y = Integer.parseInt(arr_in[1]);
    }
    
    public static String changeCoordinatetoString(int x,int y){
        String xc = Integer.toString(x);
        String yc = Integer.toString(y);
        String finalstring = "("+xc+","+yc+")";
        return finalstring;
    }

    public static boolean inMonsterRange(Player player,Monster monster){
        double x = monster.map.playerCoordinate_x;
        double y = monster.map.playerCoordinate_y;
        double x_player = player.map.playerCoordinate_x;
        double y_player = player.map.playerCoordinate_y;
        double units = Math.sqrt(Math.pow(x-x_player,2)+Math.pow(y-y_player,2));
        int mov_units = (int)units;
        if(mov_units<=9){
            return true;
        }
        else{
            return false;
        }
    }

    public static void MonstermovetowardsPlayer(Player player,Monster monster){ //AI MOVEMENT
        double x = monster.map.playerCoordinate_x;
        double y = monster.map.playerCoordinate_y;
        double x_player = player.map.playerCoordinate_x;
        double y_player = player.map.playerCoordinate_y;
        double units = Math.sqrt(Math.pow(x-x_player,2)+Math.pow(y-y_player,2));
        int mov_units = (int)units;
        if(mov_units>=17){
            int aiMov = mov_units/4;
            double m = (monster.map.playerCoordinate_y-player.map.playerCoordinate_y)/(monster.map.playerCoordinate_x-player.map.playerCoordinate_x);
            double c = monster.map.playerCoordinate_y-(m*monster.map.playerCoordinate_x);
            double cQ = Math.pow(aiMov,2)-Math.pow(monster.map.playerCoordinate_x,2)+(2*player.map.playerCoordinate_y*c)-Math.pow(player.map.playerCoordinate_y,2)-Math.pow(c,2);
            double aQ = 1+Math.pow(m,2);
            double bQ = (-2*monster.map.playerCoordinate_x)+(2*m*c)-(2*monster.map.playerCoordinate_y*m);
            double x_co1 = (-bQ+Math.sqrt(Math.pow(bQ,2)-(4*aQ*cQ)))/(2*aQ);
            double x_co2 = (-bQ-Math.sqrt(Math.pow(bQ,2)-(4*aQ*cQ)))/(2*aQ);
            int x_co;
            if(x_co1<0){
                x_co1 = -1*x_co1;
            }
            if(x_co2<0){
                x_co2 = -1*x_co2;
            }
            if(x_co1 > x_co2){
                x_co = (int)x_co2;
            }
            else{
                x_co = (int)x_co1;
            }
            double y_cod = (m*x_co)+c;
            int y_co = (int)y_cod;
            if(x_co!=0 || y_co!=0){
                monster.map.playerCoordinate_x = x_co;
                monster.map.playerCoordinate_y = y_co;
            }
            
        }
    }
}























