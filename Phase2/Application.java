//Name : Mubtasim Ahmed Rakheen
//UTA ID: 1001848135
//CSE 1325 PROF ALEX DILLHOF
package Phase2;
import Phase2.GameUtility;
import Phase2.Player;
import Phase2.Weapon;
import Phase2.Combat;
import Phase2.Map;
import Phase2.MapGrid;
import Phase2.Monster;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.lang.*;
import java.lang.Exception.*; 
import java.text.ParseException;  

public class Application{
    public static ArrayList <String> weaponNameList = new ArrayList<String>();
    public static ArrayList <String> weaponDamageList = new ArrayList<String>();
    public static ArrayList <Integer> bonusDamageList = new ArrayList<Integer>();
    public static void read_weaponinfo(String filename) throws Exception{ //.csv file
        weaponNameList.clear();
        weaponDamageList.clear();
        bonusDamageList.clear();
        try{
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String data = scan.nextLine();
                String[] arr = new String[data.length()];
                arr = data.split(",",data.length());
                weaponNameList.add(arr[0]);
                weaponDamageList.add(arr[1]);
                int val = Integer.parseInt(arr[2]);
                bonusDamageList.add(val);
            }
            scan.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found!!\n");
        }
    }

    public static ArrayList <String> pDates = new ArrayList<String>();
    public static ArrayList <String> pNames = new ArrayList<String>();
    public static ArrayList <Integer> pSTR = new ArrayList<Integer>();
    public static ArrayList <Integer> pDEX = new ArrayList<Integer>();
    public static ArrayList <Integer> pCON = new ArrayList<Integer>();
    public static ArrayList <Integer> pHP = new ArrayList<Integer>();
    public static ArrayList <Integer> pAC = new ArrayList<Integer>();;
    public static ArrayList <String> tpNames = new ArrayList<String>();
    public static boolean read_playercharacterinfo(String folder) throws Exception{
        tpNames.clear();
        File directoryPath = new File(folder);
        if(directoryPath.exists()){
            //List of all files and directories
            File filesList[] = directoryPath.listFiles();
            for(File file : filesList) {
                try{
                    Scanner sc= new Scanner(file);
                    while (sc.hasNextLine()) {
                        String input = sc.nextLine();
                        String[] arr = new String[input.length()];
                        arr = input.split(",",input.length());
                        tpNames.add(arr[1]);
                    }
                }
                catch(FileNotFoundException e){
                    System.out.println("File could not be found!!\n");
                    continue;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    public static void saveCreatedCharacterinArrayList(String chardate,String charname,int str,int dex,int con,int hp,int ac){
        pDates.add(chardate);
        pNames.add(charname);
        pSTR.add(str);
        pDEX.add(dex);
        pCON.add(con);
        pHP.add(hp);
        pAC.add(ac);
    }

    public static ArrayList <Creature> PlayerCreatures = new ArrayList<Creature>();
    public static ArrayList <Creature> MonsterCreatures = new ArrayList<Creature>();

    public static void rollInitiative(ArrayList<Creature> creatures){ //sorts the list of all creatures based on their individual initiative rolls.
        Collections.sort(creatures, Creature.creatureComparator);
    }

    public static boolean write_playercharacterinfo(String filename,String chardate,String charname,int str,int dex,int con,int hp,int ac) throws Exception{ //.csv file
        File myObj = new File(filename);
        if(myObj.createNewFile()){
            FileWriter myWriter = new FileWriter(filename);
            //format : creation_date,character_name,STR,DEX,CON,HP,AC
            myWriter.write(chardate+","+charname+","+str+","+dex+","+con+","+hp+","+ac+"\n");
            myWriter.close();
            return true;
        }
        else{
            return false;
        }        
    }

    public static boolean readSpecific_playercharacterinfo(String filename) throws Exception{
        File file = new File(filename);
        if(file.exists()){
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String data = scan.nextLine();
                Player playerobj = Player.PlayerloadFromCsv(data);
                if(playerobj.HP != -1){
                    PlayerCreatures.add(playerobj);
                }
            }
            scan.close();
            return true;
        }
        else{
            return false;
        }
    }

    //Used for Player Creature Type ArrayList
    public static boolean readPlayerData(String folder) throws Exception{
        PlayerCreatures.clear();
        //Creating a File object for directory
        File directoryPath = new File(folder);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        if(filesList.length > 0){
            for(File file : filesList) {
                try{
                    Scanner sc= new Scanner(file);
                    while (sc.hasNextLine()) {
                        String input = sc.nextLine();
                        Player playerobj = Player.PlayerloadFromCsv(input);
                        if(playerobj.HP != -1){
                            PlayerCreatures.add(playerobj);
                        }
                    }
                }
                catch(FileNotFoundException e){
                    System.out.println("File could not be found!!\n");
                    continue;
                }
            }
            return true; 
        }
        else{ //no files in folder
            return false;
        } 
    }

    //Used for Monster Creature Type ArrayList
    public static void readMonsterData(String filename) throws Exception{
        MonsterCreatures.clear();
        try{
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                String data = scan.nextLine();
                Monster monsterobj = Monster.MonsterloadFromCsv(data);
                if(monsterobj.HP != -1){
                    MonsterCreatures.add(monsterobj);
                }
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File could not be found!!\n");
        }
    }

    public static int nameValid(String name){
        try{
            Character capLetter = name.charAt(0);
            int strlen = name.length();
            int isnumSpec=0;
            int i=0;
            for(i=0;i<strlen;i++){
                Character check = name.charAt(i);
                if(Character.isLetter(check)){
                    continue;
                }
                else if(Character.isDigit(check)){
                    isnumSpec = 1;
                    break;
                }
                else if(Character.isWhitespace(check)){
                    continue;
                }
                else{ //special characters
                    isnumSpec = 1;
                    break;
                }
            }
            if(Character.isLowerCase(capLetter)){
                throw new ParseException("Name must start with a capital letter.",0);
            }
            else if(strlen>=24){
                throw new ParseException("Name cannot be longer than 24 characters.",i);
            }
            else if(isnumSpec==1){
                throw new ParseException("Name cannot have numbers or special characters.",i);
            }
            else{
                return 1;
            }
        }
        catch(ParseException e){
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public static void main(String[] args)throws Exception{
        int j=0,ci=0;
        Scanner obj = new Scanner(System.in);
        Scanner obj2 = new Scanner(System.in);
        while(j==0){
            int i=0, ans1=0;
            while(i==0){
                ci=0;
                do{
                    try{
                        System.out.println("1. Start Game");
                        System.out.println("2. Create Character");
                        System.out.println("3. Load Character");
                        System.out.println("4. Save Character");
                        System.out.println("5. Quit");
                        System.out.printf("> ");
                        ans1 = obj2.nextInt();
                        ci=1;
                    }
                    catch(InputMismatchException e){
                        System.out.println("Invalid Input!! Please Enter Again.\n");
                    }
                    obj2.nextLine(); //clears buffer
                }while(ci==0);
                if(ans1!=1 && ans1!=2 && ans1!=3 && ans1!=4 && ans1!=5){
                    System.out.println("Invalid Input!! Please Enter Again.\n");
                }
                else if(ans1==5){
                    System.out.println("Exiting.....");
                    i=1;
                    j=1;
                    System.exit(0);
                }
                else{
                    i=1;
                }
            }
            System.out.println("\n");
            
            if(ans1==2){ //Create Characters------------
                int k=0;
                while(k==0){
                    String charName=""; //character name
                    String charCreationDate=""; //character creation date
                    int charSTR=0; //Strength
                    int charCON=0; //Constitution
                    int charDEX=0; //Dexterity
                    int charHP=0; //Hit Points
                    int charAC=0; //Armor Class

                    boolean isfil = read_playercharacterinfo("saved/players");
                    int cn=0;
                    if(isfil){
                        int namevalid=0;
                        while(cn==0 && namevalid==0){
                            System.out.print("Enter Character Name: ");
                            String cns = obj.nextLine();
                            namevalid = nameValid(cns);
                            int cn2=0,count=0;
                            for(cn2=0;cn2<tpNames.size();cn2++){
                                if(cns.equals(tpNames.get(cn2))){
                                    System.out.println("\nCharacter Name already exists. Please enter another one.\n");
                                    break;
                                }
                                count++;
                            }
                            int size = tpNames.size();
                            if(count==size){
                                charName = cns;
                                cn=1;
                            }
                        }
                    }
                    else{
                        int namevalid=0;
                        while(namevalid==0){
                            System.out.print("Enter Character Name: ");
                            charName = obj.nextLine();
                            namevalid = nameValid(charName);
                        }
                    }
                    
                    LocalDate localDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    charCreationDate = localDate.format(formatter);
                    int ans2=0;
                    ci=0;
                    do{
                        try{
                            System.out.println("1. Manual Stats");
                            System.out.println("2. Random Stats");
                            System.out.print("Manual or Random Stats? ");
                            ans2 = obj2.nextInt();
                            ci=1;
                        }
                        catch(InputMismatchException e){
                            System.out.println("Invalid Input!! Please Enter Again.\n");
                        }
                        obj2.nextLine(); //clears buffer
                    }while(ci==0);

                    if(ans2==1){ //Manual Stats
                        int remain = 10;
                        int l=0;
                        while(l==0){
                            System.out.println("\nSTR:"+" "+charSTR);
                            System.out.println("DEX:"+" "+charDEX);
                            System.out.println("CON:"+" "+charCON);
                            System.out.println("Remaining:"+" "+remain+"\n");
                            System.out.println("1. Add STR");
                            System.out.println("2. Add DEX");
                            System.out.println("3. Add CON");
                            System.out.println("4. Reset");
                            System.out.println("5. Finish");
                            int ans3 = 0;
                            ci=0;
                            do{
                                try{
                                    System.out.print("> ");
                                    ans3 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine(); //clears buffer
                            }while(ci==0);
                            System.out.println("\n");
                            if(ans3==1){ //Add STR
                                int m=0;
                                while(m==0){
                                    int ans4 = 0;
                                    ci=0;
                                    do{
                                        try{
                                            System.out.print("Enter STR: ");
                                            ans4 = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input!! Please Enter Again.\n");
                                        }
                                        obj2.nextLine(); //clears buffer
                                    }while(ci==0);
                                    if(ans4<0){
                                        System.out.println("STR input cannot be negative. Please enter again.");
                                    }
                                    else if(ans4>10){
                                        System.out.println("Input value cannot exceed 10. Please try again.");
                                    }
                                    else if(ans4>remain && charSTR==0){
                                        ans4 = remain;
                                        charSTR = ans4;
                                        remain = 0;
                                        System.out.println("**Max Stat point of 10 has been reached!!**");
                                        m=1;
                                    }
                                    else if(ans4<=remain && charSTR==0){
                                        charSTR = ans4;
                                        remain = remain - ans4;
                                        m=1;
                                    }
                                    else{ //charSTR!=0
                                        System.out.println("Value for STR has already been assigned!");
                                        m=1;
                                    }
                                }     
                            }
                            else if(ans3==2){ //Add DEX
                                int n=0;
                                while(n==0){
                                    int ans5 = 0;
                                    ci=0;
                                    do{
                                        try{
                                            System.out.print("Enter DEX: ");
                                            ans5 = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input!! Please Enter Again.\n");
                                        }
                                        obj2.nextLine(); //clears buffer
                                    }while(ci==0);
                                    if(ans5<0){
                                        System.out.println("DEX input cannot be negative. Please enter again.");
                                    }
                                    else if(ans5>10){
                                        System.out.println("Input value cannot exceed 10. Please try again.");
                                    }
                                    else if(ans5>remain && charDEX==0){
                                        ans5 = remain;
                                        charDEX = ans5;
                                        remain = 0;
                                        System.out.println("**Max Stat point of 10 has been reached!!**");
                                        n=1;
                                    }
                                    else if(ans5<=remain && charDEX==0){
                                        charDEX = ans5;
                                        remain = remain - ans5;
                                        n=1;
                                    }
                                    else{ //charDEX!=0
                                        System.out.println("Value for DEX has already been assigned!");
                                        n=1;
                                    }
                                }
                            }
                            else if(ans3==3){ //Add CON
                                int p=0;
                                while(p==0){
                                    int ans6 = 0;
                                    ci=0;
                                    do{
                                        try{
                                            System.out.print("Enter CON: ");
                                            ans6 = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input!! Please Enter Again.\n");
                                        }
                                        obj2.nextLine(); //clears buffer
                                    }while(ci==0);
                                    if(ans6<0){
                                        System.out.println("CON input cannot be negative. Please enter again.");
                                    }
                                    else if(ans6>10){
                                        System.out.println("Input value cannot exceed 10. Please try again.");
                                    }
                                    else if(ans6>remain && charCON==0){
                                        ans6 = remain;
                                        charCON = ans6;
                                        remain = 0;
                                        System.out.println("**Max Stat point of 10 has been reached!!**");
                                        p=1;
                                    }
                                    else if(ans6<=remain && charCON==0){
                                        charCON = ans6;
                                        remain = remain - ans6;
                                        p=1;
                                    }
                                    else{ //charCON!=0
                                        System.out.println("Value for CON has already been assigned!");
                                        p=1;
                                    }
                                }
                            }
                            else if(ans3==4){ //Reset
                                charSTR=0; 
                                charCON=0; 
                                charDEX=0; 
                                charHP=0; 
                                charAC=0;
                                remain = 10;
                                System.out.println("**All Stats have been reset.**");
                            }
                            else if(ans3==5){ //Finish
                                l=1;
                            }
                            else{
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                        }
                        charHP = Player.HPwithModifier(charCON);
                        charAC = Player.ACwithModifier(charDEX);
                        saveCreatedCharacterinArrayList(charCreationDate,charName,charSTR,charDEX,charCON,charHP,charAC);
                        int c1=0;
                        while(c1==0){
                            System.out.print("Do you want to create more characters(Y/N)? ");
                            String isout = obj.nextLine();
                            if(isout.equals("Y")){
                                System.out.println("\n");
                                c1=1;
                            }
                            else if(isout.equals("N")){
                                k=1;
                                c1=1;
                            }
                            else{
                                System.out.printf("Invalid Input!! Please Enter Again.\n");
                            }
                        }                        
                    }
                    else if(ans2==2){ //Random Stats
                        boolean ranCheck = false;
                        Random rand = new Random();
                        while(!ranCheck){
                            charCON = rand.nextInt(11);
                            charDEX = rand.nextInt(11);
                            charSTR = rand.nextInt(11);
                            ranCheck = Player.statCheck(charCON,charDEX,charSTR);
                        }
                        int remainRan = 10 - (charCON+charDEX+charSTR);
                        System.out.println("\nSTR:"+" "+charSTR);
                        System.out.println("DEX:"+" "+charDEX);
                        System.out.println("CON:"+" "+charCON);
                        System.out.println("Remaining:"+" "+remainRan);
                        System.out.println("**Random values assigned to Stats.**\n");
                        charHP = Player.HPwithModifier(charCON);
                        charAC = Player.ACwithModifier(charDEX);
                        saveCreatedCharacterinArrayList(charCreationDate,charName,charSTR,charDEX,charCON,charHP,charAC);
                        int c=0;
                        while(c==0){
                            System.out.print("Do you want to create more characters(Y/N)? ");
                            String isout = obj.nextLine();
                            if(isout.equals("Y")){
                                System.out.println("\n");
                                c=1;
                            }
                            else if(isout.equals("N")){
                                System.out.println("\n");
                                k=1;
                                c=1;
                            }
                            else{
                                System.out.printf("Invalid Input!! Please Enter Again.\n");
                            }
                        }
                    }
                    else{
                        System.out.println("Invalid Input!! Please Enter Again.\n");
                    }
                }   
            }
            //----------------------------------

            else if(ans1==4){ //Save Character-----------------
                if(pNames.size()==0){
                    System.out.println("There are no characters that have been created. Please create at least 1 character first to save.\n");
                }
                else{
                    System.out.println("Created Characters in Memory:");
                    int count=0,a=0,a2=0,p1_ans1=0;
                    for(a=0;a<pNames.size();a++){
                        count = a+1;
                        System.out.println(count+"."+" "+pNames.get(a)+","+" "+"STR: "+pSTR.get(a)+","+" "+"DEX: "+pDEX.get(a)+","+" "+"CON: "+pCON.get(a)+","+" "+"HP: "+pHP.get(a)+","+" "+"AC: "+pAC.get(a)+"  "+"created "+pDates.get(a)+"\n");
                    }
                    System.out.println("Do you want to:");
                    System.out.println("1. Save all created characters");
                    System.out.println("2. Choose a character to save from above");
                    while(a2==0){
                        ci=0;
                        do{
                            try{
                                System.out.printf("> ");
                                p1_ans1 = obj2.nextInt();
                                ci=1;
                            }
                            catch(InputMismatchException e){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            obj2.nextLine();
                        }while(ci==0);
                        if(p1_ans1!=1 && p1_ans1!=2){
                            System.out.println("Invalid Input!! Please Enter Again.");
                        }
                        else{
                            a2=1;
                        }
                    }
                    if(p1_ans1==1){ //save all characters
                        int wi=0;
                        for(wi=0;wi<pNames.size();wi++){
                            String playerfilename = "saved/players/"+pNames.get(wi)+".csv";
                            boolean iswrite = write_playercharacterinfo(playerfilename,pDates.get(wi),pNames.get(wi),pSTR.get(wi),pDEX.get(wi),pCON.get(wi),pHP.get(wi),pAC.get(wi));
                            if(iswrite){
                                continue;
                            }
                            else{
                                System.out.println("Player Character "+pNames.get(wi)+" already exists.");
                            }
                        }
                        System.out.println("All created characters have been saved.\n");
                        pDates.clear();
                        pNames.clear();
                        pSTR.clear();
                        pDEX.clear();
                        pCON.clear();
                        pHP.clear();
                        pAC.clear();
                    }
                    else{ //choose a character to save from above
                        int lo=0;
                        while(lo==0){
                            a2=0;
                            p1_ans1=0;
                            while(a2==0){
                                ci=0;
                                do{
                                    try{
                                        System.out.println("\nEnter List Number from Chracter List to Save: ");
                                        p1_ans1 = obj2.nextInt();
                                        ci=1;
                                    }
                                    catch(InputMismatchException e){
                                        System.out.println("Invalid Input!! Please Enter Again.");
                                    }
                                    obj2.nextLine();
                                }while(ci==0);
                                if(p1_ans1>count || p1_ans1<=0){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                else{
                                    a2=1;
                                }
                            }
                            String playerfilename = "saved/players/"+pNames.get(p1_ans1-1)+".csv"; 
                            boolean iswrite = write_playercharacterinfo(playerfilename,pDates.get(p1_ans1-1),pNames.get(p1_ans1-1),pSTR.get(p1_ans1-1),pDEX.get(p1_ans1-1),pCON.get(p1_ans1-1),pHP.get(p1_ans1-1),pAC.get(p1_ans1-1));
                            if(iswrite){
                                System.out.println("Player Character "+pNames.get(p1_ans1-1)+" has been saved.");
                            }
                            else{
                                System.out.println("Player Character "+pNames.get(p1_ans1-1)+" already exists.");
                            }
                            pDates.remove(p1_ans1-1);
                            pNames.remove(p1_ans1-1);
                            pSTR.remove(p1_ans1-1);
                            pDEX.remove(p1_ans1-1);
                            pCON.remove(p1_ans1-1);
                            pHP.remove(p1_ans1-1);
                            pAC.remove(p1_ans1-1);
                            int c=0;
                            while(c==0){
                                System.out.print("Do you want to save more Characters(Y/N)? ");
                                String isout = obj.nextLine();
                                if(isout.equals("Y")){
                                    if(pNames.size()==0){
                                        System.out.println("No more characters in memory to load.");
                                        System.out.println("\n");
                                        lo=1;
                                        c=1;
                                    }
                                    else{
                                        System.out.println("\n");
                                        count=0;a=0;p1_ans1=0;
                                        for(a=0;a<pNames.size();a++){
                                            count = a+1;
                                            System.out.println(count+"."+" "+pNames.get(a)+","+" "+"STR: "+pSTR.get(a)+","+" "+"DEX: "+pDEX.get(a)+","+" "+"CON: "+pCON.get(a)+","+" "+"HP: "+pHP.get(a)+","+" "+"AC: "+pAC.get(a)+"  "+"created "+pDates.get(a)+"\n");
                                        }
                                        c=1;
                                    }
                                }
                                else if(isout.equals("N")){
                                    pDates.clear();
                                    pNames.clear();
                                    pSTR.clear();
                                    pDEX.clear();
                                    pCON.clear();
                                    pHP.clear();
                                    pAC.clear();
                                    System.out.println("\n");
                                    lo=1;
                                    c=1;
                                }
                                else{
                                    System.out.printf("Invalid Input!! Please Enter Again.\n");
                                }
                            }
                        }
    
                    }
                }
            

            }
            //---------------------------------------------

            else if(ans1==3){ //Load Character---------------------
                int a2=0,p1_ans1=0;
                System.out.println("Do you want to:");
                System.out.println("1. Load all characters");
                System.out.println("2. Select a character to load");
                while(a2==0){
                    ci=0;
                    do{
                        try{
                            System.out.printf("> ");
                            p1_ans1 = obj2.nextInt();
                            ci=1;
                        }
                        catch(InputMismatchException e){
                            System.out.println("Invalid Input!! Please Enter Again.");
                        }
                        obj2.nextLine();
                    }while(ci==0);
                    if(p1_ans1!=1 && p1_ans1!=2){
                        System.out.println("Invalid Input!! Please Enter Again.");
                    }
                    else{
                        a2=1;
                    }
                }

                if(p1_ans1==1){ //Load all characters
                    boolean isfol = readPlayerData("saved/players");
                    if(isfol){
                        System.out.println("All characters have been loaded.");
                    }
                    else{
                        System.out.println("No characters have been created. Please create at least 1 character first.");
                    }
                }

                else{ //Select a character to load
                    int mm=0;
                    while(mm==0){
                        System.out.println("Enter exact character name to load or enter Q to quit load character: ");
                        String ca = obj.nextLine();
                        if(ca.equals("Q")){
                            break;
                        }
                        String file = "saved/players/"+ca+".csv"; 
                        int cp=0,y=0;
                        for(cp=0;cp<PlayerCreatures.size();cp++){
                            if(PlayerCreatures.get(cp).creatureName.equals(ca)){
                                y=1;
                                break;
                            }
                        }
                        if(y!=1){
                            boolean exist = readSpecific_playercharacterinfo(file);
                            if(exist){
                                System.out.println("Player Character "+ca+" has been loaded.");
                            }
                            else{
                                System.out.println("The entered character does not exist. Please enter again.\n");
                            }
                        }
                        else if(y==1){
                            System.out.println("Player Character "+ca+" is already loaded! Please Enter Again.");
                        }
                    }
                }
            }
            //-----------------------

            else if(ans1==1){ //Start Game----------------------
                int sg=0;
                Player psave = new Player();
                Player psave2 = new Player();
                int pvp = 0;
                while(sg==0){
                    if(pvp==1){
                        PlayerCreatures.add(psave);
                        PlayerCreatures.add(psave2);
                        pvp = 0; //extra
                    }
                    //readMonsterData("saved/monsters.csv");
                    int a2=0,p1_ans1=0;
                    System.out.println("1. Play with Random Monsters");
                    System.out.println("2. Play with Players Only (PvP)");
                    System.out.println("3. Back");
                    while(a2==0){
                        ci=0;
                        do{
                            try{
                                System.out.printf("> ");
                                p1_ans1 = obj2.nextInt();
                                ci=1;
                            }
                            catch(InputMismatchException e){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            obj2.nextLine();
                        }while(ci==0);
                        if(p1_ans1!=1 && p1_ans1!=2 && p1_ans1!=3){
                            System.out.println("Invalid Input!! Please Enter Again.");
                        }
                        else{
                            a2=1;
                        }
                    }
                    if(PlayerCreatures.size()==0){
                        System.out.println("Please load player characters before starting the game.");
                        break;
                    }
                    if(PlayerCreatures.size()==1 && p1_ans1==2){
                        System.out.println("At least 2 characters need to be created and saved for PvP gameplay.");
                        break;
                    }

                    if(p1_ans1==3){
                        System.out.println("Going back to main menu...");
                        sg=1;
                    }
                    else if(p1_ans1==2){ //PvP play-------------------
                        pvp=1;
                        MapGrid mapGrid = new MapGrid();
                        mapGrid.createMapGrid();
                        read_weaponinfo("saved/weapons.csv");
                        rollInitiative(PlayerCreatures);
                        //Player1------------------------------------------------------
                        System.out.println("Player 1 Choose Character: ");
                        int count=0,a=0;
                        a2=0;p1_ans1=0;
                        for(a=0;a<PlayerCreatures.size();a++){
                            count = a+1;
                            System.out.println(count+"."+" "+PlayerCreatures.get(a).creatureName+","+" "+"STR: "+PlayerCreatures.get(a).STR+","+" "+"DEX: "+PlayerCreatures.get(a).DEX+","+" "+"CON: "+PlayerCreatures.get(a).CON+","+" "+"HP: "+PlayerCreatures.get(a).HP+","+" "+"AC: "+PlayerCreatures.get(a).AC+"  "+"created "+PlayerCreatures.get(a).creationDate);
                        }
                        while(a2==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans1 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans1>count || p1_ans1<=0){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            else{
                                a2=1;
                            }
                        }

                        System.out.println("\nPlayer 1 Choose Weapon: ");
                        int count2=0,a3=0,a4=0,p1_ans2=0;
                        for(a3=0;a3<weaponNameList.size();a3++){
                            count2 = a3+1;
                            System.out.println(count2+"."+" "+weaponNameList.get(a3)+","+" "+"Damage: "+weaponDamageList.get(a3)+","+" "+"Bonus To-Hit: "+bonusDamageList.get(a3));
                        }
                        while(a4==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans2 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans2>count2 || p1_ans2<=0){
                                System.out.println("Invalid Input!! Please Enter Again");
                            }
                            else{
                                a4=1;
                            }
                        }
                        Player player1 = new Player();
                        player1 = (Player)PlayerCreatures.get(p1_ans1-1);
                        psave = player1;
                        int p1_SHP = player1.HP;
                        player1.initializeweapon();
                        player1.setWeaponName(weaponNameList.get(p1_ans2-1));
                        player1.setWeaponDamage(weaponDamageList.get(p1_ans2-1));
                        player1.setWeaponBonus(bonusDamageList.get(p1_ans2-1));
                        PlayerCreatures.remove(p1_ans1-1);
                        //-------------------------------------------------------------------------

                        //Player2-----------------------------------------------------
                        System.out.println("\nPlayer 2 Choose Character: ");
                        count=0;a=0;a2=0;p1_ans1=0;
                        for(a=0;a<PlayerCreatures.size();a++){
                            count = a+1;
                            System.out.println(count+"."+" "+PlayerCreatures.get(a).creatureName+","+" "+"STR: "+PlayerCreatures.get(a).STR+","+" "+"DEX: "+PlayerCreatures.get(a).DEX+","+" "+"CON: "+PlayerCreatures.get(a).CON+","+" "+"HP: "+PlayerCreatures.get(a).HP+","+" "+"AC: "+PlayerCreatures.get(a).AC+"  "+"created "+PlayerCreatures.get(a).creationDate);
                        }
                        while(a2==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans1 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans1>count || p1_ans1<=0){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            else{
                                a2=1;
                            }
                        }
                        System.out.println("\nPlayer 2 Choose Weapon: ");
                        count2=0;a3=0;a4=0;p1_ans2=0;
                        for(a3=0;a3<weaponNameList.size();a3++){
                            count2 = a3+1;
                            System.out.println(count2+"."+" "+weaponNameList.get(a3)+","+" "+"Damage: "+weaponDamageList.get(a3)+","+" "+"Bonus To-Hit: "+bonusDamageList.get(a3));
                        }
                        while(a4==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans2 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans2>count2 || p1_ans2<=0){
                                System.out.println("Invalid Input!! Please Enter Again");
                            }
                            else{
                                a4=1;
                            }
                        }
                        Player player2 = new Player();
                        player2 = (Player)PlayerCreatures.get(p1_ans1-1);
                        psave2 = player2;
                        int p2_SHP = player2.HP;
                        player2.initializeweapon();
                        player2.setWeaponName(weaponNameList.get(p1_ans2-1));
                        player2.setWeaponDamage(weaponDamageList.get(p1_ans2-1));
                        player2.setWeaponBonus(bonusDamageList.get(p1_ans2-1));
                        PlayerCreatures.remove(p1_ans1-1);
                        //-----------------------------------------------------

                        //Combat-----------------------------
                        int combatInitiate = Combat.InitiateCombat(player1, player2);
                        int cb=1,cb2=1;
                        if(combatInitiate==1){//If Player 1 first----
                            int countdis1=0;//Disarm counter for Player 1
                            int countdis2=0;//Disarm counter for Player 2
                            int movPoint = 0;
                            int movPoint2 = 0;
                            //initial player coordinates
                            player1.map.playerCoordinate_x = 2;
                            player1.map.playerCoordinate_y = 3;
                            player1.map.playerCoordinate = "(2,3)";
                            player2.map.playerCoordinate_x = 6;
                            player2.map.playerCoordinate_y = 10;
                            player2.map.playerCoordinate = "(6,10)";
                            mapGrid.initailcreatureposition_inMap(player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P1");
                            mapGrid.initailcreatureposition_inMap(player2.map.playerCoordinate_x,player2.map.playerCoordinate_y,"P2");
                            for(cb=1;;cb++){
                                int cb3=0,cb4=0;
                                if(countdis1==cb){//Disarm end
                                    countdis1=0;
                                }
                                if(countdis2==cb){
                                    countdis2=0;
                                }
                                
                                mapGrid.printMapGrid();
                                System.out.print("\n");
                                //Printing Player Info-------
                                System.out.println("\nPlayer 1: "+player1.creatureName);
                                System.out.println("STR: "+player1.STR);
                                System.out.println("DEX: "+player1.DEX);
                                System.out.println("CON: "+player1.CON);
                                System.out.println("HP: "+player1.HP);
                                System.out.println("AC: "+player1.AC);
                                System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                if(player1.HP==0){
                                    System.out.println("\nPlayer 2: "+player2.creatureName);
                                    System.out.println("STR: "+player2.STR);
                                    System.out.println("DEX: "+player2.DEX);
                                    System.out.println("CON: "+player2.CON);
                                    System.out.println("HP: "+player2.HP);
                                    System.out.println("AC: "+player2.AC);
                                    System.out.println("Co-ordinates: "+player2.map.playerCoordinate+"\n");
                                    System.out.println("\n!!!PLAYER 2"+" "+player2.creatureName+" "+"IS VICTORIOUS!!!\n");
                                    System.out.println("  PLAYER 1"+" "+player1.creatureName+" "+"HAS BEEN DEFEATED\n");
                                    player1.HP = p1_SHP;
                                    player2.HP = p2_SHP;
                                    break;
                                }
                                while(cb3==0){//Player 1 Turn
                                    ci=0;
                                    int cbans =0;
                                    do{
                                        try{
                                            System.out.println("***Player can only move before Attcking or Disarming***");
                                            System.out.println("Player 1 choose: ");
                                            System.out.println("1. Attack");
                                            System.out.println("2. Disarm");
                                            System.out.println("3. Move");
                                            System.out.print("> ");
                                            cbans = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input. Please enter Again.\n");
                                        }
                                        obj2.nextLine();
                                    }while(ci==0);
                                    if(cbans!=1 && cbans!=2 && cbans!=3){
                                        System.out.println("Invalid Input. Please enter Again.\n");
                                    }
                                    else if(cbans==1 && countdis1==0){ //Attack
                                        int ishit = player1.attack(player2);
                                        if(ishit != 0){
                                            System.out.println("Player 1 "+player1.creatureName+" has hit Player 2 "+player2.creatureName+" for "+ishit+" damage.");
                                        }
                                        else{
                                            System.out.println("Player 1 "+player1.creatureName+" has missed Player 2 "+player2.creatureName+".");
                                        }
                                        cb3=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==1 && countdis1!=0 || cbans==2 && countdis1!=0){
                                        System.out.println("Player 1 "+player1.creatureName+" is Disarmed!\n");
                                        cb3=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==2 && countdis2==0){ //Disarm
                                        boolean dis1 = Combat.isDisarmed(player1,player2);
                                        if(dis1){
                                            System.out.println("Player 2 "+player2.creatureName+" has been disarmed for 2 rounds.\n");
                                            countdis2 = cb+2;
                                        }
                                        else{
                                            System.out.println("Player 2 "+player2.creatureName+" could not be disarmed!\n");
                                        }
                                        cb3=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==2 && countdis2!=0){
                                        System.out.println("Player 2 "+player2.creatureName+" is already disarmed! Please Enter Again.\n");
                                    }
                                    else{ //Move
                                        if(movPoint==0){
                                            int x=0;
                                            while(x==0){
                                                System.out.println("**25 BY 25 GRID**");
                                                System.out.print("Enter Co-ordinate to move (x,y): ");
                                                String coordinate = obj.nextLine();
                                                boolean iscoordinate = Map.isCoordinateEntry(coordinate);
                                                boolean scheck = false;
                                                boolean gcheck = false;
                                                boolean ismove = false;
                                                boolean bmove = false;
                                                String[] arr_in = coordinate.split("\\u002c", 3);
                                                arr_in[0] = arr_in[0].replace("(", ""); 
                                                arr_in[1] = arr_in[1].replace(")", ""); 
                                                int xx = Integer.parseInt(arr_in[0]);
                                                int yy = Integer.parseInt(arr_in[1]);
                                                if(iscoordinate){
                                                    scheck = Map.spaceNotOccupied(player2,coordinate);
                                                    gcheck = Map.NotOutofGrid(coordinate);
                                                    ismove = Map.isMovement(player1,coordinate);
                                                    bmove = Map.notAtBorderofGrid(xx,yy);
                                                    if(bmove==false){
                                                        System.out.println("Movement to border of grid is not allowed.");
                                                    }
                                                }
                                                if(iscoordinate && scheck && gcheck && ismove && bmove){
                                                    int initialx = player1.map.playerCoordinate_x;
                                                    int initialy = player1.map.playerCoordinate_y;
                                                    Map.changeCoordinatetoInt(player1,coordinate);
                                                    player1.map.playerCoordinate = coordinate;
                                                    mapGrid.changeCreatureposition_inMap(initialx,initialy,player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P1");
                                                    System.out.println("\n");
                                                    movPoint = 1;
                                                    x=1;
                                                }
                                                else{
                                                    System.out.println("Invalid Co-ordinate Entry!! Please Enter Again.\n");
                                                }
                                            }
                                        }
                                        else{
                                            System.out.println("Players cannot move more than once in 1 round!\n");
                                        }
                                        
                                    }
                                }
                                
                                System.out.print("\n");
                                System.out.println("\nPlayer 2: "+player2.creatureName);
                                System.out.println("STR: "+player2.STR);
                                System.out.println("DEX: "+player2.DEX);
                                System.out.println("CON: "+player2.CON);
                                System.out.println("HP: "+player2.HP);
                                System.out.println("AC: "+player2.AC);
                                System.out.println("Co-ordinates: "+player2.map.playerCoordinate+"\n");
                                if(player2.HP==0){
                                    System.out.println("\nPlayer 1: "+player1.creatureName);
                                    System.out.println("STR: "+player1.STR);
                                    System.out.println("DEX: "+player1.DEX);
                                    System.out.println("CON: "+player1.CON);
                                    System.out.println("HP: "+player1.HP);
                                    System.out.println("AC: "+player1.AC);
                                    System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                    System.out.println("\n!!!PLAYER 1"+" "+player1.creatureName+" "+"IS VICTORIOUS!!!\n");
                                    System.out.println("  PLAYER 2"+" "+player2.creatureName+" "+"HAS BEEN DEFEATED\n");
                                    player1.HP = p1_SHP;
                                    player2.HP = p2_SHP;
                                    break;
                                }
                                while(cb4==0){//Player 2 Turn
                                    ci=0;
                                    int cbans =0;
                                    do{
                                        try{
                                            System.out.println("***Player can only move before Attcking or Disarming***");
                                            System.out.println("Player 2 choose: ");
                                            System.out.println("1. Attack");
                                            System.out.println("2. Disarm");
                                            System.out.println("3. Move");
                                            System.out.print("> ");
                                            cbans = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input. Please enter Again.\n");
                                        }
                                        obj2.nextLine();
                                    }while(ci==0);
                                    if(cbans!=1 && cbans!=2 && cbans!=3){
                                        System.out.println("Invalid Input. Please enter Again.\n");
                                    }
                                    else if(cbans==1 && countdis2==0){ //Attack
                                        int ishit = player2.attack(player1);
                                        if(ishit != 0){
                                            System.out.println("Player 2 "+player2.creatureName+" has hit Player 1 "+player1.creatureName+" for "+ishit+" damage.");
                                        }
                                        else{
                                            System.out.println("Player 2 "+player2.creatureName+" has missed Player 1 "+player1.creatureName+".");
                                        }
                                        cb4=1;
                                        movPoint2 = 0;
                                    }
                                    else if(cbans==1 && countdis2!=0 || cbans==2 && countdis2!=0){
                                        System.out.println("Player 2 "+player2.creatureName+" is Disarmed!\n");
                                        cb4=1;
                                        movPoint2=0;
                                    }
                                    else if(cbans==2 && countdis1==0){ //Disarm
                                        boolean dis1 = Combat.isDisarmed(player2,player1);
                                        if(dis1){
                                            System.out.println("Player 1 "+player1.creatureName+" has been disarmed for 2 rounds.\n");
                                            countdis1 = cb+3;
                                        }
                                        else{
                                            System.out.println("Player 1 "+player1.creatureName+" could not be disarmed!\n");
                                        }
                                        cb4=1;
                                        movPoint2=0;
                                    }
                                    else if(cbans==2 && countdis1!=0){
                                        System.out.println("Player 1 "+player1.creatureName+" is already disarmed! Please Enter Again\n");
                                    }
                                    else{ //Move
                                        if(movPoint2==0){
                                            int x=0;
                                            while(x==0){
                                                System.out.println("**25 BY 25 GRID**");
                                                System.out.print("Enter Co-ordinate to move (x,y): ");
                                                String coordinate = obj.nextLine();
                                                boolean iscoordinate = Map.isCoordinateEntry(coordinate);
                                                boolean scheck = false;
                                                boolean gcheck = false;
                                                boolean ismove = false;
                                                boolean bmove = false;
                                                String[] arr_in = coordinate.split("\\u002c", 3);
                                                arr_in[0] = arr_in[0].replace("(", ""); 
                                                arr_in[1] = arr_in[1].replace(")", ""); 
                                                int xx = Integer.parseInt(arr_in[0]);
                                                int yy = Integer.parseInt(arr_in[1]);
                                                if(iscoordinate){
                                                    scheck = Map.spaceNotOccupied(player1,coordinate);
                                                    gcheck = Map.NotOutofGrid(coordinate);
                                                    ismove = Map.isMovement(player2,coordinate);
                                                    bmove = Map.notAtBorderofGrid(xx,yy);
                                                    if(bmove==false){
                                                        System.out.println("Movement to border of grid is not allowed.");
                                                    }
                                                }
                                                if(iscoordinate && gcheck && scheck && ismove && bmove){
                                                    int initialx = player2.map.playerCoordinate_x;
                                                    int initialy = player2.map.playerCoordinate_y;
                                                    Map.changeCoordinatetoInt(player2,coordinate);
                                                    player2.map.playerCoordinate = coordinate;
                                                    mapGrid.changeCreatureposition_inMap(initialx,initialy,player2.map.playerCoordinate_x,player2.map.playerCoordinate_y,"P2");
                                                    mapGrid.printMapGrid();
                                                    System.out.println("\n");
                                                    movPoint2 = 1;
                                                    x=1;
                                                }
                                                else{
                                                    System.out.println("Invalid Co-ordinate Entry!! Please Enter Again.\n");
                                                }
                                            }
                                        }
                                        else{
                                            System.out.println("Players cannot move more than once in 1 round!\n");
                                        }
                                        
                                    }
                                }                       
                            }
                        }

                        else{ //Player2 first----
                            int countdis1=0;//Disarm counter for Player 1
                            int countdis2=0;//Disarm counter for Player 2
                            int movPoint=0;
                            int movPoint2=0;
                            //initial player coordinates
                            player1.map.playerCoordinate_x = 2;
                            player1.map.playerCoordinate_y = 3;
                            player1.map.playerCoordinate = "(2,3)";
                            player2.map.playerCoordinate_x = 6;
                            player2.map.playerCoordinate_y = 10;
                            player2.map.playerCoordinate = "(6,10)";
                            mapGrid.initailcreatureposition_inMap(player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P1");
                            mapGrid.initailcreatureposition_inMap(player2.map.playerCoordinate_x,player2.map.playerCoordinate_y,"P2");
                            for(cb=1;;cb++){
                                int cb3=0,cb4=0;
                                if(countdis1==cb){//Disarm end
                                    countdis1=0;
                                }
                                if(countdis2==cb){
                                    countdis2=0;
                                }
                                    
                                mapGrid.printMapGrid();
                                System.out.print("\n");
                                System.out.println("\nPlayer 2: "+player2.creatureName);
                                System.out.println("STR: "+player2.STR);
                                System.out.println("DEX: "+player2.DEX);
                                System.out.println("CON: "+player2.CON);
                                System.out.println("HP: "+player2.HP);
                                System.out.println("AC: "+player2.AC);
                                System.out.println("Co-ordinates: "+player2.map.playerCoordinate+"\n");
                                if(player2.HP==0){
                                    System.out.println("\nPlayer 1: "+player1.creatureName);
                                    System.out.println("STR: "+player1.STR);
                                    System.out.println("DEX: "+player1.DEX);
                                    System.out.println("CON: "+player1.CON);
                                    System.out.println("HP: "+player1.HP);
                                    System.out.println("AC: "+player1.AC);
                                    System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                    System.out.println("\n!!!PLAYER 1"+" "+player1.creatureName+" "+"IS VICTORIOUS!!!\n");
                                    System.out.println("  PLAYER 2"+" "+player2.creatureName+" "+"HAS BEEN DEFEATED\n");
                                    player1.HP = p1_SHP;
                                    player2.HP = p2_SHP;
                                    break;
                                }
                                while(cb4==0){//Player 2 Turn
                                    ci=0;
                                    int cbans =0;
                                    do{
                                        try{
                                            System.out.println("***Player can only move before Attcking or Disarming***");
                                            System.out.println("Player 2 choose: ");
                                            System.out.println("1. Attack");
                                            System.out.println("2. Disarm");
                                            System.out.println("3. Move");
                                            System.out.print("> ");
                                            cbans = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input. Please enter Again.\n");
                                        }
                                        obj2.nextLine();
                                    }while(ci==0);
                                    if(cbans!=1 && cbans!=2 && cbans!=3){
                                        System.out.println("Invalid Input. Please enter Again.\n");
                                    }
                                    else if(cbans==1 && countdis2==0){ //Attack
                                        int ishit = player2.attack(player1);
                                        if(ishit != 0){
                                            System.out.println("Player 2 "+player2.creatureName+" has hit Player 1 "+player1.creatureName+" for "+ishit+" damage.");
                                        }
                                        else{
                                            System.out.println("Player 2 "+player2.creatureName+" has missed Player 1 "+player1.creatureName+".");
                                        }
                                        cb4=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==1 && countdis2!=0 || cbans==2 && countdis2!=0){
                                        System.out.println("Player 2 "+player2.creatureName+" is Disarmed!\n");
                                        cb4=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==2 && countdis1==0){ //Disarm
                                        boolean dis1 = Combat.isDisarmed(player2,player1);
                                        if(dis1){
                                            System.out.println("Player 1 "+player1.creatureName+" has been disarmed for 2 rounds.\n");
                                            countdis1 = cb+2;
                                        }
                                        else{
                                            System.out.println("Player 1 "+player1.creatureName+" could not be disarmed!\n");
                                        }
                                        cb4=1;
                                        movPoint=0;
                                    }
                                    else if(cbans==2 && countdis1!=0){
                                        System.out.println("Player 1 "+player1.creatureName+" is already disarmed!\n");
                                    }
                                    else{ //Move
                                        if(movPoint==0){
                                            int x=0;
                                            while(x==0){
                                                System.out.println("**25 BY 25 GRID**");
                                                System.out.print("Enter Co-ordinate to move (x,y): ");
                                                String coordinate = obj.nextLine();
                                                boolean iscoordinate = Map.isCoordinateEntry(coordinate);
                                                boolean scheck = false;
                                                boolean gcheck = false;
                                                boolean ismove = false;
                                                boolean bmove = false;
                                                String[] arr_in = coordinate.split("\\u002c", 3);
                                                arr_in[0] = arr_in[0].replace("(", ""); 
                                                arr_in[1] = arr_in[1].replace(")", ""); 
                                                int xx = Integer.parseInt(arr_in[0]);
                                                int yy = Integer.parseInt(arr_in[1]);
                                                if(iscoordinate){
                                                    scheck = Map.spaceNotOccupied(player1,coordinate);
                                                    gcheck = Map.NotOutofGrid(coordinate);
                                                    ismove = Map.isMovement(player2,coordinate);
                                                    bmove = Map.notAtBorderofGrid(xx,yy);
                                                    if(bmove==false){
                                                        System.out.println("Movement to border of grid is not allowed.");
                                                    }
                                                }
                                                if(iscoordinate && gcheck && scheck && ismove && bmove){
                                                    int initialx = player2.map.playerCoordinate_x;
                                                    int initialy = player2.map.playerCoordinate_y;
                                                    Map.changeCoordinatetoInt(player2,coordinate);
                                                    player2.map.playerCoordinate = coordinate;
                                                    mapGrid.changeCreatureposition_inMap(initialx,initialy,player2.map.playerCoordinate_x,player2.map.playerCoordinate_y,"P2");
                                                    mapGrid.printMapGrid();
                                                    System.out.println("\n");
                                                    movPoint = 1;
                                                    x=1;
                                                }
                                                else{
                                                    System.out.println("Invalid Co-ordinate Entry!! Please Enter Again.\n");
                                                }
                                            }
                                        }
                                        else{
                                            System.out.println("Players cannot move more than once in 1 round!\n");
                                        }
                                    }
                                }

                                System.out.print("\n");
                                //Printing Player Info-------
                                System.out.println("\nPlayer 1: "+player1.creatureName);
                                System.out.println("STR: "+player1.STR);
                                System.out.println("DEX: "+player1.DEX);
                                System.out.println("CON: "+player1.CON);
                                System.out.println("HP: "+player1.HP);
                                System.out.println("AC: "+player1.AC);
                                System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                if(player1.HP==0){
                                    System.out.println("\nPlayer 2: "+player2.creatureName);
                                    System.out.println("STR: "+player2.STR);
                                    System.out.println("DEX: "+player2.DEX);
                                    System.out.println("CON: "+player2.CON);
                                    System.out.println("HP: "+player2.HP);
                                    System.out.println("AC: "+player2.AC);
                                    System.out.println("Co-ordinates: "+player2.map.playerCoordinate+"\n");
                                    System.out.println("\n!!!PLAYER 2"+" "+player2.creatureName+" "+"IS VICTORIOUS!!!\n");
                                    System.out.println("  PLAYER 1"+" "+player1.creatureName+" "+"HAS BEEN DEFEATED\n");
                                    player1.HP = p1_SHP;
                                    player2.HP = p2_SHP;
                                    break;
                                }
                                while(cb3==0){//Player 1 Turn
                                    ci=0;
                                    int cbans =0;
                                    do{
                                        try{
                                            System.out.println("***Player can only move before Attcking or Disarming***");
                                            System.out.println("Player 1 choose: ");
                                            System.out.println("1. Attack");
                                            System.out.println("2. Disarm");
                                            System.out.println("3. Move");
                                            System.out.print("> ");
                                            cbans = obj2.nextInt();
                                            ci=1;
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input. Please enter Again.\n");
                                        }
                                        obj2.nextLine();
                                    }while(ci==0);
                                    if(cbans!=1 && cbans!=2 && cbans!=3){
                                        System.out.println("Invalid Input. Please enter Again.\n");
                                    }
                                    else if(cbans==1 && countdis1==0){ //Attack
                                        int ishit = player1.attack(player2);
                                        if(ishit != 0){
                                            System.out.println("Player 1 "+player1.creatureName+" has hit Player 2 "+player2.creatureName+" for "+ishit+" damage.");
                                        }
                                        else{
                                            System.out.println("Player 1 "+player1.creatureName+" has missed Player 2 "+player2.creatureName+".");
                                        }
                                        cb3=1;
                                        movPoint2 = 0;
                                    }
                                    else if(cbans==1 && countdis1!=0 || cbans==2 && countdis1!=0){
                                        System.out.println("Player 1 "+player1.creatureName+" is Disarmed!\n");
                                        cb3=1;
                                        movPoint2 = 0;
                                    }
                                    else if(cbans==2 && countdis2==0){ //Disarm
                                        boolean dis1 = Combat.isDisarmed(player1,player2);
                                        if(dis1){
                                            System.out.println("Player 2 "+player2.creatureName+" has been disarmed for 2 rounds.\n");
                                            countdis2 = cb+3;
                                        }
                                        else{
                                            System.out.println("Player 2 "+player2.creatureName+" could not be disarmed!\n");
                                        }
                                        cb3=1;
                                        movPoint2 = 0;
                                    }
                                    else if(cbans==2 && countdis2!=0){
                                        System.out.println("Player 2 "+player2.creatureName+" is already disarmed! Please Enter Again.\n");
                                    }
                                    else{ //Move
                                        if(movPoint2==0){
                                            int x=0;
                                            while(x==0){
                                                System.out.println("**25 BY 25 GRID**");
                                                System.out.print("Enter Co-ordinate to move (x,y): ");
                                                String coordinate = obj.nextLine();
                                                boolean iscoordinate = Map.isCoordinateEntry(coordinate);
                                                boolean scheck = false;
                                                boolean gcheck = false;
                                                boolean ismove = false;
                                                boolean bmove = false;
                                                String[] arr_in = coordinate.split("\\u002c", 3);
                                                arr_in[0] = arr_in[0].replace("(", ""); 
                                                arr_in[1] = arr_in[1].replace(")", ""); 
                                                int xx = Integer.parseInt(arr_in[0]);
                                                int yy = Integer.parseInt(arr_in[1]);
                                                if(iscoordinate){
                                                    scheck = Map.spaceNotOccupied(player2,coordinate);
                                                    gcheck = Map.NotOutofGrid(coordinate);
                                                    ismove = Map.isMovement(player1,coordinate);
                                                    bmove = Map.notAtBorderofGrid(xx,yy);
                                                    if(bmove==false){
                                                        System.out.println("Movement to border of grid is not allowed.");
                                                    }
                                                }
                                                if(iscoordinate && scheck && gcheck && ismove && bmove){
                                                    int initialx = player1.map.playerCoordinate_x;
                                                    int initialy = player1.map.playerCoordinate_y;
                                                    Map.changeCoordinatetoInt(player1,coordinate);
                                                    player1.map.playerCoordinate = coordinate;
                                                    mapGrid.changeCreatureposition_inMap(initialx,initialy,player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P1");
                                                    mapGrid.printMapGrid();
                                                    System.out.println("\n");
                                                    movPoint2 = 1;
                                                    x=1;
                                                }
                                                else{
                                                    System.out.println("Invalid Co-ordinate Entry!! Please Enter Again.\n");
                                                }
                                            }                                        
                                        }
                                        else{
                                            System.out.println("Players cannot move more than once in 1 round!\n");
                                        }
                                    }
                                }

                            }
                        }
                    }
                    
                    else if(p1_ans1==1){//Play with Random Monsters
                        MapGrid mapGrid = new MapGrid();
                        int movPoint = 0;
                        mapGrid.createMapGrid();
                        read_weaponinfo("saved/weapons.csv");
                        readMonsterData("saved/monsters.csv");
                        if(pvp==1){
                            PlayerCreatures.add(psave);
                            PlayerCreatures.add(psave2);
                            pvp = 0;
                        }
                        rollInitiative(PlayerCreatures);
                        rollInitiative(MonsterCreatures);

                        //Player1------------------------------------------------------
                        System.out.println("Player Choose Character: ");
                        int count=0,a=0;
                        a2=0;
                        p1_ans1=0;
                        for(a=0;a<PlayerCreatures.size();a++){
                            count = a+1;
                            System.out.println(count+"."+" "+PlayerCreatures.get(a).creatureName+","+" "+"STR: "+PlayerCreatures.get(a).STR+","+" "+"DEX: "+PlayerCreatures.get(a).DEX+","+" "+"CON: "+PlayerCreatures.get(a).CON+","+" "+"HP: "+PlayerCreatures.get(a).HP+","+" "+"AC: "+PlayerCreatures.get(a).AC+"  "+"created "+PlayerCreatures.get(a).creationDate);
                        }
                        while(a2==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans1 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans1>count || p1_ans1<=0){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            else{
                                a2=1;
                            }
                        }

                        System.out.println("\nPlayer Choose Weapon: ");
                        int count2=0,a3=0,a4=0,p1_ans2=0;
                        for(a3=0;a3<weaponNameList.size();a3++){
                            count2 = a3+1;
                            System.out.println(count2+"."+" "+weaponNameList.get(a3)+","+" "+"Damage: "+weaponDamageList.get(a3)+","+" "+"Bonus To-Hit: "+bonusDamageList.get(a3));
                        }
                        while(a4==0){
                            ci=0;
                            do{
                                try{
                                    System.out.printf("> ");
                                    p1_ans2 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(p1_ans2>count2 || p1_ans2<=0){
                                System.out.println("Invalid Input!! Please Enter Again");
                            }
                            else{
                                a4=1;
                            }
                        }
                        Player player1 = new Player();
                        player1 = (Player)PlayerCreatures.get(p1_ans1-1);
                        player1.initializeweapon();
                        player1.setWeaponName(weaponNameList.get(p1_ans2-1));
                        player1.setWeaponDamage(weaponDamageList.get(p1_ans2-1));
                        player1.setWeaponBonus(bonusDamageList.get(p1_ans2-1));
                        int pS_HP = player1.HP;
                        //-------------------------------------------------------------------------

                        //Generate Random Monsters----------------
                        ArrayList<Monster> RandomMonsters = new ArrayList<Monster>();
                        Random random = new Random(); 
                        int ap=0,pans1=0; 
                        while(ap==0){
                            ci=0;
                            do{
                                try{
                                    System.out.print("Select number of monsters to battle: ");
                                    pans1 = obj2.nextInt();
                                    ci=1;
                                }
                                catch(InputMismatchException e){
                                    System.out.println("Invalid Input!! Please Enter Again.");
                                }
                                obj2.nextLine();
                            }while(ci==0);
                            if(pans1<=0){
                                System.out.println("Invalid Input!! Please Enter Again.");
                            }
                            else if(pans1>30){
                                System.out.println("No more than 30 Monsters. Please Enter Again.");
                            }
                            else{
                                ap=1;
                            }
                        }
                        int gm=0;
                        for(gm=0;gm<pans1;gm++){
                            int ran = random.nextInt(MonsterCreatures.size()); 
                            RandomMonsters.add((Monster)MonsterCreatures.get(ran));
                            RandomMonsters.get(gm).MonsterID = gm+1;
                        }
                        //-----------------------------
                        player1.map.playerCoordinate_x = 4;
                        player1.map.playerCoordinate_y = 5;
                        player1.map.playerCoordinate = "(4,5)";
                        mapGrid.initailcreatureposition_inMap(player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P");

                        gm=0;
                        for(gm=0;gm<RandomMonsters.size();gm++){
                            int randx=0,randy=0;
                            boolean ch = false;
                            while(randx==0 || randy==0 && ch==false){
                                randx = random.nextInt(24);
                                randy = random.nextInt(24);
                                ch = Map.MonsterspaceNotOccupied(RandomMonsters,randx,randy); 
                                if(randx==4 && randy==5){
                                    ch = false;
                                }
                                else{
                                    continue;
                                }
                            }
                            RandomMonsters.get(gm).map.playerCoordinate_x = randx;
                            RandomMonsters.get(gm).map.playerCoordinate_y = randy;
                            mapGrid.initailcreatureposition_inMap(RandomMonsters.get(gm).map.playerCoordinate_x,RandomMonsters.get(gm).map.playerCoordinate_y,"M");
                        }
                        gm=0;
                        for(gm=0;gm<RandomMonsters.size();gm++){
                            String co = Map.changeCoordinatetoString(RandomMonsters.get(gm).map.playerCoordinate_x,RandomMonsters.get(gm).map.playerCoordinate_x);
                            RandomMonsters.get(gm).map.playerCoordinate = co;
                        }


                        //Player v Monster Combat ----------------------
                        int cb3=0;
                        while(cb3==0){//Player Turn First
                            int cbm=0;
                            mapGrid.printMapGrid();
                            System.out.print("\n");
                            if(player1.HP==0){
                                mapGrid.removecreaturePosition(player1.map.playerCoordinate_x,player1.map.playerCoordinate_y);
                                mapGrid.printMapGrid();
                                System.out.println("\nPlayer : "+player1.creatureName);
                                System.out.println("STR: "+player1.STR);
                                System.out.println("DEX: "+player1.DEX);
                                System.out.println("CON: "+player1.CON);
                                System.out.println("HP: "+player1.HP);
                                System.out.println("AC: "+player1.AC);
                                System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                System.out.println("  PLAYER "+" "+player1.creatureName+" "+"HAS BEEN DEFEATED\n");
                                player1.HP = pS_HP;
                                break;
                            }
                            int md=0;
                            for(md=0;md<RandomMonsters.size();md++){
                                if(RandomMonsters.get(md).HP==0){
                                    System.out.println("\nMonster : "+RandomMonsters.get(md).creatureName);
                                    System.out.println("STR: "+RandomMonsters.get(md).STR);
                                    System.out.println("DEX: "+RandomMonsters.get(md).DEX);
                                    System.out.println("CON: "+RandomMonsters.get(md).CON);
                                    System.out.println("HP: "+RandomMonsters.get(md).HP);
                                    System.out.println("AC: "+RandomMonsters.get(md).AC);
                                    System.out.println("Co-ordinates: "+RandomMonsters.get(md).map.playerCoordinate+"\n");
                                    System.out.println("MONSTER "+RandomMonsters.get(md).creatureName+" HAS BEEN DEFEATED\n");
                                    mapGrid.removecreaturePosition(RandomMonsters.get(md).map.playerCoordinate_x,RandomMonsters.get(md).map.playerCoordinate_y);
                                    RandomMonsters.remove(md);
                                }
                            }
                            if(RandomMonsters.size()==0){
                                mapGrid.printMapGrid();
                                System.out.println("***ALL MONSTERS HAVE BEEN DEFEATED.***");
                                System.out.println("  PLAYER "+" "+player1.creatureName+" "+"IS VICTORIOUS.\n");
                                player1.HP = pS_HP;
                                break;
                            }
                            
                            while(cbm==0){
                                //Printing Player Info-------
                                System.out.println("\nPlayer : "+player1.creatureName);
                                System.out.println("STR: "+player1.STR);
                                System.out.println("DEX: "+player1.DEX);
                                System.out.println("CON: "+player1.CON);
                                System.out.println("HP: "+player1.HP);
                                System.out.println("AC: "+player1.AC);
                                System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                ci=0;
                                int cbans =0;
                                do{
                                    try{
                                        System.out.println("***Player can only move before Attcking***");
                                        System.out.println("Player choose: ");
                                        System.out.println("1. Attack");
                                        System.out.println("2. Move");
                                        System.out.print("> ");
                                        cbans = obj2.nextInt();
                                        ci=1;
                                    }
                                    catch(InputMismatchException e){
                                        System.out.println("Invalid Input. Please enter Again.\n");
                                    }
                                    obj2.nextLine();
                                }while(ci==0);
                                if(cbans!=1 && cbans!=2){
                                    System.out.println("Invalid Input. Please Enter Again.\n");
                                }
                                else if(cbans==1){ //Attack
                                    System.out.println("Player choose Monster to attack: ");
                                    int Mcount =0,mi=0;
                                    movPoint=0;
                                    cbm = 1;
                                    int cbansaM=0;
                                    for(mi=0;mi<RandomMonsters.size();mi++){
                                        Mcount = mi+1;
                                        System.out.println(Mcount+"."+" "+"Monster "+Mcount+": "+RandomMonsters.get(mi).creatureName+", "+"HP: "+RandomMonsters.get(mi).HP+", "+"STR: "+RandomMonsters.get(mi).STR+", "+"DEX: "+RandomMonsters.get(mi).DEX+", "+"CON: "+RandomMonsters.get(mi).CON+", "+"AC: "+RandomMonsters.get(mi).AC+" Co-ordinates: "+RandomMonsters.get(mi).map.playerCoordinate);
                                    }
                                    ci=0;
                                    do{
                                        try{
                                            System.out.print("> ");
                                            cbansaM = obj2.nextInt();
                                            if(cbansaM>Mcount){
                                                System.out.println("Invalid Input. Please Enter Again.");
                                            }
                                            else{
                                                ci=1;
                                            }
                                        }
                                        catch(InputMismatchException e){
                                            System.out.println("Invalid Input. Please Enter Again.");
                                        }
                                        obj2.nextLine();
                                    }while(ci==0);
                                    int ishit = player1.attack(RandomMonsters.get(cbansaM-1));
                                    if(ishit != 0){
                                        System.out.println("Player "+player1.creatureName+" has hit Monster "+cbansaM+": "+RandomMonsters.get(cbansaM-1).creatureName+" for "+ishit+" damage.");
                                    }
                                    else{
                                        System.out.println("Player "+player1.creatureName+" has missed Monster "+cbansaM+": "+RandomMonsters.get(cbansaM-1).creatureName+".");
                                    }
                                }
                                else{ //Move
                                    if(movPoint==0){
                                        int x=0;
                                        while(x==0){
                                            System.out.println("**25 BY 25 GRID**");
                                            System.out.print("Enter Co-ordinate to move (x,y): ");
                                            String coordinate = obj.nextLine();
                                            boolean iscoordinate = Map.isCoordinateEntry(coordinate);
                                            boolean scheck = false;
                                            boolean gcheck = false;
                                            boolean ismove = false;
                                            boolean bmove = false;
                                            if(iscoordinate){
                                                String[] arr_in = coordinate.split("\\u002c", 3);
                                                arr_in[0] = arr_in[0].replace("(", ""); 
                                                arr_in[1] = arr_in[1].replace(")", ""); 
                                                int xx = Integer.parseInt(arr_in[0]);
                                                int yy = Integer.parseInt(arr_in[1]);
                                                scheck = Map.MonsterspaceNotOccupied(RandomMonsters,xx,yy);
                                                if(scheck==false){
                                                    System.out.println("There is Monster at that position!!");
                                                }
                                                gcheck = Map.NotOutofGrid(coordinate);
                                                ismove = Map.isMovement(player1,coordinate);
                                                bmove = Map.notAtBorderofGrid(xx,yy);
                                                if(bmove==false){
                                                    System.out.println("Movement to border of grid is not allowed.");
                                                }
                                            }
                                            if(iscoordinate && scheck && gcheck && ismove && bmove){
                                                int initialx = player1.map.playerCoordinate_x;
                                                int initialy = player1.map.playerCoordinate_y;
                                                Map.changeCoordinatetoInt(player1,coordinate);
                                                player1.map.playerCoordinate = coordinate;
                                                mapGrid.changeCreatureposition_inMap(initialx,initialy,player1.map.playerCoordinate_x,player1.map.playerCoordinate_y,"P");
                                                mapGrid.printMapGrid();
                                                System.out.println("\n");
                                                movPoint = 1;
                                                x=1;
                                            }
                                            else{
                                                System.out.println("Invalid Co-ordinate Entry!! Please Enter Again.\n");
                                            }
                                        }
                                    }
                                    else{
                                        System.out.println("Players cannot move more than once in 1 round!\n");
                                    }
                                    
                                }
                            }
                            //Monster--------------------------------
                            System.out.print("\n");
                            if(player1.HP==0){
                                mapGrid.removecreaturePosition(player1.map.playerCoordinate_x,player1.map.playerCoordinate_y);
                                mapGrid.printMapGrid();
                                System.out.println("\nPlayer : "+player1.creatureName);
                                System.out.println("STR: "+player1.STR);
                                System.out.println("DEX: "+player1.DEX);
                                System.out.println("CON: "+player1.CON);
                                System.out.println("HP: "+player1.HP);
                                System.out.println("AC: "+player1.AC);
                                System.out.println("Co-ordinates: "+player1.map.playerCoordinate+"\n");
                                System.out.println("  PLAYER "+" "+player1.creatureName+" "+"HAS BEEN DEFEATED\n");
                                player1.HP = pS_HP;
                                break;
                            }
                            md=0;
                            for(md=0;md<RandomMonsters.size();md++){
                                if(RandomMonsters.get(md).HP==0){
                                    System.out.println("\nMonster : "+RandomMonsters.get(md).creatureName);
                                    System.out.println("STR: "+RandomMonsters.get(md).STR);
                                    System.out.println("DEX: "+RandomMonsters.get(md).DEX);
                                    System.out.println("CON: "+RandomMonsters.get(md).CON);
                                    System.out.println("HP: "+RandomMonsters.get(md).HP);
                                    System.out.println("AC: "+RandomMonsters.get(md).AC);
                                    System.out.println("Co-ordinates: "+RandomMonsters.get(md).map.playerCoordinate+"\n");
                                    System.out.println("MONSTER "+RandomMonsters.get(md).creatureName+" HAS BEEN DEFEATED\n");
                                    mapGrid.removecreaturePosition(RandomMonsters.get(md).map.playerCoordinate_x,RandomMonsters.get(md).map.playerCoordinate_y);
                                    RandomMonsters.remove(md);
                                }
                                else{
                                    continue;
                                }
                            }
                            if(RandomMonsters.size()==0){
                                mapGrid.printMapGrid();
                                System.out.println("***ALL MONSTERS HAVE BEEN DEFEATED.***");
                                System.out.println("  PLAYER "+" "+player1.creatureName+" "+"IS VICTORIOUS.\n");
                                player1.HP = pS_HP;
                                break;
                            }
                            int mx=0;
                            for(mx=0;mx<RandomMonsters.size();mx++){
                                boolean inrange = Map.inMonsterRange(player1,RandomMonsters.get(mx));
                                if(inrange){
                                    int isatt = RandomMonsters.get(mx).attack(player1);
                                    if(isatt!=0){
                                        System.out.println("\nMonster "+(mx+1)+": "+RandomMonsters.get(mx).creatureName+" has hit Player "+player1.creatureName+" for "+isatt+" damage.");
                                    }
                                    else{
                                        System.out.println("\nMonster "+(mx+1)+": "+RandomMonsters.get(mx).creatureName+" has missed Player "+player1.creatureName);
                                    }
                                }
                                else{
                                    int ini_x = RandomMonsters.get(mx).map.playerCoordinate_x;
                                    int ini_y = RandomMonsters.get(mx).map.playerCoordinate_y;
                                    Map.MonstermovetowardsPlayer(player1,RandomMonsters.get(mx));
                                    mapGrid.changeCreatureposition_inMap(ini_x,ini_y,RandomMonsters.get(mx).map.playerCoordinate_x,RandomMonsters.get(mx).map.playerCoordinate_y,"M");
                                }

                            }

                        }

                    }

                }
                
            }
            //--------------------------------

        }

    }

}


















