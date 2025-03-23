/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobby.components;

import DataAccessObject.File.UserDAO;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lobby.Main;
import lobby.algorithms.CountCharacter;
import lobby.gamecenter.GameCenter;

/**
 * CSD201 - Login
 *
 * @author Trương Đoàn Minh Phúc - CE190744
 */
public class LoginPane extends Pane {

    private final Pane paneHeader;
    private final Pane paneBody;
    private final Pane paneFooter;
    private final Text txtTitle;
    private final Text txtUsername;
    private final Text txtPassword;
    private final Text txtForgotPassword;
    private final TextField fieldUsername;
    private final PasswordField fieldPassword;
    private final javafx.scene.control.Button btnLogin;

    private final double WIDTH;
    private final double HEIGHT;

    private boolean status;

    /**
     * The constructor.
     */
    public LoginPane() {
        paneHeader = new Pane();
        paneBody = new Pane();
        paneFooter = new Pane();
        txtTitle = new Text();
        txtUsername = new Text();
        txtPassword = new Text();
        txtForgotPassword = new Text();
        fieldUsername = new TextField();
        fieldPassword = new PasswordField();

        btnLogin = new javafx.scene.control.Button();

        status = true;

        WIDTH = Main.WIDTH / 3;
        HEIGHT = Main.HEIGHT;

        // Settings of Login pane
        this.setId("login-pane");
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setMaxSize(WIDTH, HEIGHT);
        this.getChildren().addAll(paneHeader, paneBody, paneFooter);

        // Configuration of elements
        configureHeader();
        configureBody();
        configureFooter();
    }

