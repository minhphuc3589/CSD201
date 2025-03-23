package lobby;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * CSD201 - Main
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class Main extends Application {
    private static Stage stage;

    private static final double SCALE = 1.2;
    public static final double WIDTH = Screen.getPrimary().getBounds().getWidth() / SCALE;
    public static final double HEIGHT = Screen.getPrimary().getBounds().getHeight() / SCALE;


    /**
     * The main method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the frame
     *
     * @param stage The frame of stage
     * @throws Exception if one of all Object is not initiated
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        this.stage = stage;
        Lobby scene = new Lobby();

        stage.setTitle("Login");
        stage.setScene(scene.getScene());
        stage.show();

    }

    /**
     * Getter of static Stage
     *
     * @return The static stage
     */
    public static Stage getStage() {
        return stage;
    }

}
