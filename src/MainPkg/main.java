package MainPkg;//package src;

import MainPkg.SlangList;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashMap;
import java.io.*;
//for converting hash map into json


public class main {
    public static void main(String[] args) {
        HashMap<String, String> capitalCities = new HashMap<String, String>();
        long start = System.currentTimeMillis();
// some time passes

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


    }
}
