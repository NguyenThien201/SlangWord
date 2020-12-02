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
                case "add":
                    Word newWord = app.addNewWords(app.slangList);
                    if (newWord.replaceIndex>=0) {
                        app.slangList.replaceWordWithIndex(newWord.key, newWord.value, newWord.replaceIndex);
                    } else {
                        app.slangList.addNewWord(newWord.key, newWord.value);
                    }
                    state = "menu";
                    break;
                case "exit":
                    System.out.println("Application is shutting down");
                    return;

                case "reset":
                    System.out.println("Reset the application");
                    app.slangList.clearCacheData();
                    state = "ls";
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
        List<String> resultList = slangList.getWordByKey(searchKey, true);
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

    Word addNewWords(SlangList slangList) {
        Word add = new Word();
        Scanner keyScan = new Scanner(System.in);

        System.out.print("\nEnter the key for new words: ");
        add.key = keyScan.nextLine();
        System.out.print("Enter the value: ");
        Scanner valueScan = new Scanner(System.in);
        add.value = valueScan.nextLine();

        List<String> duplicateKeyList = slangList.getWordByKey(add.key);
        if (duplicateKeyList.isEmpty()) {
            System.out.println("Completed adding || " + add.key + " || " + add.value);
            return add;
        } else {
            System.out.println("\nFound " + duplicateKeyList.size() + " duplicate results for key \"" + add.key + "\"");
            System.out.println("Do you want to add a new one or replace an existed one?");
            Scanner addOrReplace = new Scanner(System.in);
            System.out.println("\"rep\" for replace - else for add");
            if (addOrReplace.nextLine().contains("rep")) {
                System.out.println("Choose word index inside \"[]\" from the list to replace");
                slangList.displayAllWordInListWithIndexPrefix(duplicateKeyList);
                while (true) {
                    Scanner replaceIndexScanner = new Scanner(System.in);
                    String inputInt = replaceIndexScanner.nextLine();
                    try {
                        int replaceIndex = Integer.parseInt(inputInt);
                        if (replaceIndex >= duplicateKeyList.size() || replaceIndex < 0) {
                            System.out.println("Index out of range, please choose new index");
                        } else {
                            System.out.println("Completed replace \"" + add.key + "\" || \""+ slangList.slangMap.get(add.key+"_"+replaceIndex) + "\" with " + add.key + " || " + add.value);
                            add.replaceIndex = replaceIndex;
                            return add;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("You must input a number, try again");
                    }
                }
            } else {
                System.out.println("Completed adding || " + add.key + " || " + add.value);
                return add;
            }
        }
    }

    final class Word {
        public String key;
        public String value;
        public int replaceIndex;

        Word() {
            this.key = "";
            this.value = "";
            this.replaceIndex = -1;
        }
    }
}
