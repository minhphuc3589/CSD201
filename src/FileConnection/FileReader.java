/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileConnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PhucTDMCE190744
 */
public class FileReader {

    private Scanner scan;

    /**
     * The constructor.
     */
    public FileReader() {
    }

    /**
     * Reads the existed data in file.
     *
     * @param path The text file
     * @return The string after reading
     */
    public String read(String path) {
        String string = null;

        try {
            scan = new Scanner(new File(path));

            while (scan.hasNextLine()) {
                string = scan.nextLine();
            }

            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return string;
    }

    /**
     * Writes a new data into file.
     *
     * @param path The string path
     * @param string The string others
     * @return True if wrote successfully
     */
    public boolean write(String path, String... string) {
        File file = new File(path);

        String stringExisted = "";

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            /* Loading existed data in database */
            scan = new Scanner(file);

            while (scan.hasNextLine()) {
                stringExisted += scan.nextLine() + "\n";
            }

            scan.close();
            /* Loading existed data in database */
            
            
            // Adds the new account of user which was signed up
            // string[0]: Presentation of username used to signed in
            // string[1]: Presentation of password used to signed in
            for (String value : string) {
                stringExisted += value + ";";
            }
            
            
            // Checks only remove the last character of stringExisted if had data in string
            if (!stringExisted.isEmpty()) {
                stringExisted = stringExisted.substring(0, stringExisted.length() - 1);
            }

            // Write the new data into the file to overwrite the old data
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(stringExisted);
            }

            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
