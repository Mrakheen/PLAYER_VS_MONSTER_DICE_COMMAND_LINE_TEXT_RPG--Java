//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.Player;
import Phase2.Monster;
import Phase2.Creature;
import java.util.*;
import java.io.*;

public class MapGrid{
    private String[][] mapGrid; 

    public MapGrid(){
        this.mapGrid = new String[25][25]; 
    }    

    public void createMapGrid(){
        int i=0,j=0;
        for(i=0;i<25;i++){
    	    for(j=0;j<25;j++){
        	    this.mapGrid[i][j] = " .";
            }
        }
    }

    public void printMapGrid(){
        // Loop through all rows
        for (int i = 0; i < 25; i++){
            // Loop through all elements of current row
            for (int j = 0; j < 25; j++){
                System.out.print(this.mapGrid[i][j] + " ");
            }
                System.out.print("\n");
        }
    }

    public void initailcreatureposition_inMap(int x,int y,String sym){
        this.mapGrid[24-y][x] = sym;
    } 

    public void changeCreatureposition_inMap(int initialx,int initialy,int newx,int newy,String sym){
        this.mapGrid[24-initialy][initialx] = " .";
        this.mapGrid[24-newy][newx] = sym;
    }

    public void removecreaturePosition(int x,int y){
        this.mapGrid[24-y][x] = " .";
    }

}












