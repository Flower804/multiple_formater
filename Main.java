//this project will first focus on a CLI program and after it will focus on the GUI and all that pretty stuff
//file: Main.java
/* I know I'm using a deprecated method but fuck it, I passed more than 10 hours writing this code in a older 
    version of java because I forgot to update my java version and I make it work
   And I'm wayyyyy to deep to change the code to a new method because like the WHOLE code revolves around that method
   So like, just don't coment on it please, what matters is that it works in the end of the day :,)
ASS: Flower <3
   */

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
        //default stuff
        //linux_script_writer("GabrielMoita_129", "sdb1");
        if((System.getProperty("os.name") == "windows")){
            script_starter(1);
        } else {
            script_starter(2);
        }
    }

    public static void script_starter(int os){ //os == 1 -> windows
        System.out.print("\033[H\033[2J");     //os == 2 -> Unix
        System.out.flush();
        Scanner myObj = new Scanner(System.in);
        try{
            System.out.println("welcome to this version, eventually there will be a GUI but we're still not there \n\n-Format pen-drive: 1\nFormat Floppy-Disk: 2(not working)");
            int user_choice;

            user_choice = myObj.nextInt();
            if(user_choice == 1){
                System.out.print("\033[H\033[2J");
                System.out.flush();
                //TODO: adapt this for the volume catcher
                if(os == 1){ //user is in windows
                    volume_catcher(os, null);
                }else{ //other operating sistem
                    String sudo_password;
                    Scanner password = new Scanner(System.in);
                    try{
                        System.out.println("please insert your sudo password");
                        sudo_password = password.nextLine();
                        volume_catcher(os, sudo_password);
                        //linux_script_writer(sudo_password, "volume");
                    } catch(Exception e){
                        System.out.println("Sorry something went wrong");
                    }
                    password.close();
                    //Linux thingy that i did yesterday
                }        
            } else {
                System.out.println("sorry invalid Input please try again");
            }
            myObj.close();
        } catch(Exception e) {
            System.out.println("something went wrong");
            script_starter(os); //try to avoid program breaking out on us
        }
    }


    public static void endder(){
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();

        System.out.println("The volumes have been formatted succesfully :D");
    }

    //====================================================================================================
    //==========================================WINDOWS AREA==============================================
    //====================================================================================================


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
    public static void volume_catcher(int os, String sudopassword){
        System.out.println("how many volumes would you like to format?");
        Scanner myObj = new Scanner(System.in);
        int format_number = myObj.nextInt();

        String[] volumes_to_format = {"", "", "", "", ""};

        Scanner volumes_to_get = new Scanner(System.in);
        for(int i = 0; i <= (format_number - 1); i++) {
            String user_volume;

            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();

            System.out.println((i + 1) + "volume out of: " + format_number + "\n");

            try{
                if(os == 1){ //user is using windows
                    Process powerShellProcess = Runtime.getRuntime().exec(new String[]{"powershell.exe", "get-Volume"}); //
                    powerShellProcess.getOutputStream().close();
        
                    BufferedReader stdout = new BufferedReader(new InputStreamReader(powerShellProcess.getInputStream()));

                    String line;
                    while((line = stdout.readLine()) != null) {
                        System.out.println(line);
                    }
                    stdout.close();
                } else {
                    Process bashProcess = Runtime.getRuntime().exec(new String[]{"bash", "-c", "df"});
                    BufferedReader stdout = new BufferedReader(new InputStreamReader(bashProcess.getInputStream()));

                    String line;
                    while((line = stdout.readLine()) != null) {
                        System.out.println(line);
                    }
                    stdout.close();
                }
            } catch(IOException e){
                System.out.println("an error has occured in getting Volumes");
                e.printStackTrace();
            }
            System.out.println("What Volume do you want to format?");
            System.out.println("-> ");

            user_volume = volumes_to_get.nextLine();

            if(os == 1){
                if(!(user_volume.indexOf(':') == 1)){
                    user_volume.concat(":");
                }
            } else { //Thank you Guido for helping me with this T_T
                //int currentMatch = 0; //TODO: acording to Guido this can be optimized, need to look at that later
                //int current = 0;
                //while(currentMatch != "/dev/".length() && current < user_volume.length()){
                //    if("/dev/".charAt(current) == user_volume.charAt(currentMatch)){
                //        currentMatch++;
                //    }
                //    current++;
                //}
                //if(!(currentMatch == "/dev/".length())){
                //    user_volume = "/dev/".concat(user_volume);
                //}
            }
            volumes_to_format[i] = user_volume;

        }
        System.out.print("\033[H\033[2J"); //clear terminal  
        System.out.flush();

        System.out.println("Do you want to continue the operation with default settings?");
        System.out.println("Type \"yes\" or \"no\" ");
        int flag = confirmation();
        if(flag == 0){
            change_default();

            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
        } else if(flag == 1){
            System.out.println("operation continued with default settings");
        }

        System.out.println("do you want to format this volumes?");

        for(int i = 0; i <= (format_number - 1); i++){
            System.out.println(volumes_to_format[i] );
        }

        System.out.println("\n write \"yes\" or \"no\" ");

        if(confirmation() == 1){
            System.out.println("entered format sequence");
            for(int j = 0; j <= (format_number - 1); j++){
                if(os == 1){ //assuming windows -> write_the_script
                    String user_volume = volumes_to_format[j]; 
                    
                    //prepare volume to be of format "F:" for example
                    String pre_gon = "\"";
                    String pre_final_gon = pre_gon.concat(user_volume);
                    
                    //System.out.println(pre_final_gon.concat(pre_gon));
                    write_the_script(pre_final_gon.concat(pre_gon)); //Send volume to format sequence
                } else {
                    System.out.println("sudo pass" + sudopassword);
                    System.out.println("volume entering" + volumes_to_format[j]);
                    linux_script_writer(sudopassword, volumes_to_format[j]);
                }    
            }
        }
        myObj.close();
    }

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

            System.out.println("Formatting the volumes... ");


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
                delete_script("erase_script.ps1");
                create_script();
            }
        } catch (IOException e) {
            System.out.println("An error occurred in script creator");
            e.printStackTrace();
        }
    }
    public static void delete_script(String script){
        File myObj = new File(script);
        if(myObj.delete()){
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
    
    //====================================================================================================
    //==========================================LINUX AREA================================================
    //====================================================================================================

    public static void linux_script_writer(String sudo_pass, String user_volume){
        //System.out.print("\033[H\033[2J"); //clear terminal  
        //System.out.flush();
        ////example for fat32
        String[] linux_script = {"#!/bin/bash \n\n",
                                 "\ndevice_name =", "sdb1",
                                 "\n\necho ", "password", " | sudo -S -k umount /dev/", "volume",
                                 "\necho ", "password", "| sudo -S -k mkfs.vfat /dev/", "volume"};
        String[] password = {"\"", "password", "\""};
        password[1] = sudo_pass;
        String string_password = String.join(",", password);
        string_password.replace(",", "");  
        linux_script[4] = string_password;
        linux_script[8] = string_password;
        linux_script[2] = user_volume;
        linux_script[6] = user_volume;
        linux_script[10] = user_volume;

        String final_script = String.join(",", linux_script);
        try{
            FileWriter myWriter = new FileWriter("linux_erase_script.sh");
            myWriter.write(final_script.replace(",", ""));
            myWriter.close(); //save file
        } catch(IOException e){
            System.out.println("oidhawoiudhg");
        }
        
        try{
            String[] command = {"/bin/sh ", "linux_erase_script.sh"};
            String  string_command = String.join(",", command);

            System.out.println(string_command.replace(",", "")); //the problem is in runing the script, the command is not the issue
            Runtime.getRuntime().exec(string_command.replace(",", " "));
        } catch(IOException e) {
            System.out.println("ahsdu");
        }
        //This will eventually work
        //Somehow it always works in the end

        //delete_script("linux_erase_script.sh"); //delete script for security/privacy reasons
    }
}