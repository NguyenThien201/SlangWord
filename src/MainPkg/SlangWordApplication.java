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

        String state = "ls";
        while (true) {
            switch (state) {
                case "ls":
                    System.out.println("\n=========================================================================");
                    System.out.println("18127270 - Slang word menu");
                    System.out.println("input the command in \"\" to use");
                    System.out.println("\"key\"     -   Search by key               \"value\"   -   search by value");
                    System.out.println("\"history\" -   Show history                \"mean\"    -   search by meaning");
                    System.out.println("\"ls\"    -   Display the command list");
                    System.out.println("At any state press enter to back to the previous screen");
                    System.out.println("=========================================================================");
                    state = "menu";
                    break;
                case "menu":
                    Scanner sc = new Scanner(System.in);
                    System.out.print("\nMenu :: Enter the command: ");
                    state = sc.nextLine();
                    break;
                case "key":
                    if (app.performKeySearch(app.slangList)) {
                           state = "menu";
                    }
                    break;
                case "value":
                    if (app.performValueSearch(app.slangList)) {
                        state = "menu";
                    }
                    break;
                case "valueHistory":
                    app.slangList.showSearchByValueHistory();
                    state = "menu";
                    break;
                case "keyHistory":
                    app.slangList.showSearchByKeyHistory();
                    state = "menu";
                    break;
                default:
                    state = "menu";
                    break;
            }
        }
    }

    boolean performKeySearch(SlangList slangList) {
        Scanner keyScan = new Scanner(System.in);
        System.out.print("\nEnter the key: ");
        String searchKey = keyScan.nextLine();
        if (searchKey.isEmpty() || searchKey.isEmpty()) {
            return true;
        }
        long start = System.currentTimeMillis();
        List<String> resultList = slangList.getWordByKey(searchKey.toLowerCase(), true);
        long stop = System.currentTimeMillis();
        System.out.println("Found " + resultList.size() + " results for key \"" + searchKey + "\" after " + (stop - start) + " miliseconds");
        slangList.displayAllWordInList(resultList);
        return false;
    }

    boolean performValueSearch(SlangList slangList) {
        Scanner valueScan = new Scanner(System.in);
        System.out.print("\nEnter the value: ");
        String searchValue = valueScan.nextLine();
        if (searchValue.isEmpty() || searchValue.isEmpty()) {
            return true;
        }
        long start = System.currentTimeMillis();
        List<String> resultList = slangList.getWordByValue(searchValue.toLowerCase(), true);
        long stop = System.currentTimeMillis();
        System.out.println("Found " + resultList.size() + " results for value \"" + searchValue + "\" after " + (stop - start) + " miliseconds");
        slangList.displayAllWordInList(resultList);
        return false;
    }



}
