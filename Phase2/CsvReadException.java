//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception.*;

public class CsvReadException extends Exception{
    private String data;
    public CsvReadException(String data){
        this.data = data;
    }

    public String toString(){
        return "CsvReadException"+"  "+this.data;
    }

}