    /**
     * Gets handler of Register text.
     *
     * @param x The coordination of x
     * @param y The coordination of y
     * @return The Register pane
     */
    private StackPane paneRegisterText(double x, double y) {
        StackPane paneRegister = new StackPane();
        Text txtRegister = new Text();

        txtRegister.setId("register-text");
        txtRegister.setText("You don't have account? Register it!");
        txtRegister.setFont(Font.font("Geist Mono", 16));

        // Text's event handler
        txtRegister.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (int i = 0; i < paneBody.getChildren().size(); i++) {
                System.out.println(paneBody.getChildren().get(i).getId());
            }
        });

        paneRegister.setMinWidth(x);
        paneRegister.setLayoutX(0);
        paneRegister.setLayoutY(y);
        paneRegister.getChildren().add(txtRegister);

        return paneRegister;
    }

    /**
     * Gets handler of Login pane.
     *
     * @return The Login pane
     */
    private Pane paneLoginHandler() {

        Pane pane = new Pane();
        Pane paneTextUsername = new Pane();
        Pane paneTextPassword = new Pane();

        pane.setId("login-handler-pane");
        pane.setMinSize(WIDTH, HEIGHT / 2);

        // Username configuration
        txtUsername.setText("Username");
        txtUsername.setFont(Font.font("Geist Mono", 18));
        txtUsername.setLayoutY(txtUsername.getFont().getSize());

        paneTextUsername.setId("username-text");
        paneTextUsername.setLayoutX((WIDTH / 5) - txtUsername.getFont().getSize() - txtUsername.getText().length() + 10);
        paneTextUsername.setLayoutY(0);
        paneTextUsername.getChildren().add(txtUsername);

        fieldUsername.setId("username-field");
        fieldUsername.setFont(Font.font("Geist Mono", 16));
        fieldUsername.setMinWidth(WIDTH - ((WIDTH / 5) - txtUsername.getFont().getSize() - txtUsername.getText().length()) * 2);
        fieldUsername.setLayoutX((WIDTH / 5) - txtUsername.getFont().getSize() - txtUsername.getText().length());
        fieldUsername.setLayoutY(paneTextPassword.getLayoutY() + fieldUsername.getFont().getSize() - 2);

        // Password configuration
        txtPassword.setText("Password");
        txtPassword.setFont(Font.font("Geist Mono", 18));
        txtPassword.setLayoutY(txtPassword.getFont().getSize());

        paneTextPassword.setId("password-text");
        paneTextPassword.setLayoutX((WIDTH / 5) - txtPassword.getFont().getSize() - txtPassword.getText().length() + 10);
        paneTextPassword.setLayoutY(fieldUsername.getLayoutY() + txtPassword.getFont().getSize() * 4);
        paneTextPassword.getChildren().add(txtPassword);

        fieldPassword.setId("password-field");
        fieldPassword.setFont(Font.font("Geist Mono", 16));
        fieldPassword.setMinWidth(WIDTH - ((WIDTH / 5) - txtUsername.getFont().getSize() - txtUsername.getText().length()) * 2);
        fieldPassword.setLayoutX((WIDTH / 5) - txtUsername.getFont().getSize() - txtUsername.getText().length());
        fieldPassword.setLayoutY(paneTextPassword.getLayoutY() + fieldPassword.getFont().getSize() - 2);

        // Submit button's handler
        HBox hboxSubmission = hboxSubmission(pane.getMinWidth(), pane.getMinHeight());

        StackPane paneRegisterText = paneRegisterText((pane.getMinWidth()), (pane.getMinHeight() + hboxSubmission.getLayoutY()) / 2);

        pane.getChildren().addAll(fieldUsername, fieldPassword, paneTextUsername, paneTextPassword, hboxSubmission, paneRegisterText);

        return pane;
    }

    /**
     * Initializes the Game Center.
     */
    private void initGameCenter() {
        Alert alertError = new Alert(Alert.AlertType.INFORMATION);
        UserDAO userDAO = new UserDAO();

        userDAO.login(fieldUsername.getText(), fieldPassword.getText());

        if (userDAO.isLogin()) {
            GameCenter gameCenter = new GameCenter();

            btnLogin.setDisable(true);

            gameCenter.start();

            Main.getStage().close();

        } else {

            alertError.setTitle("Login failed");
            alertError.setHeaderText("Login failed");
            alertError.setContentText("Username or password is not correct");

            alertError.showAndWait();
        }
    }

    /**
     * Submission handler.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return The HBox containing Forgot Password text and Submit button
     */
    private HBox hboxSubmission(double x, double y) {
        HBox hboxSubmission = new HBox();

        txtForgotPassword.setId("forgot-password-text");
        txtForgotPassword.setText("Forgot password?");
        txtForgotPassword.setFont(Font.font("Geist Mono", 16));

        /* Sign in account */
        btnLogin.setText("Login");

        btnLogin.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            initGameCenter();            
        });
        /* Sign in account */

        hboxSubmission.setId("submission-hbox");
        hboxSubmission.setMinSize(x, btnLogin.getHeight());
        hboxSubmission.setSpacing(x / 4);
        hboxSubmission.setAlignment(Pos.CENTER);
        hboxSubmission.setLayoutX(0);
        hboxSubmission.setLayoutY(fieldPassword.getLayoutY() + (fieldPassword.getFont().getSize() * 2) + 10 * 2);
        hboxSubmission.getChildren().addAll(txtForgotPassword, btnLogin);

        return hboxSubmission;
    }

    /**
     * Header and components inside Header.
     */
    private void configureHeader() {
        VBox paneTextTitle = new VBox();

        // Logo header
        Image image = new Image("pictures/Logo_FPT_Education.png");

        double imgHeightHeader = 75;
        double imgScale = image.getWidth() / image.getHeight();

        ImageView imgHeader = new ImageView();
        imgHeader.setImage(image);
        imgHeader.setLayoutY(0);
        imgHeader.setFitWidth(imgHeightHeader * imgScale);
        imgHeader.setFitHeight(imgHeightHeader);

        // Title
        txtTitle.setText("L O G I N");
        txtTitle.setFont(Font.font("Geist Mono", 40));

        // Header pane's subpane
        paneTextTitle.setMinSize(WIDTH, txtTitle.getFont().getSize() + imgHeader.getFitHeight());
        paneTextTitle.setAlignment(Pos.CENTER);
        paneTextTitle.setSpacing(25);
        paneTextTitle.setLayoutY(0);
        paneTextTitle.getChildren().addAll(imgHeader, txtTitle);

        // Header pane's configuration
        paneHeader.setId("header-pane");
        paneHeader.setLayoutY(HEIGHT / 16);
        paneHeader.getChildren().addAll(paneTextTitle);
    }

    /**
     * Body and components inside Body.
     */
    private void configureBody() {
        // Body configuration
        paneBody.setId("body-pane");
        paneBody.setLayoutY((HEIGHT / 2.6) - 5);

        // Register pane's configuration
        Pane paneLogin = paneLoginHandler();

        paneBody.getChildren().addAll(paneLogin);
    }

    /**
     * Footer and components inside Footer.
     */
    private void configureFooter() {
        // Footer pane's subpane
        StackPane paneLicense = new StackPane();

        // License's configuration
        LicenseText txtLicense = new LicenseText("License belongs to CSD201\n"
                + "Lecturer: Le Thi Phuong Dung");

        int countNextLine = new CountCharacter(txtLicense.getLicense(), '\n').getResult();

        txtLicense.setId("license-text");
        txtLicense.setLayoutY(txtLicense.getFont().getSize());

        // Subpane's configuration
        paneLicense.setMinSize(WIDTH, txtLicense.getFont().getSize());
        paneLicense.getChildren().add(txtLicense);

        // Footer pane's configuration
        paneFooter.setId("footer-pane");
        paneFooter.setLayoutY(HEIGHT - (txtLicense.getFont().getSize() * countNextLine) - 25);
        paneFooter.getChildren().add(paneLicense);
    }
}
