/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.algorithms;

/**
 * CSD201 - Algorithm
 * 
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class CountCharacter {
    private int result;
    
    /**
     * 
     * @param string Origin string
     * @param character Which want to count
     */
    
    public CountCharacter(String string, Character character) {
        int countCharacter = 0;
        
        string = string.trim();
        
        if (!string.contains(String.valueOf(character))) result = 1;
        
        while (string.trim().contains(String.valueOf(character))) {
            string = string.substring(string.indexOf(String.valueOf(character)), string.length());
            countCharacter++;
        }
        
        result = countCharacter + 1;
    }
    
    public int getResult() {
        return result;
    }
}
