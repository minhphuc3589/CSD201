/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.components;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * CSD201 - License Text [Java FX]
 * 
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class LicenseText extends Text {
    private String license;
    
    /**
     * The constructor.
     * 
     * @param license The string of license
     */
    public LicenseText(String license) {
        this.license = license;
        
        this.setText(license);
        this.setFont(Font.font("Geist Mono", 16));
    }
    
    /**
     * Gets the string of license.
     * 
     * @return The string of license
     */
    public String getLicense() {
        return license;
    }
}
