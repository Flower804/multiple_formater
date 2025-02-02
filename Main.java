//this project will first focus on a CLI program and after it will focus on the GUI and all that pretty stuff
//file: Main.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
//things for pwshell
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args){
        //change_default();
        script_starter();
    }

    public static void script_starter(){
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();
        Scanner myObj = new Scanner(System.in);
        try{
            System.out.println("welcome to this version that will futury have a GUI\n\n-Format pen-drive: 1 \n\n-Format Floppy-Disk: 2 (not working) \n");
            int user_choice;

            user_choice = myObj.nextInt();
            if(user_choice == 1){ 
                System.out.println("Start pen_drive format sequence");
                System.out.print("\033[H\033[2J"); //clear terminal  
                System.out.flush(); 
                pen_drive_format_preparation();
                //write_the_script(volume); //Start the pen_drive format sequence
            } else {
                System.out.println("wrong Input please try again");
                script_starter();
            }

        } catch(Exception e){
            System.out.println("something went wrong");
                
                script_starter();
        }
        myObj.close();
    }

    public static void endder(){
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();

        System.out.println("The volumes have been formatted succesfully :D");
    }

    /*
     *  /l          - format for single-sided use
     *  /4          - formates a fouble-density disketter
     *  /8          - GOrmats at 8 sector per tracks
     *  /F:(size)   - Formats the dislet to a size other than for what the drive was designed
     *  /N:(sectors)- Specifies the number of secors on the disk. Used to format a 3 1/2* disk with the number of secotrs per track. For both 720KB disks, the value should be N:9
     *  /T:(tracks) - Specifies the number of secors on the disk. Used to format a 3 1/2* disk with the number of secotrs per track. For both 720KB disks and 1.44MB disks, the value should be T:80
     * 
     * 
     *  examples
     *      format a:               -The disk in floppy Drive A: will be formatted, and it will not be bootable
     *      format a: /S            -The disk in floppy drive A: will be formatted and the operating system files will be copied onto the disk, turning it into a bootable disk
     *      format a: /4            -The floppy disk drive A: will be formated to double-density capacity iun this high-density drive
     *      format b: /T:80 /N:9    -The floy disk in drive B: will be formatted to 720KB (80 tracks, 9 secotrs per track)
     */
    //===================================USER PREPARATION AREA============================================

    //====================================================================================================
    //===================================USB-Preaparation PART============================================
    //====================================================================================================

    /*
     * File System   Windows 7/8     Windows10/11    MacOs   Ubunto  Linux
     * NTFS             YES             YES           YES     YES     YES
     * FAT32            YES             YES           YES     YES     YES
     * exFAT            YES             YES           YES     YES     YES
     * HFS+ (Isn't worth it not compatyble)
     * APFS (Isn't worth it not compatyble)
     * EXT2, 3, 4 (Isn't worth it not compatyble)
     */
    public static void pen_drive_format_preparation(){
        
        System.out.println("how many volumes would you like to format?");
        Scanner myObj = new Scanner(System.in);
        int format_number = myObj.nextInt();

        String[] volumes_to_format = {" ", "", "", "", "", "", "", "", "", ""}; //create final String[] so you can "parse" the choosen volumes from the user 

        Scanner volume_to_get = new Scanner(System.in);
        for(int i = 0; i <= (format_number-1); i++){
            String user_volume;

            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            
            System.out.println((i +1) + " volume out of: " + format_number + "\n"); //show the user on where he is in the loop
            try{
                Process powerShellProcess = Runtime.getRuntime().exec("powershell.exe get-Volume"); //execute command to get pc's volume list
                //ProcessBuilder powerShellProcess = new ProcessBuilder("powershell.exe get-Volume");

                powerShellProcess.getOutputStream().close();

                String line;
                BufferedReader stdout = new BufferedReader(new InputStreamReader(
                  powerShellProcess.getInputStream()));
                while ((line = stdout.readLine()) != null) { //TODO: find a way to analise the line got and find a way to only show the ones that have the tag - Removable
                 System.out.println(line);
                }
                stdout.close();

            } catch(IOException e) {
                System.out.println("an error has ocurred in pre-formater USB");
                e.printStackTrace();
            }
            System.out.println("What Volume do you want to format");
            System.out.println("-> ");
            
        
            user_volume = volume_to_get.nextLine(); //get user's intended volume

            if(!(user_volume.indexOf(':') == 1)){ //verify the volume specified is in the F: format if not force it F -> F:
                user_volume.concat(":");
            }
            
            volumes_to_format[i] = user_volume; //add volume to volume list

        }
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();

        System.out.println("Do you want to change the configuration options? \n\nif you don't change it will continue with default\n\nwrite \"yes\" or \"no\" ");
        int flag = confirmation();
        if(flag == 1){
            change_default(); //go to the change default configs thingy

            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
        } else if(flag == 0){
            System.out.println("operation continued with default settings");
        }

        System.out.println("do you want to format this volumes? \n");
        
        for(int i = 0; i <= (format_number - 1); i++){ //print out the volumes selected by the user
            System.out.println(volumes_to_format[i] );
        }

        System.out.println("\n write  \"yes\" or \"no\" ");

        if(confirmation() == 1){
            System.out.println("enteres format sequence");
            for(int j = 0; j <= (format_number - 1); j++){
                String user_volume = volumes_to_format[j]; 

                //prepare volume to be of format "F:" for example
                String pre_gon = "\"";
                String pre_final_gon = pre_gon.concat(user_volume);

                //System.out.println(pre_final_gon.concat(pre_gon));
                write_the_script(pre_final_gon.concat(pre_gon));    //send volume to formater sequence
            }
        }
        myObj.close();
        volume_to_get.close();
    }
    //====================================   CONFIGS   ===================================================
    public static void change_default() {
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();
        
        String[] current_default = {"", "\n", ""}; //create string that will hold the configs in the default_config.txt file
        String backup_default = "powershell\nexfat"; //backup default configs in case the user fucks up
                                                     //HEY BUDDY yes YOU that is reading the code, unless you KNOW WHAT YOU'RE DOING don't mess with this
        try{
            Scanner object = new Scanner(new File("default_config.txt"));
            
            System.out.println("current loaded configs \n");
            
            String name = object.nextLine();
            String filesystem = object.nextLine();

            current_default[0] = name; //add the current def for volume name to the current config string
            current_default[2] = filesystem; //add the current def for FileSystem to the current config string
        } catch(FileNotFoundException e){
            System.out.println("issue loading default config file");
        }
        File myObj = new File("default_config.txt");

        System.out.println("\n what do you wanna change? \n1:Name \n2:FileSystem \n3:restore default");
        
        String new_string_default;
        Scanner choice = new Scanner(System.in);
        int answer = choice.nextInt();
        
        if(answer == 1){ //change the default name on the configs
            String name = ""; //create temp string to hold the new name choosen by the user
            
            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            
            System.out.println("\n Please enter new name");
            
            Scanner new_name = new Scanner(System.in);
            
            name = new_name.nextLine(); //get new name

            current_default[0] = name; //change the old name for the new name in the default config string
            myObj.delete();
            try{
                FileWriter new_defaults = new FileWriter("default_config.txt");
                
                new_string_default = String.join(",", current_default);
                
                new_defaults.write(new_string_default.replace(",", "")); //write default config 
                new_defaults.close();
            } catch(IOException e){
                System.out.println("an error has ocurred in writer");
            }
        } else if(answer == 2){ //change the default FileSystem on the configs
            String filesystem = ""; //create temp string that will hold new value for the FileSystem
            
            Scanner new_fileSystem = new Scanner(System.in);
            
            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            
            System.out.println("please write one of the following: NTFS, FAT32, exFAT");
            
            filesystem = new_fileSystem.nextLine();
            
            current_default[2] = filesystem; //change the default value of the FileSystem for a new one
            myObj.delete();
            try{
                FileWriter new_defaults = new FileWriter("default_config.txt");
                
                new_string_default = String.join(",", current_default);
                
                new_defaults.write(new_string_default.replace(",", "")); //write new configs
                new_defaults.close();
            } catch(IOException e){
                System.out.println("an error has ocurred in writer");
            }
        } else if(answer == 3){ //the user regreted adventuring and decided to load the default configs that come with the program
            try{
                FileWriter new_defaults = new FileWriter("default_config.txt");
                
                new_defaults.write(backup_default);
                new_defaults.close();
            } catch(IOException e) {
                System.out.println("an error has ocurred in writer");
            }
        }
    }


    //===================================FORMATTER AREA===================================================


    public static int confirmation(){
        Scanner myObj = new Scanner(System.in);
        String answer = "";

        answer = myObj.nextLine();
        if(answer.equals("yes")){ //for some reason there is a bug when comparing strings with "==" so it's better to use .equals("object")
            System.out.println(" got 1");
            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            return 1;
        } else if(answer.equals("no")){
            System.out.println(" got 0");
            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            return 0;
        } else {
            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.println("sorry smth bad happened \nplease write yes or no");
            confirmation();
        }
        return 0;
    }

    //====================================================================================================
    //==============================Floppy-Disk FORMATTER PART============================================
    //====================================================================================================

    public static void formater_Floppy(String[] arguments){  //Format function to format floppy diskls
        try{
            String[] command = {"cmd.exe", "/c", "format"};

            //join two arrays
            int first_array = command.length;
            int second_array = arguments.length;

            int total_size = first_array + second_array;

            String[] total_array = new String[total_size];

            System.arraycopy(command, 0, total_array, 0, first_array);
            System.arraycopy(arguments, 0, total_array, first_array, second_array);

            System.out.println("" + Arrays.toString(total_array));
            

            Process proc = Runtime.getRuntime().exec(total_array);
            
            //read the output

            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            String[] confirm = {"call", "Main", "main", "{ENTER}"};
            while((line = reader.readLine()) != null){ //TODO: find a way to do a list of errors/analyse the outputs properly
                System.out.print(line + "\n");
                proc = Runtime.getRuntime().exec(confirm);
            }
            System.out.println("\r");

            proc.waitFor();
        }catch(InterruptedException | IOException ex){
            System.out.println("Well the formater is fuc#d");
            System.out.println(ex);
        }
    }

    //====================================================================================================
    //==============================PEN-DRIVE FORMATTER PART==============================================
    //====================================================================================================

    /*Pen-drive format sequence
        1- write script
        2- create script
        3- delete script
        4- create script
        5- write script
        6- run script
    */
    public static void run_script(){
        //System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String current_dir = System.getProperty("user.dir"); //get current directory of the code
        //to bypass system security -> powershell -ExecutionPolicy Bypass -File script.ps1
        //total command would to script -> powershell.exe -ExecutionPolicy Bypass "& ""dir"
        System.out.println(current_dir + "\n");

        String script_to_run = "\\erase_script.ps1\""; //created script name to concate to directory
        String final_to_conclude = current_dir.concat(script_to_run);
        String command = "powershell.exe -ExecutionPolicy Bypass \"& \"";

        System.out.println(command.concat(final_to_conclude)); //print out final command for the debugging purposses 
        try{
            Process powerShellProcess = Runtime.getRuntime().exec(command.concat(final_to_conclude)); //execute final command

            powerShellProcess.getOutputStream().close();

            //comments here used for debuging 
            //String line;
            System.out.println("Formatting the volumes... ");
            //BufferedReader stdout = new BufferedReader(new InputStreamReader(
            //powerShellProcess.getInputStream()));
            //while ((line = stdout.readLine()) != null) {
            // System.out.println(line);
            //}
            //stdout.close();

        } catch(IOException e) {
            System.out.println("An error has occured in script runner");
            e.printStackTrace();
        }
    }

    public static void write_the_script(String user_volume){ //first
        create_script();
        //original string -> String script = "ipmo storage \n$source =\"C:\\Data\\PowerShellWorkshop\"\n$destination = \"F:\" \nFormat-Volume -DriveLetter $destination -NewFileSystemLabel powershell -FileSystem exfat -Confirm:$false \nrobocopy $source $destination /S \n[media.SystemSounds]::(\"Hand\").play()";
        //code to be run in powershell
        String[] script = {"ipmo storage \n",
                         "$source = \"C:\\Data\\PowerShellWorkshop\" \n", 
                         "$destination = ",
                         "\"not definet\" ",
                         "\n", 
                         "Format-Volume -DriveLetter $destination -NewFileSystemLabel ",
                         "volume_name", 
                         " -FileSystem ",
                         "exfat", 
                         " -Confirm:$false \n",
                         "robocopy $source $destination /S \n",
                         "[media.SystemSounds]::(\"Hand\").play()"};
        script[3] = user_volume;
        String path = "default_config.txt";
        try{
            Scanner file_reader = new Scanner(new File(path));
            script[6] = file_reader.nextLine();
            script[8]= file_reader.nextLine();

        } catch(FileNotFoundException e){
            System.out.println("default config file not found");
        }
        
        

        String final_string = String.join(",", script); //convert final array into finall string

        try{
            FileWriter myWriter = new FileWriter("erase_script.ps1");
            myWriter.write(final_string.replace(",", ""));
            myWriter.close();
        } catch(IOException e){
            System.out.println("An error has occurred in writer");
            e.printStackTrace();
        }
        run_script();
    }

    public static void create_script(){
        try{
            File myObj = new File("erase_script.ps1");
            if(myObj.createNewFile()){
                System.out.println("File created: " + myObj.getName());
            } else { //script already exists delete it and try again
                delete_script();
            }
        } catch (IOException e) {
            System.out.println("An error occurred in script creator");
            e.printStackTrace();
        }
    }
    public static void delete_script(){
        File myObj = new File("erase_script.ps1");
        if(myObj.delete()){
            System.out.println("Deleted the file: " + myObj.getName());
            create_script();
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
    
}