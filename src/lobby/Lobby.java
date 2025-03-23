/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby;

import lobby.references.components.ButtonFX;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lobby.components.LoginPane;


/**
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class Lobby {    
    private final Stage stage;
    private static Scene scene;
    private final StackPane stackPane;
    private Pane paneCurrent;
        
    private final int WIDTH;
    private final int HEIGHT;

    
    /**
     * The constructor
     */
    public Lobby() {
        stackPane = new StackPane();
        
        // Configure basis of Lobby's frame
        this.WIDTH = (int) Main.WIDTH;
        this.HEIGHT = (int) Main.HEIGHT;
        this.stage = Main.getStage();
        
        Pane paneOptionBar = getOptionBar();
        Pane paneBackground = getBackground();
        this.paneCurrent = new LoginPane();
        
        // Stack pane (Main's pane to start)
        stackPane.setId("stack-pane");
        stackPane.setFocusTraversable(true);
        stackPane.setAlignment(Pos.TOP_LEFT);
        stackPane.getChildren().addAll(paneBackground, paneOptionBar, paneCurrent);

        // Scene
        scene = new Scene(stackPane, WIDTH, HEIGHT);
        configureScene();
        
        // Handle Event
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
            }
        });
    }
    
    
    private Pane getBackground() {
        Pane pane =  new Pane();
        Text txtTitle = new Text();

        // Used to align the center of text
        StackPane stackPane = new StackPane();
        
        stackPane.getChildren().add(txtTitle);
        
        txtTitle.setText("GROUP 3");
        txtTitle.setFont(Font.font("Bungee Shade", 26));
        
        // Set title position
        stackPane.setLayoutX((WIDTH / 1.5) - txtTitle.getFont().getSize() - txtTitle.getText().length());
        stackPane.setLayoutY(40);

        // Pane's configuration
        pane.setId("background-pane");
        pane.getChildren().add(stackPane);
        return pane;
    }
    
    private Pane getOptionBar() {
        Pane pane = new Pane();
        ButtonFX btnMinimize = new ButtonFX("pictures/minimize-button.png", 20, 20);
        ButtonFX btnQuit = new ButtonFX("pictures/exit-button.png", 20, 20);
        
        // Set Option button's position
        btnMinimize.setLayoutX(0);
        btnMinimize.setLayoutY(0);
        
        btnQuit.setLayoutX(btnMinimize.getX() + btnMinimize.getFitWidth() + 5);
        btnQuit.setLayoutY(0);
        
        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            stage.close();
        });

        btnMinimize.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            stage.setIconified(true);
        });
        
        pane.setId("option-bar");
        pane.setMaxSize(btnQuit.getFitWidth() * 2 + 5, btnQuit.getFitHeight());
        pane.setTranslateX(WIDTH - pane.getMaxWidth() - 15);
        pane.setTranslateY(15);
        pane.getChildren().addAll(btnQuit, btnMinimize);
        
        return pane;
    }
 
    private void configureScene() {
        this.scene.getStylesheets().add("css/client.css");
    }
    
    
    public static Scene getScene() {
        return Lobby.scene;
    }
}
