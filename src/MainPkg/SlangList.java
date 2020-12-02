package MainPkg;

import MainPkg.SlangWordApplication;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.*;
import java.util.HashMap;
import java.io.*;

class SlangList implements java.io.Serializable {

    HashMap<String, String> slangMap = new HashMap<String, String>();
    HashMap<String, String> lowerCaseSlangMap = new HashMap<String, String>();
    List<String> keySearchHistory = new ArrayList<String>();
    List<String> valueSearchHistory = new ArrayList<String>();

    public void loadData() {
        if (!this.importCacheDataFrom("./slangList.ser")) {
            this.readDataFrom("./slang.txt");
        }
    }

    public void cacheDate() {
        this.saveDataTo("./slangList.ser");
    }

    public void clearCacheData() {
        this.readDataFrom("./slang.txt");
        this.keySearchHistory.clear();
        this.valueSearchHistory.clear();
    }

    public void readDataFrom(String path) {
        HashMap<String, String> cacheMap = new HashMap<String, String>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains("`")) {
                    String[] arrOfStr = data.split("`", 2);
                    int i = 0;
                    while (cacheMap.get(arrOfStr[0] + "_" + i) != null) {
                        i += 1;
                    }
                    cacheMap.put(arrOfStr[0] + "_" + i, arrOfStr[1]);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Can not read file from: " + path);
            e.printStackTrace();
            return;
        }

        this.slangMap = cacheMap;
        this.lowerCaseSlangMap = this.configValueSlangMap(cacheMap);
        System.out.println("import count " + this.slangMap.size());
        return;
    }

    public boolean importCacheDataFrom(String path) {
        SlangList cacheSlang;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            cacheSlang = (SlangList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Does not have cache file");
            return false;
        } catch (ClassNotFoundException c) {
            System.out.println("Can not found the cache file");
            return false;
        }

        this.slangMap = cacheSlang.slangMap;
        this.lowerCaseSlangMap = this.configValueSlangMap(cacheSlang.slangMap);
        this.keySearchHistory = cacheSlang.keySearchHistory;
        this.valueSearchHistory = cacheSlang.valueSearchHistory;

        System.out.println("import from cache count  " + this.slangMap.size());
        return true;
    }

    public void saveDataTo(String path) {
        if (!this.slangMap.isEmpty()) {
            try {
                FileOutputStream fileOut = new FileOutputStream(path);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                System.out.println("Can not save to " + path);
                i.printStackTrace();
            }
        } else {
            System.out.println("There are nothing to save to " + path);
        }
    }

    private HashMap<String, String> configValueSlangMap(HashMap<String, String> normalMap) {
        HashMap<String, String> cacheMap = new HashMap<String, String>();
        for (String key : normalMap.keySet()) {
            String mapValue = normalMap.get(key);
            cacheMap.put(key, mapValue.toLowerCase());
        }
        return cacheMap;
    }

    /**
     * @param value:          the search value
     * @param isAddToHistory: add the search value to history list or not
     * @return the search key stand for the specific value found in the hash map
     */
    public List<String> getWordByValue(String value, boolean isAddToHistory) {
        if (isAddToHistory) {
            this.valueSearchHistory.add(value);
        }
        return this.getWordByValue(value);
    }

    /**
     * @param value: the search value
     * @return the search key stand for the specific value found in the hash map
     */
    public List<String> getWordByValue(String value) {
        List<String> resultList = new ArrayList<String>();
        for (String key : this.lowerCaseSlangMap.keySet()) {
            String mapValue = this.lowerCaseSlangMap.get(key);
            if (mapValue.contains(value)) {
                resultList.add(key);
            }
        }
        return resultList;
    }

    /**
     * @param searchKey:      the search key
     * @param isAddToHistory: add the search key to history list or not
     * @return the search key stand for the specific value found in the hash map
     */
    public List<String> getWordByKey(String searchKey, boolean isAddToHistory) {
        if (isAddToHistory) {
            this.keySearchHistory.add(searchKey);
        }
        return this.getWordByKey(searchKey);
    }

    /**
     * @param searchKey: the search key
     * @return the search key stand for the specific value found in the hash map
     */
    public List<String> getWordByKey(String searchKey) {
        List<String> resultList = new ArrayList<String>();
        int i = 0;
        while (this.lowerCaseSlangMap.get(searchKey + "_" + i) != null) {
            resultList.add(searchKey + "_" + i);
            i += 1;
        }
        return resultList;
    }

    public void displayAllWordInList(List<String> keyList) {
        for (String s : keyList) {
            int lastIndex = s.lastIndexOf("_");
            String searchKey = s.substring(0, lastIndex);
            System.out.println(">>> " + SlangWordApplication.ANSI_BLUE + searchKey + SlangWordApplication.ANSI_RESET + SlangWordApplication.ANSI_YELLOW + " : " + SlangWordApplication.ANSI_YELLOW + SlangWordApplication.ANSI_BLUE + this.slangMap.get(s) + SlangWordApplication.ANSI_RESET);
        }
    }

    public void displayAllWordInListWithIndexPrefix(List<String> keyList) {
        for (String s : keyList) {
            int lastIndex = s.lastIndexOf("_");
            String searchKey = s.substring(0, lastIndex);
            String indexIndex = s.substring(lastIndex + 1);

            System.out.println(">>> " + SlangWordApplication.ANSI_RED + "[" + indexIndex + "] " + SlangWordApplication.ANSI_RESET + SlangWordApplication.ANSI_BLUE + searchKey + SlangWordApplication.ANSI_RESET + SlangWordApplication.ANSI_YELLOW + " : " + SlangWordApplication.ANSI_YELLOW + SlangWordApplication.ANSI_BLUE + this.slangMap.get(s) + SlangWordApplication.ANSI_RESET);
        }
    }

    public void showSearchByKeyHistory() {
        System.out.println(SlangWordApplication.ANSI_CYAN + "Search by key history:" + SlangWordApplication.ANSI_RESET);
        for (String s : keySearchHistory) {
            System.out.println(">>> " + SlangWordApplication.ANSI_BLUE + s + SlangWordApplication.ANSI_RESET);
        }
    }

    public void addNewWord(String key, String value) {
        int i = 0;
        while (this.lowerCaseSlangMap.get(key + "_" + i) != null) {
            i += 1;
        }
        this.slangMap.put(key+"_"+i,value);
        this.lowerCaseSlangMap = this.configValueSlangMap(this.slangMap);
    }
    public void replaceWordWithIndex(String key, String value, int index) {
        this.slangMap.put(key+"_"+index,value);
        this.lowerCaseSlangMap = this.configValueSlangMap(this.slangMap);
    }
    public void showSearchByValueHistory() {
        System.out.println(SlangWordApplication.ANSI_CYAN + "Search by value history:" + SlangWordApplication.ANSI_RESET);
        for (String s : valueSearchHistory) {
            System.out.println(">>> " + SlangWordApplication.ANSI_BLUE + s + SlangWordApplication.ANSI_RESET);
        }
    }

    public void showRandomWord() {
        Random ran = new Random();
        Object[] keys = slangMap.keySet().toArray();
        Object randomKey = keys[ran.nextInt(keys.length)];
        String keyString = randomKey.toString();
        int lastIndex = keyString.lastIndexOf("_");
        String searchKey = keyString.substring(0, lastIndex);
        List<String> randomKeyList = this.getWordByKey(searchKey);
        this.displayAllWordInList(randomKeyList);
    }


}

