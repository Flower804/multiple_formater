//this project will first focus on a CLI program and after it will focus on the GUI and all that pretty stuff
//file: Main.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        String[] arguments = {"echo", "this is working"};

        formater_Floppy(arguments);        

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
    public static void formater_Floppy(String[] arguments){ 
        try{
            String[] command = {"cmd.exe", "/c"};

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
            while((line = reader.readLine()) != null){
                System.out.print(line + "\n");
            }

            proc.waitFor();
        }catch(InterruptedException | IOException ex){
            System.out.println("Well the program is fuc#d");
            System.out.println(ex);
        }
    }

    
}
