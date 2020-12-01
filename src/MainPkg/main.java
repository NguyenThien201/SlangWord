package MainPkg;//package src;

import MainPkg.SlangList;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.List;
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

        long start = System.currentTimeMillis();


        SlangList slangList = new SlangList();
        slangList.loadData();

        String state = "menu";

        while (true) {
            switch (state) {
                case "menu":
                    System.out.println("18127270 - Slang word menu");
                    System.out.println("input the command in \"\" to use");
                    System.out.println("\"key\" - Search by key    \"mean\" - search by meaning");
                    System.out.println("\"history\" - Show history    \"mean\" - search by meaning");
                    System.out.println("At any state press enter to back to the previous screen");
                    Scanner sc = new Scanner(System.in);
                    System.out.print("Enter the command: ");
                    state = sc.nextLine();
                    break;
                case "key":
                    performKeySearch(slangList);
                    break;
                case "value":
                    Scanner valueScan = new Scanner(System.in);
                    System.out.print("Enter the value: ");
                    String searchKey = valueScan.nextLine();
//                    int i = 0;
//                    while (slangList.slangMap.get(searchKey + "_" + i) != null) {
//                        System.out.println( ">>> " + ANSI_BLUE + searchKey + ANSI_RESET + ANSI_YELLOW + " : " + ANSI_YELLOW + ANSI_BLUE + slangList.slangMap.get(searchKey + "_" + i) + ANSI_RESET );
//                        i+=1;
//                    }
//                    if (i == 0) {
//                        System.out.println(ANSI_RED + "No slang word for \"" + searchKey + "\"" + ANSI_RESET);
//                    }
                    break;
                default:
                    System.out.println("haha");
                    break;
            }
        }
    }

     static void performKeySearch(SlangList slangList) {
            Scanner keyScan = new Scanner(System.in);
            System.out.print("Enter the key: ");
            String searchKey = keyScan.nextLine();
            List<String> resultList = slangList.getWordByKey(searchKey, true);
            slangList.displayAllWordInList(resultList);
    }

}
