package MainPkg;

import MainPkg.SlangList;
import jdk.jshell.SourceCodeAnalysis;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;


public class SlangWordApplication {
    SlangList slangList;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static boolean isColor = true;

    public static String ANSI_RESET() {
        return (isColor ? ANSI_RESET : "");
    }

    public static String ANSI_RED() {
        return (isColor ? ANSI_RED : "");
    }

    public static String ANSI_YELLOW() {
        return (isColor ? ANSI_YELLOW : "");
    }

    public static String ANSI_BLUE() {
        return (isColor ? ANSI_BLUE : "");
    }

    public static String ANSI_CYAN() {
        return (isColor ? ANSI_CYAN : "");
    }

    public static String ANSI_GREEN() {
        return (isColor ? ANSI_GREEN : "");
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SlangWordApplication app = new SlangWordApplication();
        app.slangList = new SlangList();
        app.slangList.loadData();
        long stop = System.currentTimeMillis();
        System.out.println("Import complete after " + (stop - start) + " miliseconds");
        System.out.println(ANSI_GREEN()+"\n========================================================================="+ANSI_RESET());
        Scanner colorSc = new Scanner(System.in);
        System.out.println(ANSI_RED() + "IMPORTANT: PRESS ENTER IF THIS LINE IS RED, PRESS ANY KEY ELSE IF NOT" + ANSI_RESET());
        String colorPick = colorSc.nextLine();
        if (!colorPick.isEmpty()) {
            isColor = false;
        }
        System.out.println(ANSI_GREEN()+"\n========================================================================="+ANSI_RESET());
        System.out.println(ANSI_YELLOW() + "\nThe random slang word for today is:" + ANSI_RESET());
        app.slangList.showRandomWord();
        String state = "ls";
        while (true) {
            switch (state) {
                case "ls":
                    System.out.println(ANSI_GREEN()+"\n========================================================================="+ANSI_RESET());
                    System.out.println("\n18127270 - Slang word menu");
                    System.out.println(ANSI_GREEN()+"Input the command in \"\" to use"+ANSI_RESET());
                    System.out.println(ANSI_CYAN() + "\"key\" " + ANSI_RESET() + "       -   Search by key                    " + ANSI_CYAN() + "   \"value\" " + ANSI_RESET() + "           -   search by value");
                    System.out.println(ANSI_CYAN() + "\"keyHistory\" " + ANSI_RESET() + "-   Show key history                 " + ANSI_CYAN() + "   \"valueHistory\" " + ANSI_RESET() + "    -   Show value history ");
                    System.out.println(ANSI_CYAN() + "\"add\" " + ANSI_RESET() + "       -   Add new word                     " + ANSI_CYAN() + "   \"del\" " + ANSI_RESET() + "             -   Delete a word ");
                    System.out.println(ANSI_CYAN() + "\"keyQuiz\"" + ANSI_RESET() + "    -   Start key quiz                   " + ANSI_CYAN() + "   \"valueQuiz\" " + ANSI_RESET() + "       -   Start value quiz ");
                    System.out.println(ANSI_CYAN() + "\"reset\" " + ANSI_RESET() + "     -   clear all cache data            " + ANSI_CYAN() + "    \"exit\" " + ANSI_RESET() + "            -   Save cache data and exit ");
                    System.out.println(ANSI_CYAN() + "\"random\" " + ANSI_RESET() + "    -   Show a random word");
                    System.out.println(ANSI_CYAN() + "\"ls\"  " + ANSI_RESET() + "       -   Display the command list");
                    System.out.println("At any state press "+ANSI_RED()+"enter "+ANSI_RESET()+"to back to the menu command");
                    System.out.println(ANSI_GREEN()+"\n========================================================================="+ANSI_RESET());
                    state = "menu";
                    break;
                case "menu":
                    Scanner sc = new Scanner(System.in);
                    System.out.print(ANSI_GREEN()+"\nMenu :: Enter the command: "+ANSI_RESET());
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
                case "del":
                    Word deleteWord = app.deleteAWord(app.slangList);
                    if (deleteWord.replaceIndex < -1) {
                        state = "menu";
                        break;
                    } else {
                        app.slangList.deleteWordWithIndex(deleteWord.key, deleteWord.replaceIndex);
                    }
                    break;
                case "add":
                    Word newWord = app.addNewWords(app.slangList);
                    if (newWord.replaceIndex < -1) {
                        state = "menu";
                        break;
                    } else if (newWord.replaceIndex >= 0) {
                        app.slangList.replaceWordWithIndex(newWord.key, newWord.value, newWord.replaceIndex);
                    } else {
                        app.slangList.addNewWord(newWord.key, newWord.value);
                    }
                    break;
                case "exit":
                    app.slangList.cacheData();
                    System.out.println("Application is shutting down");
                    return;
                case "reset":
                    System.out.println("Reset the application");
                    app.slangList.clearCacheData();
                    state = "ls";
                    break;
                case "random":
                    app.slangList.showRandomWord();
                    state = "menu";
                    break;
                case "keyQuiz":
                    if (app.performQuiz(true)) {
                        state = "menu";
                    }
                    break;
                case "valueQuiz":
                    if (app.performQuiz(false)) {
                        state = "menu";
                    }
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

    Word deleteAWord(SlangList slangList) {
        Word delete = new Word();
        Scanner keyScan = new Scanner(System.in);
        System.out.print("\nEnter the key for the delete word: ");
        delete.key = keyScan.nextLine();
        if (delete.key.isEmpty()) {
            delete.replaceIndex = -2;
            return delete;
        }
        List<String> duplicateKeyList = slangList.getWordByKey(delete.key);
        if (duplicateKeyList.isEmpty()) {
            System.out.println("Can't found any word to delete");
            return delete;
        } else {
            System.out.println("\nFound " + duplicateKeyList.size() + " duplicate results for key \"" + delete.key + "\"");
            System.out.println("Choose word index inside \"[]\" from the list to delete - \"all\" to delete all");
            slangList.displayAllWordInListWithIndexPrefix(duplicateKeyList);
            while (true) {
                Scanner deleteScanner = new Scanner(System.in);
                String inputInt = deleteScanner.nextLine();
                if (inputInt.isEmpty()) {
                    delete.replaceIndex = -2;
                    return delete;
                } else if (inputInt.contains("all")) {
                    delete.replaceIndex = -1;
                    System.out.println("Completed delete all word with the key \"" + delete.key + "\"");
                    return delete;
                }
                try {
                    int deleteIndex = Integer.parseInt(inputInt);
                    if (deleteIndex >= duplicateKeyList.size() || deleteIndex < 0) {
                        System.out.println("Index out of range, please choose new index");
                    } else {
                        System.out.println("Completed delete \"" + delete.key + "\" || \"" + slangList.slangMap.get(delete.key + "_" + deleteIndex));
                        delete.replaceIndex = deleteIndex;
                        return delete;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("You must input a number, try again");
                }
            }
        }
    }

    Word addNewWords(SlangList slangList) {
        Word add = new Word();
        Scanner keyScan = new Scanner(System.in);
        System.out.print("\nEnter the key for new words: ");
        add.key = keyScan.nextLine();
        if (add.key.isEmpty()) {
            add.replaceIndex = -2;
            return add;
        }
        System.out.print("Enter the value: ");
        Scanner valueScan = new Scanner(System.in);
        add.value = valueScan.nextLine();
        if (add.value.isEmpty()) {
            add.replaceIndex = -2;
            return add;
        }
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
                    if (inputInt.isEmpty()) {
                        add.replaceIndex = -2;
                        return add;
                    }
                    try {
                        int replaceIndex = Integer.parseInt(inputInt);
                        if (replaceIndex >= duplicateKeyList.size() || replaceIndex < 0) {
                            System.out.println("Index out of range, please choose new index");
                        } else {
                            System.out.println("Completed replace \"" + add.key + "\" || \"" + slangList.slangMap.get(add.key + "_" + replaceIndex) + "\" with " + add.key + " || " + add.value);
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

    public boolean performQuiz(boolean isShowKey) {
        Random ran = new Random();
        String key = this.slangList.getRandomWordKey();
        int lastIndex = key.lastIndexOf("_");
        String displayKey = key.substring(0, lastIndex);
        int resultPos = ran.nextInt(3) + 1;
        System.out.println(ANSI_CYAN()+"\nChoose the correct meaning for the " + (isShowKey ? "key \"" : "value \"") + (isShowKey ? displayKey : this.slangList.slangMap.get(key)) + "\" - to exit, press enter:"+ANSI_RESET());
        for (int i = 1; i <= 4; i++) {
            if (resultPos == i) {
                System.out.println("[" + i + "] " + (!isShowKey ? displayKey : this.slangList.slangMap.get(key)));
            } else {
                String randomKey = this.slangList.getRandomWordKey();
                while (randomKey.equals(key)) {
                    randomKey = this.slangList.getRandomWordKey();
                }
                System.out.println("[" + i + "] " + (!isShowKey ? randomKey.substring(0, randomKey.lastIndexOf("_")) : this.slangList.slangMap.get(randomKey)));
            }
        }

        Scanner stringScanner = new Scanner(System.in);
        String stringInput = stringScanner.nextLine();
        if (stringInput.isEmpty()) {
            return true;
        }
        try {
            int intInput = Integer.parseInt(stringInput);
            if (intInput > 4 || intInput < 0) {
                System.out.println("Index out of range, please choose new index between 1 and 4");
            } else if (intInput == resultPos) {
                System.out.println(ANSI_YELLOW() + "Correct answer!" + ANSI_RESET());
            } else {
                System.out.println(ANSI_RED() + "Incorrect, the answer is [" + resultPos + "] >>> " + (!isShowKey ? displayKey : this.slangList.slangMap.get(key)) + ANSI_RESET());
            }
        } catch (NumberFormatException ex) {
            System.out.println("You must input a number, try again");
        }
        return false;
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
