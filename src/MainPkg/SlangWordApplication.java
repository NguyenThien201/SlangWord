package MainPkg;//package src;

import MainPkg.SlangList;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap;
import java.io.*;
//for converting hash map into json


public class SlangWordApplication {
    SlangList slangList;
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
        SlangWordApplication app = new SlangWordApplication();
        app.slangList = new SlangList();
        app.slangList.loadData();
        long stop = System.currentTimeMillis();
        System.out.println("Import complete after " + (stop - start) + " miliseconds");
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
                    app.performKeySearch(app.slangList);
                    break;
                case "value":
                    app.performValueSearch(app.slangList);
                    break;
                default:
                    System.out.println("haha");
                    break;
            }
        }
    }

    void performKeySearch(SlangList slangList) {
        Scanner keyScan = new Scanner(System.in);
        System.out.print("");
        System.out.print("Enter the key: ");
        String searchKey = keyScan.nextLine();
        long start = System.currentTimeMillis();
        List<String> resultList = slangList.getWordByKey(searchKey.toLowerCase(), true);
        long stop = System.currentTimeMillis();
        System.out.println("Found " + resultList.size() + " results for key \"" + searchKey + "\" after " + (stop - start) + " miliseconds");
        slangList.displayAllWordInList(resultList);
    }

    void performValueSearch(SlangList slangList) {
        Scanner valueScan = new Scanner(System.in);
        System.out.print("\nEnter the value: ");
        String searchValue = valueScan.nextLine();
        long start = System.currentTimeMillis();
        List<String> resultList = slangList.getWordByValue(searchValue.toLowerCase(), true);
        long stop = System.currentTimeMillis();
        System.out.println("Found " + resultList.size() + " results for value \"" + searchValue + "\" after " + (stop - start) + " miliseconds");
        slangList.displayAllWordInList(resultList);
    }

}
