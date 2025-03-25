/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.components;

import javafx.scene.layout.Pane;
import lobby.Main;

/**
 * CSD201 - Register Pane [Java FX]
 * 
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class RegisterPane extends Pane {
    private Pane paneHeader;
    private Pane paneBody;
    private Pane paneFooter;
    
    private final double WIDTH;
    private final double HEIGHT;
    
    public RegisterPane() {
        paneHeader = new Pane();
        paneBody = new Pane();
        paneFooter = new Pane();
        
        this.WIDTH = Main.WIDTH / 3;
        this.HEIGHT = Main.HEIGHT;
        
        // Settings of Login pane
        this.setId("register-pane");
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);
        this.getChildren().addAll(paneHeader, paneBody, paneFooter);
    }

    

}
