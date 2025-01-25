//this project will first focus on a CLI program and after it will focus on the GUI and all that pretty stuff
//file: Main.java

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        try{
            String command = "mkdir test";

            Process proc = Runtime.getRuntime().exec(command);
            


            proc.waitFor();
        }catch(InterruptedException e; IOException i){
            System.out.println("Well the program is fuc#d");
        }

    } 
}
