/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.references.components;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Java FX Button - CSD201
 * 
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class ButtonFX extends ImageView {    
    public ButtonFX(String imgURL, double width, double height) {
        Image image = new Image(imgURL);
        this.setImage(image);
        this.setCursor(Cursor.HAND);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }
    
    public ButtonFX(Image image, double width, double height) {
        this.setImage(image);
        this.setCursor(Cursor.HAND);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }
}
