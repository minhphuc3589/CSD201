/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.components;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class LicenseText extends Text {
    private String license;
    
    public LicenseText(String text) {
        license = text;
        
        this.setText(text);
        this.setFont(Font.font("Geist Mono", 16));
    }
    
    public String getLicense() {
        return license;
    }
}
