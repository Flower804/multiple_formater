//this project will first focus on a CLI program and after it will focus on the GUI and all that pretty stuff
//file: Main.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
//things for pwshell
import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args){
        script_starter();
        //pen_drive_format_preparation();
        endder();
    }

    public static void script_starter(){
        //String[] arguments_floppy = {"a:"};
        //String volume = "\"F:\""; //volume for the pendrive
//
        //formater_Floppy(arguments_floppy);
        //
        //write_the_script(volume);
        Scanner myObj = new Scanner(System.in);
        try{
            System.out.println("welcome to this version that will futury have a GUI\n Format pen-drive: 1 \n Format Floppy-Disk: 2 (not working) \n");
            int user_choice;

            user_choice = myObj.nextInt();
            if(user_choice == 1){ //TODO: fix the problem where if you put any other number the program freaks out
                System.out.println("Start pen_drive format sequence");
                System.out.print("\033[H\033[2J"); //clear terminal  
                System.out.flush(); 
                pen_drive_format_preparation();
                //write_the_script(volume); //Start the pen_drive format sequence
            } else {
                System.out.println("wrong Input please try again");
                myObj.close();
                script_starter();
            }

            myObj.close();
        } catch(Exception e){
            System.out.println("something went wrong");
            myObj.close();
            script_starter();
        }
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

    public static void pen_drive_format_preparation(){
        
        System.out.println("how many volumes would you like to format?");
        Scanner myObj = new Scanner(System.in);
        int format_number = myObj.nextInt();

        String[] volumes_to_format = {" ", "", ""}; //create final String[] so you can "parse" the choosen volumes from the user 

        Scanner volume_to_get = new Scanner(System.in);
        for(int i = 0; i <= (format_number-1); i++){
            String user_volume;

            System.out.print("\033[H\033[2J"); //clear terminal  
            System.out.flush();
            
            System.out.println((i +1) + "volume out of: " + format_number + "\n"); //show the user on where he is in the loop
            try{
                Process powerShellProcess = Runtime.getRuntime().exec("powershell.exe get-Volume"); //execute command to get pc's volume list

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
        volume_to_get.close();
        for(int j = 0; j <= (format_number - 1); j++){
            String user_volume = volumes_to_format[j]; 

            //prepare volume to be of format "F:" for example
            String pre_gon = "\"";
            String pre_final_gon = pre_gon.concat(user_volume);

            //System.out.println(pre_final_gon.concat(pre_gon));
            write_the_script(pre_final_gon.concat(pre_gon));    //send volume to formater sequence
        }
        myObj.close();
    }
    //===================================FORMATTER AREA===================================================


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
                         "Format-Volume -DriveLetter $destination -NewFileSystemLabel powershell -FileSystem exfat -Confirm:$false \n",
                         "robocopy $source $destination /S \n",
                         "[media.SystemSounds]::(\"Hand\").play()"};
        script[3] = user_volume;

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