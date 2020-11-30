package MainPkg;//package src;

import MainPkg.SlangList;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap;
import java.io.*;
//for converting hash map into json


public class main {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        HashMap<String, String> capitalCities = new HashMap<String, String>();
        long start = System.currentTimeMillis();


        try {
            File myObj = new File("/Users/thiennguyen/HocDi/java_01/slang.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                if (data.contains("`")) {
                    String[] arrOfStr = data.split("`", 2);
                    int i = 0;
                    while (capitalCities.get(arrOfStr[i] + "_" + i) != null) {
                        i += 1;
                    }
                    capitalCities.put(arrOfStr[0] + "_" + i, arrOfStr[1]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        SlangList e = new SlangList();
        System.out.print("Serialized data is saved in /tmp/employee.ser");
        e.capitalCities = capitalCities;
        try {

            FileOutputStream fileOut =
                    new FileOutputStream("/Users/thiennguyen/HocDi/java_01/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        SlangList e1 = null;
        try {
            FileInputStream fileIn = new FileInputStream("/Users/thiennguyen/HocDi/java_01/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e1 = (SlangList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Read list form file...");
        System.out.println(e1.capitalCities.get("CINO_0"));
        String state = "menu";
        while (true) {
            switch (state) {
                case "menu":
                    System.out.println("18127270 - Slang word menu");
                    System.out.println("input the command in \"\" to use");
                    System.out.println("\"key\" - Search by key    \"mean\" - search by meaning");
                    System.out.println("\"history\" - Show history    \"mean\" - search by meaning");
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter the command: ");
                    state = sc.nextLine();
                case "key":
                    Scanner keyScan = new Scanner(System.in);
                    System.out.print("Enter the key: ");
                    String searchKey = keyScan.nextLine();
                    int i = 0;
//                    System.out.println(searchKey + "_" + i);
                    while (e1.capitalCities.get(searchKey + "_" + i) != null) {
                        System.out.println( ">>> " + ANSI_BLUE + searchKey + ANSI_RESET + ANSI_YELLOW + " : " + ANSI_YELLOW + ANSI_BLUE + e1.capitalCities.get(searchKey + "_" + i) + ANSI_RESET );
                        i+=1;
                    }
                    if (i == 0) {
                        System.out.println(ANSI_RED + "No slang word for \"" + searchKey + "\"" + ANSI_RESET);
                    }
            }
        }



    }
}
